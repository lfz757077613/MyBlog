package com.lfz.assist;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public final class StringAssist {
    private static final Splitter SPLITTER_COMMA = Splitter.on(",");
    private static final Splitter SPLITTER_UNDERLINE = Splitter.on("_");
    private static final Joiner JOINER_COMMA = Joiner.on(",");
    private static final Joiner JOINER_UNDERLINE = Joiner.on("_");

    public static List<String> splitComma(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        return SPLITTER_COMMA.trimResults().omitEmptyStrings().splitToList(text);
    }

    public static List<String> splitUnderline(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        return SPLITTER_UNDERLINE.trimResults().omitEmptyStrings().splitToList(text);
    }

    public static String joinComma(String...params) {
        if (ArrayUtils.isEmpty(params)) {
            return StringUtils.EMPTY;
        }
        return JOINER_COMMA.join(params);
    }

    public static String joinUnderline(String...params) {
        if (ArrayUtils.isEmpty(params)) {
            return StringUtils.EMPTY;
        }
        return JOINER_UNDERLINE.join(params);
    }

}
