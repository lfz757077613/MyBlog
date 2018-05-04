package com.qunar.lfz.assist;

import org.apache.commons.lang3.StringUtils;

public final class ParamCheck {
    private static final char[] specialCharArray = {'<', '>', '.', '*', '$', '(', ')', '@', '#', '%', '&'};
    public static boolean hasSpecialChar(String param) {
        if (param == null) {
            throw new NullPointerException();
        }
        if (StringUtils.containsAny(param, specialCharArray)) {
            return true;
        }
        return false;
    }
}
