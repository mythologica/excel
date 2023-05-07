package org.example.common.excel.result;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.common.excel.config.ExcelSheetReadConfig;
import org.example.common.excel.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@ToString(exclude = {"config"})
public class ExcelSheetData {
    private String sheetName;
    private List<List<String>> rows = new ArrayList<List<String>>();    // 실제 엑셀을 파싱해서 담아지는 데이터

    private ExcelSheetReadConfig config;
    public List<String> columns = new ArrayList<>(); // column 배열
    public List<List<String>> columnRows = new ArrayList<>();  // column  대비 데이터

    private int currentRowNumber = -1;
    private List<String> currentRow;

    public boolean hasNext() {
        return this.currentRowNumber + 1 < this.columnRows.size();
    }

    public int beforeRowIndex() {
        this.currentRowNumber = -1;
        return this.currentRowNumber;
    }

    public boolean setRow(int rowNumber) {
        if(this.rows.size() > rowNumber) {
            this.currentRowNumber = rowNumber;
            return true;
        }
        return false;
    }

    public boolean next() {
        int rowNum = this.currentRowNumber + 1;
        if(this.rows.size() > rowNum && this.columnRows.get(rowNum) != null) {
            this.currentRowNumber = rowNum;
            this.currentRow = this.columnRows.get(rowNum);
            return true;
        }
        return false;
    }

    public String getString(int columnIndex) {
        return this.getString(columnIndex, null);
    }

    public String getString(String columnId) {
        return this.getString(columnId, null);
    }

    public String getString(int columnIndex, String defaultValue) {
        if( this.currentRow.size() > columnIndex ) {
            String value = this.currentRow.get(columnIndex);
            return value != null ? value : defaultValue;
        }
        return defaultValue;
    }

    public String getString(String columnId, String defaultValue) {
        int columnIndex = this.config.getRowMeta().getColumnIds().indexOf(columnId);
        String value = null;
        if( columnIndex != -1 ) {
            value = getString(columnIndex, defaultValue);
        }
        return value != null ? value : defaultValue;
    }

    public BigDecimal getBigDecimal(int columnIndex) {
        return getBigDecimal(columnIndex,null);
    }

    public BigDecimal getBigDecimal(String columnId) {
        return getBigDecimal(columnId,null);
    }

    public BigDecimal getBigDecimal(int columnIndex, BigDecimal defaultValue) {
        return CommonUtils.parseBigDecimal(getString(columnIndex,null),defaultValue);
    }

    public BigDecimal getBigDecimal(String columnId, BigDecimal defaultValue) {
        int columnIndex = this.config.getRowMeta().getColumnIds().indexOf(columnId);
        BigDecimal value = null;
        if( columnIndex != -1 ) {
            value = getBigDecimal(columnIndex, defaultValue);
        }
        return value != null ? value : defaultValue;
    }

    public boolean getBoolean(int columnIndex) {
        return getBoolean(columnIndex,false);
    }

    public boolean getBoolean(String columnId) {
        return getBoolean(columnId,false);
    }

    public boolean getBoolean(int columnIndex, boolean defaultValue) {
        return CommonUtils.parseBoolean(getString(columnIndex), defaultValue);
    }

    public boolean getBoolean(String columnId, boolean defaultValue) {
        return CommonUtils.parseBoolean(getString(columnId), defaultValue);
    }
}