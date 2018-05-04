package com.qunar.lfz.assist;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public final class StringAssist {
    private static final Splitter SPLITTER_COMMA = Splitter.on(",");
    private static final Splitter SPLITTER_UNDERLINE = Splitter.on("_");

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

}
