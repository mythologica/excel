package org.example.common.mailreqex.vo;

import java.util.ArrayList;
import java.util.List;

public class MailMergeConfig {
    public static String USER = "user";
    public static String QRCODE = "qrcode";
    public static String MARKING_OPT_OUT = "markOptOut";
    public static String DB_KEY = "key:";

    private MailMergeFindKey mailMergeFindKeysByUser;
    private MailMergeFindKey mailMergeFindKeysyQrCode;
    private MailMergeFindKey mailMergeFindKeysOptOut;

    private List<MailMergeFindKey> etcMailMergeFindKeys = new ArrayList<>();

    private List<MailMergeFindKey> mailMergeFindKeys = new ArrayList<>();

    public List<MailMergeFindKey> getMailMergeFindKeys() {
        return mailMergeFindKeys;
    }

    public MailMergeFindKey getMailMergeFindKeysByUser() {
        return this.mailMergeFindKeysByUser;
    }


    public MailMergeFindKey getMailMergeFindKeysyQrCode() {
        return this.mailMergeFindKeysyQrCode;
    }

    public MailMergeFindKey getMailMergeFindKeysOptOut() {
        return this.mailMergeFindKeysOptOut;
    }

    public List<MailMergeFindKey> getEtcMailMergeFindKeys() {
        return this.etcMailMergeFindKeys;
    }

    public void setMailMergeFindKeys(List<MailMergeFindKey> mailMergeFindKeys) {
        for(MailMergeFindKey mailMergeFindKey:mailMergeFindKeys) {
            if(MailMergeConfig.USER.equals(mailMergeFindKey.getFindKey())) {
                this.mailMergeFindKeysByUser = mailMergeFindKey;
            } else if(MailMergeConfig.QRCODE.equals(mailMergeFindKey.getFindKey())) {
                this.mailMergeFindKeysyQrCode = mailMergeFindKey;
            } else if(MailMergeConfig.MARKING_OPT_OUT.equals(mailMergeFindKey.getFindKey())) {
                this.mailMergeFindKeysOptOut = mailMergeFindKey;
            } else {
                this.etcMailMergeFindKeys.add(mailMergeFindKey);
            }
        }

        // 순서를 맞워야 해서
        if( this.mailMergeFindKeysByUser != null ) {
            this.mailMergeFindKeys.add(this.mailMergeFindKeysByUser);
        }

        if( this.mailMergeFindKeysyQrCode != null ) {
            this.mailMergeFindKeys.add(this.mailMergeFindKeysyQrCode);
        }

        if( this.mailMergeFindKeysOptOut != null ) {
            this.mailMergeFindKeys.add(this.mailMergeFindKeysOptOut);
        }

        this.mailMergeFindKeys.addAll(etcMailMergeFindKeys);

        System.out.println("mailMergeFindKeysOptOut"+mailMergeFindKeysOptOut);
    }
}
