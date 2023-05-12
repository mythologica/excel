package org.example.common.mailreqex.vo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MailItemData {
    private LinkedHashMap<String,String> data = new LinkedHashMap<>();

    public void put(String itemId,String value) {
        data.put(itemId,value);
    }

    public boolean containsKey(String itemId) {
        return data.containsKey(itemId);
    }

    public String getString(String itemId,String defaultValue) {
        if( data.containsKey(itemId) ) {
            return data.get(itemId);
        }
        return defaultValue;
    }

    public MailItemData clone() {
        MailItemData mailItemData = new MailItemData();

        Iterator<String> itr = this.data.keySet().iterator();

        while (itr.hasNext()) {
            String key = ""+itr.next();
            mailItemData.put(key,(""+this.getString(key,"")));
        }

        return mailItemData;
    }
}
