Getting Started
==============

1. Retrieve your API-key / Secret-key.
2. Setup & Run

``` python

from bitrue.future_client import FutureClient
client = FutureClient(api_key, secret_key, biz='fapi')

client.get_server_time()
{'serverTime': 1654576137774, 'timezone': 'China Standard Time'}

client.get_contracts()
[{'symbol': 'E-BTC-USDT', 'pricePrecision': 2, 'side': 1, 'maxMarketVolume': 500000, 'multiplier': 0.0001, 'minOrderVolume': 10, 'maxMarketMoney': 50000.0, 'type': 'E', 'maxLimitVolume': 100000, 'maxValidOrder': 50, 'multiplierCoin': 'BTC', 'minOrderMoney': 50.0, 'maxLimitMoney': 200000.0, 'status': 1}]

client.get_order_book(contractName='E-BTC-USDT')
{'asks': [[29561.2, 41], [29561.5, 2170], [29562.1, 176], [29562.6, 522], [29563.3, 1088], [29563.6, 1174], [29565.5, 44220], [29566.1, 132], [29566.2, 750], [29566.3, 275], [29566.4, 1621], [29566.7, 4740], [29571.5, 101], [29571.6, 16664], [29571.7, 22943], [29572, 3818], [29572.1, 403], [30198.4, 5878]], 'bids': [[29561.1, 45076], [29561, 677], [29560.9, 2244], [29560.8, 7645], [29560.7, 417], [29516.6, 1193], [29478.9, 3149], [29466.5, 3223], [29443.3, 150], [29353.9, 9569], [29343.2, 8860], [29313.7, 7410], [29282.9, 3240], [29263.3, 16803], [29225.7, 3051], [29201.8, 224], [29194.9, 2127], [29081.4, 496], [29021.6, 401], [28984.9, 1484], [28981.9, 2694], [28924.5, 1033], [25040, 50], [25030, 100], [25020, 150], [24000, 100], [23000, 150], [22000, 100], [21110, 100], [21100, 150], [21000, 100], [20000, 160]], 'time': None}

 client.get_klines(contractName='E-BTC-USDT', interval='5min')


 client.create_order(contractName="E-BTC-USDT", price="29345.3", volume="2000", type="LIMIT", side="BUY", clientOrderId="38343043403134", open="OPEN", positionType="1", timeInForce="LIMIT")
{'orderId': '1141203528176992546'}

client.cancel_order(contractName='E-BTC-USDT', orderId='1141203528176992546')

client.get_order(contractName='E-BTC-USDT', orderId='1141203528176992546')
{'side': 'BUY', 'executedQty': 0, 'orderId': 1141203528176992546, 'price': 29345.3, 'origQty': 2000.0, 'avgPrice': 0.0, 'transactTime': 1654576812000, 'action': 'OPEN', 'contractName': 'E-BTC-USDT', 'type': 'LIMIT', 'timeInForce': '', 'status': 'CANCELED'}

client.get_open_orders(contractName="E-BTC-USDT")
[{'side': 'BUY', 'executedQty': 0, 'orderId': 1141203940493854189, 'price': 29345.3, 'origQty': 2000.0, 'avgPrice': 0.0, 'transactTime': 1654578307000, 'action': 'OPEN', 'contractName': 'E-BTC-USDT', 'type': 'LIMIT', 'timeInForce': '', 'status': 'INIT'}]

```
