### Use Restlet Client Chrome Extension
* Import example json file.

### Use cURL

* Create Wallet
```bash
curl -X POST -H "Content-Type: application/json" -H "apikey: bae855bb-cc8b-3f80-96df-9c93e5339f7d" --data '{"skey": "1234qwer"}' https://testnet-apis.bezant.io/blockchain/v1/wallet
```

* Invoke
```bash
curl -X POST -H "Content-Type: application/json" -H "apikey: bae855bb-cc8b-3f80-96df-9c93e5339f7d" --data '{"function": "addUser", "address": "bznt0x0C5a2fbBB84d449d92dB51698b2b93175E70AE89", "skey": "1234qwer", "args": ["3", "jin", "Seoul", "010-1234-5678"]}' https://testnet-apis.bezant.io/blockchain/v1/bezant-channel/chaincodes/user-mgmt/invoke
```

* Query
```bash
curl -X POST -H "Content-Type: application/json" -H "apikey: bae855bb-cc8b-3f80-96df-9c93e5339f7d" --data '{"function": "getUser", "address": "bznt0x0C5a2fbBB84d449d92dB51698b2b93175E70AE89", "skey": "1234qwer", "args": ["3"]}' https://testnet-apis.bezant.io/blockchain/v1/bezant-channel/chaincodes/user-mgmt/query
```
