package test;

import org.example.TemplateMailMergeDataBuilder;
import org.example.common.mailreqex.MailDataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestParser {
    public void doTest() throws Exception {
        doAutoConfig();
    }

    public void doAutoConfig() throws Exception {
        String originalHtml = read();

        Map<String,String> databaseKeys = new LinkedHashMap<>();

        databaseKeys.put("userId","dkdkdkdkdkd"); //조회할 userId

        TemplateMailMergeDataBuilder dataBuilder = new TemplateMailMergeDataBuilder(databaseKeys);

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