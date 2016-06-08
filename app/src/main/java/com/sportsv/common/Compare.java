package com.sportsv.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sungbo on 2016-06-03.
 */
public class Compare {

    //객체가 null인지 체크한다
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            String str = (String) object;
            return str.length() == 0;
        }

        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            return collection.size() == 0;
        }

        if (object.getClass().isArray()) {
            try {
                if (Array.getLength(object) == 0) {
                    return true;
                }
            } catch (Exception e) {
                //do nothing
            }
        }

        return false;
    }


    //이메일이 정상적인 형식인지 체크한다
    public static boolean checkEmail(String email)
    {
        String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(mail);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean validatePassword(String password) {
        return password.length() > 5;
    }

}
