package cc.huluwa.page.sign.demo.client.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MD5Utils {

    /**
     *
     * 加密规则：
     * 1.将对象转成Map集合，以属性名为key，属性值为value(sign字段不要放进去)
     * 2.将Map集合进行排序，属性名称第一个字母靠前的排在前面，(如果首字母相同，则比较第二位)
     * 如： name:Tom  sex:男 age:18  则map集合键值对顺序依次为 age:18 name:Tom sex:男
     * 如： name:Tom named:Bob  age:18 agd:20   则排序结果为：name ->named ->agd ->age
     * 3.将map结合的键值对拼接成age=18&name=Tom&sex=男
     * 4.将拼接后得到的字符串转大写得到  AGE=18&NAME=TOM&SEX=男
     * 5.将转换大写得到的字符串再拼接秘钥(也就是appSecret)后得到 AGE=18&NAME=TOM&SEX=男&key=123456789
     * 6.使用Md5和秘钥将得到后的结果进行加密得到的结果就是最终的签名啦
     * 7.将得到的签名字符串去和服务端传过来的sign属性值进行忽略大小写的比较，如果相等，则拿到的值未被拦截和修改
     *
     */
    public static String getSign(Object object,String secret) throws IllegalAccessException {
        Map<String,Object> map = mapOrder(objToMap(object));

        String params = buildData(map).toUpperCase();

        params += "&key="+secret;

        return encrypt(params);
    }

    /**
     * 对象转Map 空值不添加
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> objToMap(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();

        Map<String,Object> map = new HashMap<String,Object>();
        for (int i = 0; i < fields.length; i++) {

            fields[i].setAccessible(true);

            String key = fields[i].getName();
            Object value = fields[i].get(object);

            if (value != null) {
                map.put(key,value.toString());
            }

        }
        return map;
    }

    /**
     * map按字母排序
     * @param map
     * @return
     */
    public static Map<String,Object> mapOrder( Map<String,Object> map) {
        if(map == null || map.isEmpty()) {
            return null;
        }
        Map<String,Object> sortMap = new TreeMap<String,Object>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 拼接数据
     * @return
     */
    private static String buildData(Map<String,Object> map) {
        String str = "";
        Map<String,Object> resultMap = map;
        Iterator<String> it = resultMap.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            Object value = resultMap.get(key);
            str += key + "=" + value +"&";
        }
        str = str.substring(0,str.length() - 1);
        return str;
    }

    public final static String encrypt(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        byte [] byteInput = s.getBytes();
        //获取MD5摘要算法的MessageDigest对象
        try {
            MessageDigest mdInst = MessageDigest.getInstance("md5");
            //使用指定的字节更新摘要
            mdInst.update(byteInput);
            //获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j *2];  //char占两个字节
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];  //右移四位，高四位清空 取低四位的值
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
