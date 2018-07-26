package net.abcbook.framework.common.util;

import net.abcbook.framework.common.enums.CodeEnumInteger;
import net.abcbook.framework.common.enums.CodeEnumString;

/**
 * @author summer
 * @date 2018/1/13 下午10:49
 */
public class EnumUtil {


    /**
     * @author summer
     * @date 2018/1/13 下午10:50
     * @param code 代表值
     * @param enumClass 枚举的 class 类型
     * @return T
     * @description 根据 Integer 类型的 code 值,获取相应的枚举对象
     */
    public static <T extends CodeEnumInteger> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    /**
     * @author summer
     * @date 2018/1/13 下午10:52
     * @param code key 值
     * @param enumClass 枚举的 class 类型
     * @return T
     * @description 根据 String 类型的 code 值,获取相应的枚举对象
     */
    public static <T extends CodeEnumString> T getByCode(String code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
