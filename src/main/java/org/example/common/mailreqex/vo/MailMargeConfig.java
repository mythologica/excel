package org.example.common.mailreqex.vo;

import java.util.ArrayList;
import java.util.List;

public class MailMargeConfig {
    private List<String> findKeys = new ArrayList<>();
    private String html = "";

    public List<String> getFindKeys() {
        return findKeys;
    }

    public void setFindKeys(List<String> findKeys) {
        this.findKeys = findKeys;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
