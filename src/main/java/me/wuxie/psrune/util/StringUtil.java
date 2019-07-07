package me.wuxie.psrune.util;

import java.util.Random;

public class StringUtil {
    /**
     *  替换指定位置字符
     * @param index 位置
     * @param str String
     * @param regex String
     * @param replacement String
     * @return String
     */
    public static String replaceIndex(int index,String str,String regex,String replacement){
        if(index ==0){
            return str.replaceFirst(regex,replacement);
        }
        String sign = random("无邪好帅,看我代码，你好丑".toCharArray());
        for(int a = 0;a<index;){
            a+=1;
            str = replaceFirst(str,regex,sign);
        }
        str = replaceFirst(str,regex,replacement);
        return str.replace(sign,regex);
    }
    /**
     * 获取字符串 s 在字符串 str 中出现的次数
     * @param str String
     * @param s String
     * @return int
     */
    public static int countMatches(String str, String s) {
        if (!isEmpty(str) && !isEmpty(s)) {
            int a = 0;
            for(int v = 0; (v = indexOf(str, s, v)) != -1; v += s.length()) {
                ++a;
            }
            return a;
        } else {
            return 0;
        }
    }

    /**
     *  字符串是否为空
     * @param str  String
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private static int indexOf(CharSequence str, CharSequence s, int v) {
        return str.toString().indexOf(s.toString(), v);
    }
    private static final Random RANDOM = new Random();
    private static String random(int end, char[] chars) {
        int start = 0;
        if ((end == 0)) {
            end = Integer.MAX_VALUE;
        }
        char[] buffer = new char[4];
        int gap = end - start;
        int count = 4;
        while (count-- != 0) {
            char ch;
            if (chars == null) ch = (char) (StringUtil.RANDOM.nextInt(gap) + start);else ch = chars[StringUtil.RANDOM.nextInt(gap) + start];
            if(ch >= 56320 && ch <= 57343) {
                if(count == 0) count++;else {
                    buffer[count] = ch;
                    count--;
                    buffer[count] = (char) (55296 + StringUtil.RANDOM.nextInt(128));
                }
            } else if(ch >= 55296 && ch <= 56191) {
                if(count == 0) count++;else {
                    buffer[count] = (char) (56320 + StringUtil.RANDOM.nextInt(128));
                    count--;
                    buffer[count] = ch;
                }
            } else if(ch >= 56192 && ch <= 56319) count++;else buffer[count] = ch;
        }
        return new String(buffer);
    }
    private static String random(char[] chars) {
        if (chars == null) return random( 0, null);
        return random(chars.length, chars);
    }
    public static String replaceFirst(String str,String s1,String s2){
        String sign = random("无邪好帅看我代码你好丑".toCharArray());
        return str.replace(s1,sign).replaceFirst(sign,s2).replace(sign,s1);
    }
}
