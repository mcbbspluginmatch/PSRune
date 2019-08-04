package me.wuxie.psrune.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    // 测试代码应删除或不上传至 git —— 754503921
    public static void main(String [] a){
        System.out.println((int)(9/2.7));
    }

    /**
     *  返回符文对应列id
     * @param str String
     * @return String
     */
    public static String[] getRuneRandId(String str){
        Iterator<String> strs= new ArrayList<>(Arrays.asList(str.split("<\\|psRune"))).iterator();
        StringBuffer sb = new StringBuffer();
        while (strs.hasNext()){
            String s = strs.next();
            if(s.contains("|>")){
                sb.append(s.split("\\|>")[0]);
                if(strs.hasNext()){
                    sb.append(",");
                }
            }
        }
        return sb.toString().split(",");
    }
}
