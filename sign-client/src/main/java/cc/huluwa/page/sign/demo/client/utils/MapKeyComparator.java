package cc.huluwa.page.sign.demo.client.utils;

import java.util.Comparator;

/**
 * Md5Map排序规则
 */
public class MapKeyComparator implements Comparator<String> {

    public int compare(String str1, String str2) {
        return str1.compareTo(str2);   //升序排序
        //return str2.compareTo(str1);   降序排序
    }

}