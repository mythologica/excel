package test;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestJava {

    public void doTest() {
        test04();
    }

    private List<String> getFindKeys(String src) {
        Pattern pattern = Pattern.compile("(\\<!--\\@mailparser-config\\{[0-9a-zA-Z,]*\\}@-->)");
        Matcher matcher = pattern.matcher(src);
        List<String> findKeys = new ArrayList<>();
        if( matcher.find() ) {
            String[] tempStr = matcher.group().replaceAll("\\<!-\\-@mailparser-config\\{" ,"").replaceAll("\\}@-->","").split("[,]");
            for(String key:tempStr) {
                findKeys.add(key.trim());
            }
        }
        return findKeys;
    }

    public void test04() {
        String src = "<!--@mailparser-config{common,qrcode,markingOptOut}@--><!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>Title</title></head><body>    <img class=\"CLAJ3Pw2\" width=\"100%\" style=\"align-items: baseline\" src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png\" alt=\"Google\" loading=\"lazy\">    <span>홍길동</span>    <span>hglqhdytYIDFafdhjf</span>    <span>test@mail.com</span>    <a href=\"https://localhost:9090/markingOptOut/hglqhdytYIDFafdhjf\" target=\"_blank\">link</a></body></html>\n";
        List<String> findKeys = getFindKeys(src);
        for(String k:findKeys) {
            System.out.println("["+k+"]");
        }
    }

    public boolean isBoolean(String v){
        if( v == null || v.length() < 1 ){
            return false;
        }
        char t = v.toLowerCase().charAt(0);
        return 't' == t || '1' == t;
    }

    public void test03() {
        String[] list = {"True","true","1","0","false",null,"  ","r"};
        for(String v:list) {
            System.out.println( isBoolean(v) );
        }
    }

    private int getBlockSize(int totalCount, int blockCount) {
        return (totalCount/ 100) + (totalCount % 100 == 0 ? 0 : 1);
    }

    public void test02() {
        int maxSize = 123;
        List<String> list = new ArrayList<String>(maxSize);

        for(int i=0;i<maxSize;i++) {
            list.add(""+i);
        }

        List<List<String>> parts = ListUtils.partition(list,100);

        int blockCount = parts.size();

        System.out.println( list.size() +":"+ blockCount );

        int blockIndex = 0;
        for(List<String> part:parts) {
            for(String v:part) {
                System.out.println("block:"+blockIndex+"value:"+v);
            }
            blockIndex++;
        }
    }

    public void test01() {
        String a = "A";

        System.out.println(System.identityHashCode(a));

        List<String> list = new ArrayList<>();
        list.add(String.valueOf(a));
        System.out.println(System.identityHashCode(list.get(0)));
        System.out.println(System.identityHashCode("" + a));

    }



}
