package org.example.common.mailreqex;

import org.example.common.mailreqex.vo.MailMergeConfig;
import org.example.common.mailreqex.vo.MailMergeData;
import org.example.common.mailreqex.vo.MailMergeFindKey;
import org.example.common.mailreqex.vo.MailParingData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailReqEx {

    private final static String  CONFIG_LINE_REGEX = "(\\<!--\\@mailparser-config\\{[0-9a-zA-Z,\\s]*}@-->)";

    private final static String CONFIG_LINE_PREFIX = "\\<!-\\-@mailparser-config\\{";

    private final static String CONFIG_LINE_LASTFIX = "\\}@-->";

    private final static String  ITEM_REGEX = "(##[0-9a-zA-Z]*.[0-9a-zA-Z-_]*##)";

    public static MailParingData parseConfigLine(String originalHtml) {
        Pattern keyPattern = Pattern.compile(CONFIG_LINE_REGEX);
        Matcher matcher = keyPattern.matcher(""+originalHtml);
        List<MailMergeFindKey> parseKeys = new ArrayList<>();
        String configStr = "";
        if (matcher.find()) {
            configStr = "" + matcher.group();
            String[] tempStr = matcher.group().replaceAll(CONFIG_LINE_PREFIX, "").replaceAll(CONFIG_LINE_LASTFIX, "").split("[,]");
            for (String key : tempStr) {
                String tmpText = key.trim();

                System.out.println("tmpText:"+tmpText);

                if (tmpText.startsWith(MailMergeConfig.DB_KEY)) {
                    String dbKey = tmpText.replaceAll(MailMergeConfig.DB_KEY, "");
                    parseKeys.add(new MailMergeFindKey("key", dbKey));
                } else {
                    parseKeys.add(new MailMergeFindKey(tmpText, ""));
                }
            }
        }
        MailParingData mailParingData = new MailParingData( originalHtml );

        mailParingData.setMailHtml(originalHtml.substring(configStr.length())); // config line을 제거한 나머지 html

        MailMergeConfig mailMergeConfig = new MailMergeConfig();
        mailMergeConfig.setMailMergeFindKeys(parseKeys);
        mailParingData.setMailMargeConfig(mailMergeConfig);

        return mailParingData;
    }

    private static String[] parseKeys( String findWord ) {
        int sidx = 2;
        int eidx = findWord.lastIndexOf("##");
        return findWord.substring(sidx,eidx).split("[.]");
    }

    public static String parse(String mailHtml, MailMergeData mailMergeData) throws Exception {
        Pattern pattern = Pattern.compile(ITEM_REGEX);
        Matcher matcher = pattern.matcher(mailHtml);

        List<String> findKeys = new ArrayList<>();

        while(matcher.find()){
            findKeys.add(""+matcher.group());
        }

        System.out.println("\n\n=========================================");


        String rpText = ""+mailHtml;
        for(String k:findKeys) {
            String[] temp = parseKeys(k);

            System.out.println("temp:"+temp[0]+"."+temp[1]);
            System.out.println("value:"+ mailMergeData.getString(temp[0], temp[1]));

            rpText = rpText.replaceAll(k, mailMergeData.getString(temp[0], temp[1]));
        }

        System.out.println("\n\n=========================================");
        return rpText;
    }
}
