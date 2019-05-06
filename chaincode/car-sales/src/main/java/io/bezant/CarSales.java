package io.bezant;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.bezant.constant.CommonConstant;
import io.bezant.exception.CommonException;
import io.bezant.model.Car;
import io.bezant.model.CarHistory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.List;

import static io.bezant.exception.ErrorMessage.*;
import static io.bezant.util.CommonUtil.*;

public class CarSales extends ChaincodeBase {
    private static final Log log = LogFactory.getLog(CarSales.class);

    public static void main(String[] args) {
        new CarSales().start(args);
    }

    @Override
    public Response init(ChaincodeStub stub) {
        log.info("========= Init =========");
        String func = stub.getFunction();

        if (!"init".equals(func)) {
            throw new CommonException(String.format(NOT_SUPPORT_FUNCTION, "init"));
        }

        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        log.info("========= Invoke =========");
        try {
            String func = stub.getFunction();

            switch (func) {
                case "addCar": return addCar(stub);
                case "modifyOwner": return modifyOwner(stub);
                case "getCar": return getCar(stub);
                case "getCarHistory": return getCarHistory(stub);
                case "getCarsByBrand": return getCarsByBrand(stub);
                case "getCars": return getCars(stub);
                default: return newErrorResponse(String.format(NOT_FOUND_FUNCTION, func));
            }
        } catch (Exception e) {
            return newErrorResponse(e.getMessage());
        }
    }

    private Response addCar(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 4);
        Car car = new Car(args.get(0), args.get(1), args.get(2), args.get(3));

        String carStr = stub.getStringState(car.getId());

        if (!Strings.isNullOrEmpty(carStr)) {
            throw new CommonException(ALREADY_EXIST_CAR);
        }

        stub.putStringState(car.getId(), convertObjectToJsonString(car));

        String compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_CAR_COMPOSITE_KEY, car.getBrand(), car.getId()).toString();
        stub.putStringState(compositeKey, "\u0000");

        return newSuccessResponse();
    }

    private Response modifyOwner(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 2);

        String carStr = stub.getStringState(args.get(0));

        if (Strings.isNullOrEmpty(carStr)) {
            throw new CommonException(NOT_EXIST_CAR);
        }

        Car car = convertJsonStringToObject(carStr, Car.class);
        car.setOwner(args.get(1));

        stub.putStringState(car.getId(), convertObjectToJsonString(car));
        return newSuccessResponse();
    }

    private Response getCar(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);

        String carStr = stub.getStringState(args.get(0));

        if (Strings.isNullOrEmpty(carStr)) {
            throw new CommonException(NOT_EXIST_CAR);
        }

        return newSuccessResponse(carStr.getBytes());
    }

    private Response getCarHistory(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);

        QueryResultsIterator<KeyModification> keyModifications = stub.getHistoryForKey(args.get(0));

        List<CarHistory> carHistories = Lists.newArrayList();

        for (KeyModification keyModification : keyModifications) {
            CarHistory carHistory = new CarHistory(keyModification.getTxId(), convertJsonStringToObject(keyModification.getStringValue(), Car.class));
            carHistories.add(carHistory);
        }

        return newSuccessResponse(convertObjectToJsonStringBytes(carHistories));
    }

    private Response getCarsByBrand(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);
        return getCarsByPartialCompositeKey(stub, stub.createCompositeKey(CommonConstant.PREFIX_CAR_COMPOSITE_KEY, args.get(0)));
    }

    private Response getCars(ChaincodeStub stub) {
        checkAndGetParams(stub, 0);
        return getCarsByPartialCompositeKey(stub, stub.createCompositeKey(CommonConstant.PREFIX_CAR_COMPOSITE_KEY));
    }

    private Response getCarsByPartialCompositeKey(ChaincodeStub stub, CompositeKey compositeKey) {
        QueryResultsIterator<KeyValue> kvs = stub.getStateByPartialCompositeKey(compositeKey.toString());

        List<Car> cars = Lists.newArrayList();

        for (KeyValue kv : kvs) {
            CompositeKey splitCompositeKey = stub.splitCompositeKey(kv.getKey());
            String id = splitCompositeKey.getAttributes().get(1);
            String carStr = stub.getStringState(id);
            Car car = convertJsonStringToObject(carStr, Car.class);
            cars.add(car);
        }

        return newSuccessResponse(convertObjectToJsonStringBytes(cars));
    }
}