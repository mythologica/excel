package org.example.common.excel;

import org.example.common.excel.config.ExcelWriteConfig;

public class ExcelWriteFactory {
    public static boolean write(ExcelWriteConfig config) throws Exception {
        ExcelWriteHandler excelWriteHandler = null;
        try {
            excelWriteHandler = new ExcelWriteHandler(config);
            excelWriteHandler.write();
        } catch (Exception e) {
            // 에러 발생했을때 하시고 싶은 TO-DO
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
