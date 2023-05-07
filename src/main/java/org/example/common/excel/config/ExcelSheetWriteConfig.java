package org.example.common.excel.config;

import lombok.Builder;
import lombok.Getter;

//import org.apache.poi.ss.usermodel.Cell;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ExcelSheetWriteConfig<T> {
    private String sheetName;
    private String[] cellHeader;
    private List<T> rows = new ArrayList<>();
    private ExcelFunction<T> parseer;
//    public void parseExcelRow(int writeSheetIndex, Row row, T data);

}
