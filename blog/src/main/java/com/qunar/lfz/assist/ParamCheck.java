package com.qunar.lfz.assist;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public final class ParamCheck {
    private static final char[] specialCharArray = {'<', '>', '.', '*', '$', '(', ')', '@', '#', '%', '&'};
    public static boolean hasSpecialChar(@NonNull String param) {
        if (StringUtils.containsAny(param, specialCharArray)) {
            return true;
        }
        return false;
    }
}
