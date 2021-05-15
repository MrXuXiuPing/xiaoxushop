package co.yixiang.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱校验工具类
 */
@Slf4j
public class EmailUtils {
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}
