package org.example;

import org.apache.commons.lang3.StringUtils;
import org.example.common.mailreqex.MailDataMatcher;
import org.example.common.mailreqex.MailReqEx;
import org.example.common.mailreqex.vo.MailMargeConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailDataMatcherFactory {

    public static String COMMON = "common";
    public static String USER = "user";

    public static String QRCODE = "qrcode";
    public static String MARKING_OPT_OUT = "markingOptOut";

    private static MailMargeConfig getFindKeys(String src) {
        MailMargeConfig config = new MailMargeConfig();
        Pattern pattern = Pattern.compile("(\\<!--\\@mailparser-config\\{[0-9a-zA-Z,\\s]*}@-->)");
        Matcher matcher = pattern.matcher(src);
        List<String> findKeys = new ArrayList<>();
        String configStr = "";
        if( matcher.find() ) {
            configStr = ""+matcher.group();
            String[] tempStr = matcher.group().replaceAll("\\<!-\\-@mailparser-config\\{" ,"").replaceAll("\\}@-->","").split("[,]");
            for(String key:tempStr) {
                findKeys.add(key.trim());
            }
        }
        config.setHtml(src.substring(configStr.length()));
        config.setFindKeys(findKeys);
        return config;
    }

    public static String parse(String src) throws Exception {
        MailMargeConfig config = getFindKeys(src);
        List<String> parseKeys = config.getFindKeys();
        if( parseKeys.size() > 0 ) {
            return MailDataMatcherFactory.reqEx(parseKeys).parse(config.getHtml());
        } else {
            return src;
        }
    }

    public static String parse(String src,List<String> parseKeys) throws Exception {
        String html = getFindKeys(src).getHtml();
        return MailDataMatcherFactory.reqEx(parseKeys).parse(html);
    }

    public static MailReqEx reqEx(List<String> parseKeys) {
        return new MailReqEx(getMatcher(parseKeys));
    }

    public static MailDataMatcher getMatcher(List<String> parseKeys) {
        MailDataMatcher mailDataMatcher = new MailDataMatcher();

        if( parseKeys != null && parseKeys.size() > 0 ) {
            if( parseKeys.indexOf(USER) != -1 ) {
                initParsingDatasByUser(mailDataMatcher);
            }
            if( parseKeys.indexOf(QRCODE) != -1 ) {
                initParsingDatasByQrCode(mailDataMatcher);
            }
            if( parseKeys.indexOf(MARKING_OPT_OUT) != -1 ) {
                initParsingDatasByMarkingOptOut(mailDataMatcher);
            }
        }

        return mailDataMatcher;
    }

    private static MailDataMatcher initParsingDatasByUser(MailDataMatcher mailDataMatcher) {

        mailDataMatcher.put("user", "country", "kr");
        mailDataMatcher.put("user", "id", "hglqhdytYIDFafdhjf");
        mailDataMatcher.put("user", "name", "홍길동");
        mailDataMatcher.put("user", "email", "test@mail.com");

        return mailDataMatcher;
    }

    private static MailDataMatcher initParsingDatasByQrCode(MailDataMatcher mailDataMatcher) {

        mailDataMatcher.put("qrcode", "src", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        mailDataMatcher.put("qrcode", "srcBase64", "data:image/png;base64,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");
        mailDataMatcher.put("qrcode", "alt", "Google");
        mailDataMatcher.put("qrcode", "fromDate", "2023-05-12");
        mailDataMatcher.put("qrcode", "toDate", "2023-05-31");

        return mailDataMatcher;
    }

    private static MailDataMatcher initParsingDatasByMarkingOptOut(MailDataMatcher mailDataMatcher) {

        mailDataMatcher.put("link", "marking-opt-out", "https://localhost:9090/markingOptOut/##user.id##");
        mailDataMatcher.put("label", "marking-opt-out", "link");

        return mailDataMatcher.merge((mailGroupData) -> {
            String userId = mailGroupData.getString("user", "id");

            //값에서 치환해야 할 것 정의
            String link = mailGroupData.getString("link", "marking-opt-out").replaceAll("##user.id##", userId);
            mailGroupData.put("link", "marking-opt-out", link);
            return mailGroupData;
        });
    }
}
