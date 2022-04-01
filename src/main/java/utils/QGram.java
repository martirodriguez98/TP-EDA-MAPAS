package utils;

import java.util.HashMap;

public class QGram {

    private static int N;

    public QGram(int N){
        this.N=N;
    }

    private static HashMap<String,Integer> getTokens(String str){
        StringBuilder string = new StringBuilder();
        appends(string);
        string.append(str);
        appends(string);
        HashMap<String, Integer> map = new HashMap<>();
        int count;
        for(int i=0; i<string.length()-(N-1); i++){
            String key = makeKey(string,i);
            if(!map.containsKey(key)){
                map.put(key,1);
            }
            else{
                count = map.get(key);
                map.put(key, count+1);
            }
        }
        return map;
    }

    private static String makeKey(StringBuilder str, int index){
        StringBuilder string = new StringBuilder();
        for(int i=index; i<index+N;i++){
            string.append(str.charAt(i));
        }
        return string.toString();
    }

    private static void appends(StringBuilder str){
        for(int i=0; i<N-1; i++){
            str.append("#");
        }
    }

    public static Double similarity(String str1, String str2){
        HashMap<String,Integer> hm1 = getTokens(str1);
        HashMap<String,Integer> hm2 = getTokens(str2);
        int totalStr1 = 0;
        int totalStr2 = 0;
        for(String s : hm2.keySet())
            totalStr2 += hm2.get(s);
        int count = 0;
        for(String s1 : hm1.keySet()){
            totalStr1 += hm1.get(s1);
            for(String s2 : hm2.keySet()){
                if(s1.equals(s2)){
                    count+=Math.min(hm1.get(s1),hm2.get(s2));
                }
            }
        }
        return (totalStr1 + totalStr2 - (totalStr1 - count + totalStr2 - count)) * 1.0 / (totalStr1 + totalStr2);
    }

}