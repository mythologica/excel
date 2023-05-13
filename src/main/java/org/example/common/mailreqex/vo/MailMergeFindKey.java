package org.example.common.mailreqex.vo;

import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class MailMergeFindKey {
    private String findKey;
    private String dbKey;
    private boolean dbFlag = false;

    public MailMergeFindKey(String findKey,String dbKey) {
        this.findKey = findKey;
        this.dbKey = dbKey;
        if( this.dbKey != null && this.dbKey.length() > 0 ) {
            this.dbFlag = true;
        }
    }

    public String getFindKey() {
        return findKey;
    }

    public void setFindKey(String findKey) {
        this.findKey = findKey;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public boolean isDbFlag() {
        return this.dbFlag;
    }
}
