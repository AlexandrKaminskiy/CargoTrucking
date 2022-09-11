package by.singularity.service.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {
    public static Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static Integer parseInt(String strNum) {
        Integer num;
        try {
            num = Integer.parseInt(strNum);
        } catch (Exception e) {
            num = null;
        }
        return num;
    }

    public static <Role extends Enum<Role>> Role parseEnum(String strRole,
                                                           Class<Role> roleType) {
        Role role;
        try {
            role = Role.valueOf(roleType, strRole);
        } catch (Exception e) {
            role = null;
        }
        return role;
    }

    public static Boolean parseBool(String strBool) {
        Boolean bool;
        try {
            bool = Boolean.getBoolean(strBool);
        } catch (Exception e) {
            bool = null;
        }
        return bool;
    }
}
