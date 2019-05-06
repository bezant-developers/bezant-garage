package io.bezant;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.bezant.constant.CommonConstant;
import io.bezant.exception.CommonException;
import io.bezant.model.User;
import io.bezant.model.UserHistory;
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

public class UserMgmt extends ChaincodeBase {
    private static final Log log = LogFactory.getLog(UserMgmt.class);

    public static void main(String[] args) {
        new UserMgmt().start(args);
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
                case "addUser": return addUser(stub);
                case "modifyUser": return modifyUser(stub);
                case "removeUser": return removeUser(stub);
                case "getUserHistory": return getUserHistory(stub);
                case "getUser": return getUser(stub);
                case "getUsers": return getUsers(stub);
                case "addLoginHistory": return addLoginHistory(stub);
                case "getLoginHistory": return getLoginHistory(stub);
                case "getLoginHistories": return getLoginHistories(stub);
                default: return newErrorResponse(String.format(NOT_FOUND_FUNCTION, func));
            }
        } catch (Exception e) {
            return newErrorResponse(e.getMessage());
        }
    }

    private Response addUser(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 4);
        User user = getUserByArgs(args);

        String compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER, user.getId()).toString();

        String userStr = stub.getStringState(compositeKey);

        if (!Strings.isNullOrEmpty(userStr)) {
            throw new CommonException(ALREADY_EXIST_USER);
        }

        stub.putStringState(compositeKey, convertObjectToJsonString(user));
        return newSuccessResponse();
    }

    private Response modifyUser(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 4);

        User newUser = getUserByArgs(args);

        String compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER, newUser.getId()).toString();

        String userStr = stub.getStringState(compositeKey);

        if (Strings.isNullOrEmpty(userStr)) {
            throw new CommonException(NOT_EXIST_USER);
        }

        stub.putStringState(compositeKey, convertObjectToJsonString(newUser));
        return newSuccessResponse();
    }

    private Response removeUser(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);

        String compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER, args.get(0)).toString();

        String userStr = stub.getStringState(compositeKey);

        if (Strings.isNullOrEmpty(userStr)) {
            throw new CommonException(NOT_EXIST_USER);
        }

        stub.delState(compositeKey);
        return newSuccessResponse();
    }

    private Response getUserHistory(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);

        CompositeKey compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER, args.get(0));
        QueryResultsIterator<KeyModification> keyModifications = stub.getHistoryForKey(compositeKey.toString());

        List<UserHistory> userHistories = Lists.newArrayList();

        for (KeyModification keyModification : keyModifications) {
            UserHistory userHistory = new UserHistory(keyModification.getTxId(), convertJsonStringToObject(keyModification.getStringValue(), User.class));
            userHistories.add(userHistory);
        }

        return newSuccessResponse(convertObjectToJsonStringBytes(userHistories));
    }

    private Response getUser(ChaincodeStub stub) {
        List<String> args = checkAndGetParams(stub, 1);

        CompositeKey compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER, args.get(0));

        String userStr = stub.getStringState(compositeKey.toString());

        if (Strings.isNullOrEmpty(userStr)) {
            throw new CommonException(NOT_EXIST_USER);
        }

        return newSuccessResponse(userStr.getBytes());
    }

    private Response getUsers(ChaincodeStub stub) {
        checkAndGetParams(stub, 0);

        CompositeKey compositeKey = stub.createCompositeKey(CommonConstant.PREFIX_COMPOSITE_KEY_USER);
        QueryResultsIterator<KeyValue> kvs = stub.getStateByPartialCompositeKey(compositeKey.toString());

        List<User> users = Lists.newArrayList();

        for (KeyValue kv : kvs) {
            User user = convertJsonStringToObject(kv.getStringValue(), User.class);
            users.add(user);
        }

        return newSuccessResponse(convertObjectToJsonStringBytes(users));
    }

    private User getUserByArgs(List<String> args) {
        return new User(args.get(0), args.get(1), args.get(2), args.get(3));
    }

    private Response addLoginHistory(ChaincodeStub stub) {
        return newSuccessResponse();
    }

    private Response getLoginHistory(ChaincodeStub stub) {
        return newSuccessResponse();
    }

    private Response getLoginHistories(ChaincodeStub stub) {
        return newSuccessResponse();
    }
}