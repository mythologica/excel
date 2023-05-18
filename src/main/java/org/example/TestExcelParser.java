package org.example;

import org.example.common.mailreqex.MailDataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestExcelParser {
    public void doTest() throws Exception {
        doAutoConfig();
    }

    public void doAutoConfig() throws Exception {
        String originalHtml = read();

        Map<String,String> defaultData = new LinkedHashMap<>();

        defaultData.put("userId","dkdkdkdkdkd"); //조회할 userId
        defaultData.put("qrcode.fromDate", "2023-05-13");
        defaultData.put("qrcode.toDate", "2023-05-20");

        TemplateMailMergeDataBuilder dataBuilder = new TemplateMailMergeDataBuilder(defaultData);

        String result = new MailDataParser(originalHtml).parse(dataBuilder);

        System.out.println("result:" + result);
    }

    private String read() {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources\\data\\a.html"));
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception ce) {
            }
        }

        return sb.toString();
    }
}