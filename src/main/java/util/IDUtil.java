package util;

import java.util.UUID;

public class IDUtil {

    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    public static String randomGroupId() {
        return UUID.randomUUID().toString().substring(0,5);
    }
}
