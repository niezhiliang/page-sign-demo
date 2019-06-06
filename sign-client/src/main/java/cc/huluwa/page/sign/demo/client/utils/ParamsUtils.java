package cc.huluwa.page.sign.demo.client.utils;


import java.lang.reflect.Field;

public class ParamsUtils {

    /**
     * 获取get参数类型
     * @param obj
     * @return
     */
    public static String toGetParams(Object obj) {

        StringBuffer stringBuffer = new StringBuffer();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                String key = field.getName();
                if (value != null) {
                    stringBuffer.append(key+"="+value).append("&");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String params = stringBuffer.toString().substring(0,stringBuffer.length()-1);

        return params;
    }
}
