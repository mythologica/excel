package org.example.common.excel.result;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Builder(toBuilder = true)
@Getter
@ToString
public class ExcelData {
    private List<ExcelSheetData> sheets = new ArrayList<ExcelSheetData>();

    public List<ExcelSheetData> getSheets() {
        for (ExcelSheetData sheet : sheets) {
            sheet.beforeRowIndex();
        }
        return this.sheets;
    }

    public Optional<ExcelSheetData> getSheet(int sheetIndex) {
        if (this.sheets.size() > sheetIndex) {
            ExcelSheetData sheetData = this.sheets.get(sheetIndex);
            sheetData.beforeRowIndex();
            return Optional.of(sheetData);
        }
        return Optional.empty();
    }

    public Optional<ExcelSheetData> getSheet(String sheetName) {
        int idx = -1;
        for (ExcelSheetData sheet : sheets) {
            if (sheet.getSheetName().equals(sheetName)) {
                return getSheet(idx);
            }
            idx++;
        }
        return Optional.empty();
    }

}
