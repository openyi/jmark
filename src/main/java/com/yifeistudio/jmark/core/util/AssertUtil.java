package com.yifeistudio.jmark.core.util;

import java.util.Collection;
import java.util.Map;

public class AssertUtil {

    public static void IS_TRUE(boolean express, String errMsg) {
        if (!express) {
            throw new IllegalArgumentException(errMsg);
        }
    }

    public static void IS_TRUE(boolean condition, boolean express, String errMsg) {
        if (condition) {
            if (!express) {
                throw new IllegalArgumentException(errMsg);
            }
        }
    }

    public static void NOT_NULL(Object obj, String errMsg) {
        IS_TRUE(obj != null, errMsg);
        IS_TRUE(obj instanceof String, !obj.equals(""), errMsg);
        IS_TRUE(obj instanceof Collection, obj instanceof Collection && !((Collection)obj).isEmpty(), errMsg);
        IS_TRUE(obj instanceof Map, obj instanceof Map && !((Map)obj).isEmpty(), errMsg);
    }
}
