package org.example.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.example.common.excel.config.ExcelSheetWriteConfig;
import org.example.common.excel.config.ExcelWriteConfig;
import org.example.common.excel.result.ExcelWriteData;
import org.example.common.excel.utils.ExcelUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriteHandler {
    private String templateFileName;

    private final ExcelWriteConfig config;

    public ExcelWriteHandler(ExcelWriteConfig config) {
        this.config = config;
    }

    public ExcelWriteData write() throws Exception {
        FileOutputStream fos = null;

        // 워크북
        SXSSFWorkbook workbook = null;
        // 행
        SXSSFRow row = null;
        // 셀
        SXSSFCell cell = null;
        // 샐 스타일
        CellStyle styleMoneyFormat = null;


        // 엑셀 헤더 정보 구성
        String[] cellHeader;// = {"번호", "테스트", "날짜", "시간"};

        try {
            // 워크북 생성
            workbook = new SXSSFWorkbook();
            workbook.setCompressTempFiles(true);

            List<ExcelSheetWriteConfig> sheetWriteConfigs = this.config.getExcelSheetWriteConfigs();

            int sheetIndex = 0;

            for( ExcelSheetWriteConfig sheetConfig : sheetWriteConfigs) {
                // 셀 헤더 카운트
                int cellHeaderIndex = 0;

                // 행 카운트
                int rowIndex = 1;

                // 워크시트 생성
                SXSSFSheet sheet = workbook.createSheet(sheetConfig.getSheetName());
                sheet.setRandomAccessWindowSize(3000); // 메모리 행 100개로 제한, 초과 시 Disk로 flush

                //셀 칼럼 크기 설정
                //sheet.setColumnWidth(2, 300);
                // 행 생성
                //row = sheet.createRow(0);

                row = sheet.createRow(0);

                // 셀 스타일 생성
                styleMoneyFormat = workbook.createCellStyle();
                CreationHelper ch = workbook.getCreationHelper();
                styleMoneyFormat.setDataFormat(ch.createDataFormat().getFormat("#,##0"));

                // 헤더 적용
                String[] headers = sheetConfig.getCellHeader();
                for (String head :headers) {
                    cell = row.createCell(cellHeaderIndex++);
                    cell.setCellValue(head);
                }
                for (Object obj: sheetConfig.getRows()) {
                    row = sheet.createRow(rowIndex);
                    sheetConfig.getParseer().parse( obj , row , sheetIndex );
                    rowIndex++;
                }
            }

            /*
            String filename = "파일명.xlsx";
            String orgFileName = "TEST_TEMP_FILE_01_.xlsx"; // 서버저장파일명
            String fileDownLoadPath = "/home/excelTemp/"
            */

            // 파일생성
            fos = new FileOutputStream(ExcelUtils.getWritePath(config.subPath) + config.realFileName);
            workbook.write(fos);

            return ExcelWriteData.builder()
                    .filePath(config.subPath)
                    .realFilNm(config.realFileName)
                    .viewFileNm(config.viewFileName)
                    .build();

        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) try {
                fos.close();
            } catch (Exception ignore) {
            }
            try {
                workbook.close();
                workbook.dispose();
                if (fos != null) try {
                    fos.close();
                } catch (Exception ignore) {
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

        }
    }
}
