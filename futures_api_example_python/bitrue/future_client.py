# coding=utf-8


import hashlib
import hmac
import requests
import time
from urllib.parse import urlparse
from operator import itemgetter
from bitrue.exceptions import BitrueAPIException, BitrueRequestException

try:
    import simplejson as json
except Exception as ex:
    import json

class FutureClient(object):

    API_URL =  'https://futuresopenapi.byqian.{}/fapi'   # 'https://fapi.bitrue.{}/fapi'        #
    FUTURES_URL = 'https://futuresopenapi.byqian.{}/fapi'    #'https://fapi.bitrue.{}/fapi'    # 
    WEBSITE_URL = 'https://www.byqian.{}'                  # 'https://www.bitrue.{}'          # 
    
    PUBLIC_API_VERSION = 'v1'
    PRIVATE_API_VERSION = 'v1'

    ORDER_STATUS_NEW = 'NEW'
    ORDER_STATUS_PARTIALLY_FILLED = 'PARTIALLY_FILLED'
    ORDER_STATUS_FILLED = 'FILLED'
    ORDER_STATUS_CANCELED = 'CANCELED'
    ORDER_STATUS_PENDING_CANCEL = 'PENDING_CANCEL'
    ORDER_STATUS_REJECTED = 'REJECTED'
    ORDER_STATUS_EXPIRED = 'EXPIRED'

    KLINE_INTERVAL_1MINUTE = '1min'
    KLINE_INTERVAL_5MINUTE = '5min'
    KLINE_INTERVAL_15MINUTE = '15min'
    KLINE_INTERVAL_30MINUTE = '30min'
    KLINE_INTERVAL_1HOUR = '1h'
    KLINE_INTERVAL_1DAY = '1day'
    KLINE_INTERVAL_1WEEK = '1week'
    KLINE_INTERVAL_1MONTH = '1month'

    SIDE_BUY = 'BUY'
    SIDE_SELL = 'SELL'

    ORDER_TYPE_LIMIT = 'LIMIT'
    ORDER_TYPE_MARKET = 'MARKET'

    TIME_IN_FORCE_GTC = 'GTC'  # Good till cancelled
    TIME_IN_FORCE_IOC = 'IOC'  # Immediate or cancel
    TIME_IN_FORCE_FOK = 'FOK'  # Fill or kill

    ORDER_RESP_TYPE_ACK = 'ACK'
    ORDER_RESP_TYPE_RESULT = 'RESULT'
    ORDER_RESP_TYPE_FULL = 'FULL'


    def __init__(self, api_key=None, api_secret=None, requests_params=None, tld='com'):
        """Bitrue API Client constructor
        :param api_key: Api Key
        :type api_key: str.
        :param api_secret: Api Secret
        :type api_secret: str.
        :param requests_params: optional - Dictionary of requests params to use for all calls
        :type requests_params: dict.
        """

        self.API_URL = self.API_URL.format(tld)
        self.WEBSITE_URL = self.WEBSITE_URL.format(tld)
        self.FUTURES_URL = self.FUTURES_URL.format(tld)
        

        self.API_KEY = api_key
        self.API_SECRET = api_secret
        self.session = self._init_session()
        self._requests_params = requests_params
        self.response = None
        self.timestamp_offset = 0

        # init DNS and SSL cert
        self.ping()
        # calculate timestamp offset between local and Bitrue server
        res = self.get_server_time()
        self.timestamp_offset = res['serverTime'] - int(time.time() * 1000)
    
    def _init_session(self):
        session = requests.session()
        session.headers.update({
            'Accept': 'application/json',
            'User-Agent': 'Bitrue/Python',

        })
        return session
    
    def _create_api_uri(self, path, signed=True, version=PUBLIC_API_VERSION):
        return self.API_URL + '/' + version + '/' + path
    
    def _create_website_uri(self, path):
        return self.WEBSITE_URL + "/" + path
    
    def _generate_signature(self, ts, method, path, params={}, payload=None):
        # sig_explain = '&'.join(["{}={}".format(k, v) for k, v in body.items()])
        # print(params)
        if params:
            qs = '&'.join(["{}={}".format(k, v) for k, v in params.items()])
            if path.find('?') == -1:
                qs = "?" + qs
            sig_explain = "%d%s%s%s%s" %(ts, method.upper(), path, qs, "" if not payload else payload)
        else:
            sig_explain = "%d%s%s%s" %(ts, method.upper(), path, "" if not payload else payload)
        # print("\n\n", sig_explain, "\n\n")

        m = hmac.new(self.API_SECRET.encode("utf-8"), sig_explain.encode('utf-8'), hashlib.sha256)
        return m.hexdigest()
    
    def _reqeust(self, method, uri, signed, force_params=False, **kwargs):

        # set default request timeout
        kwargs['timeout'] = 10
        headers = None

        # add our global requests params
        if self._requests_params:
            kwargs.update(self._requests_params)
        
        a_payload = None
        data = kwargs.get('data', None)

        # if get request assign data array to params value for requests lib
        if data and (method == 'get' or force_params):
            kwargs['params'] = '&'.join('%s=%s' %(data[0], data[1]) for data in kwargs['data'])
            del(kwargs['data'])
            data = None

        if data and isinstance(data, dict):
            kwargs['data'] = data

            # find any requests params passed and apply them
            if 'requests_params' in kwargs['data']:
                # merge requests params into kwargs
                kwargs.update(kwargs['data']['requests_params'])
                del(kwargs['data']['requests_params'])
             
            if method == 'get' or force_params:
                kwargs['params'] = '&'.join('%s=%s' %(data[0], data[1]) for data in kwargs['data'])
            else:
                a_payload = json.dumps(kwargs['data'])
                kwargs['json'] = kwargs['data']
            del(kwargs['data'])
        
        headers =  {'Content-Type': 'application/json'}

        if signed:
            ts = int(time.time() * 1000 + self.timestamp_offset)
            pr = urlparse(uri)
            path = pr.path
            if pr.query:
                path = path.join(['?', pr.query])

            # generate signature
            headers['X-CH-TS'] = str(ts)
            headers['X-CH-APIKEY'] = self.API_KEY
            headers['X-CH-SIGN'] = self._generate_signature(ts, method, path, params=kwargs.get('params',{}), payload=a_payload)
        # print(headers)
        kwargs.update({'headers' :headers})
        # print(kwargs)
        
        self.response = getattr(self.session, method)(uri, **kwargs)
        return self._handle_response()
    
    def _request_api(self, method, path, signed=False, version=PUBLIC_API_VERSION, **kwargs):
        uri = self._create_api_uri(path, signed, version)
        return self._reqeust(method, uri, signed, **kwargs)
    
    def _request_website(self, method, path, signed=False, **kwargs):
        uri = self._create_website_uri(path)
        return self._reqeust(method, uri, signed, **kwargs)
    
    def _handle_response(self):
        """internal helper for handing API responses from the Bitrue server.
        Rasises the appropriate exceptions when necessary; otherwise, returns the response
        """
        if not (200 <= self.response.status_code < 300):
            raise BitrueAPIException(self.response)
        
        try:
            return self.response.json()
        except ValueError:
            raise BitrueRequestException('Invalid Response: %s' %(self.response.text,))

    def _get(self, path, signed=False, version=PUBLIC_API_VERSION, **kwargs):
        return self._request_api('get', path, signed, version, **kwargs)
    
    def _post(self, path, signed=False, version=PUBLIC_API_VERSION, **kwargs):
        return self._request_api('post', path, signed, version, **kwargs)
    
    def _put(self, path, signed=False, version=PUBLIC_API_VERSION, **kwargs):
        return self._request_api('put', path, signed, version, **kwargs)
    
    def _delete(self, path, signed=False, version=PUBLIC_API_VERSION, **kwargs):
        return self._request_api('delete', path, signed, version, **kwargs)
    
    # public endpoints
    def get_time(self):
        return self.get_server_time()

    def get_server_time(self):
        return self._get('time')
    
    def ping(self):
        return self._get('ping')

    def get_contracts(self):
        return self._get('contracts')
    
    def get_symbol_info(self, symbol):
        res = self.get_contracts()

        for item in res:
            if item['symbol'] == symbol.upper():
                return item
        return None
    
    def get_ticker(self, **params):
        return self._get('ticker', params=params)
    
    def get_index(self, **params):
        return self._get("index", params=params)
    
    def get_order_book(self, **params):
        return self._get("depth", params=params)
    
    def get_klines(self, **params):
        return self._get("klines", params=params)
    
    # Account Endpoints

    def create_order(self, **params):
        """
        volume, price, contractName, type, side, open, position, clientOrderId, timeInForce
        """
        return self._post('order', True, data=params)

    def order_limit(self, timeInForce=TIME_IN_FORCE_GTC, **params):
        params.update({
            'type': self.ORDER_TYPE_LIMIT,
            'timeInForce': timeInForce
        })
        return self.create_order(**params)
    
    def order_limit_buy(self, timeInForce=TIME_IN_FORCE_GTC, **params):
        params.update({
            'side': self.SIDE_BUY,
        })
        return self.order_limit(timeInForce=timeInForce, **params)
    
    def order_limit_sell(self, timeInForce=TIME_IN_FORCE_GTC, **params):
        """Send in a new limit sell order
        """
        params.update({
            'side': self.SIDE_SELL
        })
        return self.order_limit(timeInForce=timeInForce, **params)
    
    def order_market(self, **params):
        """Send in a new market order
        """
        params.update({
            'type': self.ORDER_TYPE_MARKET
        })
        return self.create_order(**params)
    
    def order_market_buy(self, **params):
        """Send in a new market buy order
        """
        params.update({
            'side': self.SIDE_BUY
        })
        return self.order_market(**params)
    
    def order_market_sell(self, **params):
        """Send in a new market sell order
        """
        params.update({
            'side': self.SIDE_SELL
        })
        return self.order_market(**params)
    
    def get_order(self, **params):
        """Check an order's status. Either orderId or origClientOrderId must be sent.
        """
        return self._get('order', True, params=params)
    
    def cancel_order(self, **params):
        """Cancel an active order. Either orderId or origClientOrderId must be sent.
        """
        return self._post('cancel', True, data=params)
    
    def get_open_orders(self, **params):
        """Get all open orders on a symbol.
        """
        # data = self.extend_timestamp(params)
        # print(data)
        # params['timestamp'] = int(time.time() * 1000 + self.timestamp_offset)
        return self._get('openOrders', True, params=params)
    
    def get_account(self, **params):
        """Get current account information.
        """
        return self._get('account', True, params=params)
    
    def get_asset_balance(self, asset, **params):
        """Get current asset balance.
        """
        res = self.get_account(**params)
        # find asset balance in list of balances
        if "balances" in res:
            for bal in res['balances']:
                if bal['asset'].lower() == asset.lower():
                    return bal
        return None
    
    def get_my_trades(self, **params):
        """Get trades for a specific symbol.
        """
        return self._get('myTrades', True, params=params)


