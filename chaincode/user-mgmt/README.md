### Add user
```bash
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addUser", "0", "philip", "Seoul", "010-1234-5678"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addUser", "1", "jin", "Busan", "010-1234-5678"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addUser", "2", "ian", "Seongnam", "010-1234-5678"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addUser", "3", "mac", "Jeju", "010-1234-5678"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addUser", "4", "windows", "Daegu", "010-1234-5678"]}'
```

### Modify user
```bash
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["modifyUser", "0", "philip", "Suwon", "010-1234-5678"]}'
```

### Get user
```bash
docker exec cli peer chaincode query -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getUser", "0"]}'
```

### Get user history
```bash
docker exec cli peer chaincode query -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getUserHistory", "0"]}'
```

### Get users
```bash
docker exec cli peer chaincode query -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getUsers"]}'
```

### Remove user
```bash
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n user-mgmt --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["removeUser", "0"]}'
```

### Upgrade
```bash
docker exec cli peer chaincode install -n user-mgmt -v 1.1 -l java -p /opt/gopath/src/user-mgmt
docker exec cli2 peer chaincode install -n user-mgmt -v 1.1 -l java -p /opt/gopath/src/user-mgmt                                                                                            
docker exec cli peer chaincode upgrade -o orderer.example.com:7050 -C bezant-channel -n user-mgmt -v 1.1 -c '{"Args":["init"]}'               
```
