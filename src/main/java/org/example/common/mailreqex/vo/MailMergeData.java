package org.example.common.mailreqex.vo;

import lombok.ToString;
import org.apache.commons.collections4.MapUtils;
import org.example.common.mailreqex.MailMargeFunction;

import java.math.BigDecimal;
import java.util.*;

@ToString
public class MailMergeData {
    private Map<String, Map<String,String>> dataMap = new LinkedHashMap<>();

    public void put(String groupId, String itemId, String value) {
        Map<String,String> itemData = null;
        if (dataMap.containsKey(groupId)) {
            itemData = dataMap.get(groupId);
            itemData.put(itemId, value);
        } else {
            itemData = new LinkedHashMap<>();
            itemData.put(itemId, value);
            dataMap.put(groupId, itemData);
        }
    }

    public List<String> getGroupIds() {
        List<String> ids = new ArrayList<>();
        for(String k:dataMap.keySet()){
            ids.add(k);
        }
        return ids;
    }

    public List<String> getItemIds(String groupId){
        List<String> ids = new ArrayList<>();
        if( dataMap.containsKey(groupId) ) {
            Map<String,String> itemMap = dataMap.get(groupId);
            for(String k:itemMap.keySet()){
                ids.add(k);
            }
        }
        return ids;
    }

    public boolean containsKey(String groupId, String itemId) {
        return dataMap.containsKey(groupId) && dataMap.get(groupId).containsKey(itemId);
    }

    public String getString(String groupId, String itemId) {
        return getString(groupId, itemId, "");
    }

    public String getString(String groupId, String itemId, String defaultValue) {
        if (containsKey(groupId, itemId)) {
            return MapUtils.getString(dataMap.get(groupId),itemId,defaultValue);
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

    public MailMergeData merge(MailMargeFunction mailMargeFunction) {
        mailMargeFunction.init(this);
        return this;
    }

    public MailMergeData clone() {
        MailMergeData itemData = new MailMergeData();
        for(String groupId:getGroupIds()) {
            Map<String,String> itemMap = dataMap.get(groupId);
            for(String itemId:getItemIds(groupId)) {
                itemData.put(groupId,itemId,itemMap.get(itemId));
            }
        }
        return itemData;
    }
}
