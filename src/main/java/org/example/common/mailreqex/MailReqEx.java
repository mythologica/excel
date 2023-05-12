package org.example.common.mailreqex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailReqEx {
    private MailDataMatcher mailDataMatcher;

    public MailReqEx(MailDataMatcher mailDataMatcher) {
        this.mailDataMatcher = mailDataMatcher;
    }
    public String[] parseKeys( String findWord ) {
        int sidx = 2;
        int eidx = findWord.lastIndexOf("##");
        return findWord.substring(sidx,eidx).split("[.]");
    }

    public String parse(String src) throws Exception {
        Pattern pattern = Pattern.compile("(##[a-z]*.[0-9a-zA-Z-_]*##)");
        
        Matcher matcher = pattern.matcher(src);

        List<String> findKeys = new ArrayList<>();

        while(matcher.find()){
            String key = String.valueOf(matcher.group());
            findKeys.add(key);
        }

        String rpText = ""+src;
        for(String k:findKeys) {
            String[] temp = parseKeys(k);

//            System.out.print("groupId:"+ temp[0]);
//            System.out.print(",itemId:"+ temp[1]);
//            System.out.print(",value:"+ mailDataMatcher.getString(temp[0], temp[1]));
//            System.out.println("\n");

            rpText = rpText.replaceAll(k, mailDataMatcher.getString(temp[0], temp[1]));
        }
        return rpText;
    }
}
