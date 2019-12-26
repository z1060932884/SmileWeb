package com.zzj.learn.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LambdaTest {

    
    public static void main(String[] args){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("1","a");
        hashMap.put("2","b");
        hashMap.put("3","c");

        for(Map.Entry<String,String> entry : hashMap.entrySet()){
            System.out.println(entry.getKey()+":" +entry.getValue());
        }

        //lambda表达式实现：
        hashMap.forEach((key,value) -> System.out.println(key+":"+value));


        System.out.println(toUpperString1(new MyNumber<String>() {
            @Override
            public String getNumber(String s) {
                return s;
            }
        },"bcd").toUpperCase());

        System.out.println(toUpperString(mn -> mn.toUpperCase(),"abc"));
    }

    //内置的函数接口
    public static String toUpperString(Function<String,String> mn, String str) {
        return mn.apply(str);

    }
        /**
         * 自定义函数接口
         */
        public static String toUpperString1(MyNumber<String> mn, String str) {
        return mn.getNumber(str);
    }
}
