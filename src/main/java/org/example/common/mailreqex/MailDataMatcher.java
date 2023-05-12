package org.example.common.mailreqex;

import org.example.common.mailreqex.vo.MailGroupData;

import java.math.BigDecimal;

public class MailDataMatcher {

    private MailGroupData mailGroupData = new MailGroupData();

    private MailMargeFunction mailMargeFunction;

    public void put(String groupId, String itemId, String value) {
        this.mailGroupData.put(groupId, itemId, value);
    }

    public boolean containsKey(String groupId, String itemId) {
        return this.mailGroupData.containsKey(groupId, itemId);
    }

    public String getString(String groupId, String itemId, String defaultValue) {
        return this.mailGroupData.getString(groupId, itemId, defaultValue);
    }

    public MailDataMatcher merge(MailMargeFunction mailMargeFunction) {
        this.mailMargeFunction = mailMargeFunction;
        this.mailGroupData = this.mailMargeFunction.init(this.mailGroupData);
        return this;
    }

    public String getString(String groupId, String itemId) {
        return getString(groupId, itemId, "");
    }

    public BigDecimal getBigDecimal(String groupId, String itemId) {
        return getBigDecimal(groupId, itemId, new BigDecimal(0));
    }

    public BigDecimal getBigDecimal(String groupId, String itemId, BigDecimal defaultValue) {
        if (containsKey(groupId, itemId)) {
            return new BigDecimal(getString(groupId, itemId, defaultValue.toPlainString()));
        }
        return defaultValue;
    }

    public boolean isBoolean(String groupId, String itemId) {
        return isBoolean(groupId, itemId, false);
    }

    public boolean isBoolean(String groupId, String itemId, boolean defaultValue) {
        if (containsKey(groupId, itemId)) {
            String v = getString(groupId, itemId, "");
            if (v == null || v.length() < 1) {
                return false;
            }
            char t = v.toLowerCase().charAt(0);
            return 't' == t || '1' == t;
        }
        return defaultValue;
    }

    public MailGroupData getMailGroupData() {
        return this.mailGroupData.clone();
    }
}


