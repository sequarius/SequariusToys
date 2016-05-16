package com.sequarius.microblog.tags;

/**
 * Created by Sequarius on 2015/6/1.
 */
public class Utils {
    public static String BLOCK = "__jsp_override__";

    static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }
}
