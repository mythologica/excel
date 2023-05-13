package org.example.common.mailreqex.vo;

import java.util.ArrayList;
import java.util.List;

public class MailParingData {
    private String originalHtml;
    private String mailHtml;

    private MailMergeConfig mailMergeConfig;


    public MailParingData(String originalHtml) {
        this.originalHtml = originalHtml;
    }

    public String getOriginalHtml() {
        return ""+originalHtml;
    }

    public String getMailHtml() {
        return ""+mailHtml;
    }

    public void setMailHtml(String mailHtml) {
        this.mailHtml = mailHtml;
    }

    public MailMergeConfig getMailMargeConfig() {
        return mailMergeConfig;
    }

    public void setMailMargeConfig(MailMergeConfig mailMergeConfig) {
        this.mailMergeConfig = mailMergeConfig;
    }
}
