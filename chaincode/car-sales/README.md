### Add car
```bash
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addCar", "0x1111", "Audi", "A8", "jin"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addCar", "0x1112", "BMW", "X6", "philip"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addCar", "0x1113", "Audi", "A4", "ian"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addCar", "0x1114", "Hundai", "Sonata", "mac"]}'
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["addCar", "0x1115", "Kia", "Ssolento", "windows"]}'
```

### Modify owner
```bash
docker exec cli peer chaincode invoke -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["modifyOwner", "0x1111", "jean"]}'
```

### Get car
```bash
docker exec cli peer chaincode query -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getCar", "0x1111"]}'
```

### Get car history
```bash
docker exec cli peer chaincode query -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getCarHistory", "0x1111"]}'
```

### Get cars by brand
```bash
docker exec cli peer chaincode query -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getCarsByBrand", "Audi"]}'
```

### Get cars
```bash
docker exec cli peer chaincode query -o orderer.example.com:7050 -C bezant-channel -n car-sales --peerAddresses peer0.bezant.example.com:7051 -c '{"Args":["getCars"]}'
```

### Upgrade
```bash
docker exec cli peer chaincode install -n car-sales -v 1.1 -l java -p /opt/gopath/src/car-sales
docker exec cli2 peer chaincode install -n car-sales -v 1.1 -l java -p /opt/gopath/src/car-sales                                                                                            
docker exec cli peer chaincode upgrade -o orderer.example.com:7050 -C bezant-channel -n car-sales -v 1.1 -c '{"Args":["init"]}'               
```