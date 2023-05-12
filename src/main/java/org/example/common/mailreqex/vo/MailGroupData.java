package org.example.common.mailreqex.vo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MailGroupData {
    private Map<String, MailItemData> groupData = new LinkedHashMap<>();

    public void put(String groupId, String itemId, String value) {
        MailItemData mailItemData = null;
        if (groupData.containsKey(groupId)) {
            mailItemData = groupData.get(groupId);
            mailItemData.put(itemId, value);
        } else {
            mailItemData = new MailItemData();
            mailItemData.put(itemId, value);
            groupData.put(groupId, mailItemData);
        }
    }

    public boolean containsKey(String groupId, String itemId) {
        return groupData.containsKey(groupId) && groupData.get(groupId).containsKey(itemId);
    }

    public String getString(String groupId, String itemId) {
        return getString(groupId, itemId, "");
    }

    public String getString(String groupId, String itemId, String defaultValue) {
        if (containsKey(groupId, itemId)) {
            return groupData.get(groupId).getString(itemId, defaultValue);
        }
        return defaultValue;
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

    protected void putItem(String groupId,MailItemData mailItemData) {
        this.groupData.put(groupId,mailItemData);
    }

    public MailGroupData clone() {
        MailGroupData mailGroupData = new MailGroupData();

        Iterator<String> itr = this.groupData.keySet().iterator();

        while (itr.hasNext()) {
            String k = ""+itr.next();
            mailGroupData.putItem(k,this.groupData.get(k).clone());
        }

        return mailGroupData;
    }
}
