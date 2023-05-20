package org.example.common.preview;

import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PreviewMergeData {
    private final Map<String, Map<String,String>> dataMap = new LinkedHashMap<>();

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

    public PreviewMergeData merge(PreviewMergeFunction previewMergeFunction) {
        return previewMergeFunction.init(this);
    }
}
