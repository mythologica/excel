package org.example.common.excel.config;

import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface ExcelFunction<T>{
    void parse(T data, Row row, int sheetIndex);
}
