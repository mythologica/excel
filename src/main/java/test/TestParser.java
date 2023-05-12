package test;

import org.example.MailDataMatcherFactory;
import org.example.common.mailreqex.MailReqEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class TestParser {
    public void doTest() throws Exception {
        doAutoConfig();
        doCustomConfig();
    }

    public void doAutoConfig() throws Exception {
        String src = read();

        String result = MailDataMatcherFactory.parse(src);
        System.out.println("result:" + result);
    }

    public void doCustomConfig() throws Exception {
        String src = read();

        List<String> parseKeys = Arrays.asList(MailDataMatcherFactory.QRCODE, MailDataMatcherFactory.MARKING_OPT_OUT);
        String result = MailDataMatcherFactory.parse(src,parseKeys);
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