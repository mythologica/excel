package org.example.common.mailreqex.vo;

import java.util.ArrayList;
import java.util.List;

public class MailMargeConfig {
    private List<String> parseKeys = new ArrayList<>();
    private String html = "";

    public List<String> getParseKeys() {
        return parseKeys;
    }

    public void setParseKeys(List<String> parseKeys) {
        this.parseKeys = parseKeys;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
