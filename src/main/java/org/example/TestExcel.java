package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.common.excel.ExcelReadFactory;
import org.example.common.excel.ExcelWriteFactory;
import org.example.common.excel.config.ExcelReadConfig;
import org.example.common.excel.config.ExcelSheetReadConfig;
import org.example.common.excel.config.ExcelSheetWriteConfig;
import org.example.common.excel.config.ExcelWriteConfig;
import org.example.common.excel.result.ExcelData;
import org.example.common.excel.result.ExcelRowMeta;
import org.example.common.excel.result.ExcelSheetData;
import org.example.common.excel.utils.CommonUtils;
import org.example.vo.OfficeVO;
import org.example.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    public void doTest() throws Exception {
        TestExcel testExcel = new TestExcel();
        testExcel.doTestWrite();
        testExcel.doTestRead();
    }

    private List<UserVO> getUsers() {
        List<UserVO> list = new ArrayList<>();

        list.add(UserVO.builder().regNo("1").name("홍길동").age(18).build());
        list.add(UserVO.builder().regNo("2").name("강감찬").age(50).build());
        list.add(UserVO.builder().regNo("3").name("이순신").age(40).build());
        list.add(UserVO.builder().regNo("4").name("김철수").age(9).build());

        return list;
    }

    private List<OfficeVO> getOffices() {
        List<OfficeVO> list = new ArrayList<>();

        list.add(OfficeVO.builder().regNo("1").officeName("아이파크").build());
        list.add(OfficeVO.builder().regNo("2").officeName("래미안").build());

        return list;
    }


    public void doTestWrite() {
        System.out.println("Excel Write");
        try {
            //String fullFileName = "\\data\\writefile_" + ExcelUtils.getYYYYMMDDHH24MISSMS() + "_.xlsx";
            String subPath = "\\data\\";
            String viewFileName = "writefile.xlsx";
            String realFileName = "writefile_" + CommonUtils.getYYYYMMDDHH24MISSMS() + ".xlsx";

            // TODO Auto-generated method stub

            // WriteConfig
            List<ExcelSheetWriteConfig> sheetConfigs = new ArrayList<>();

            // User Config
            ExcelSheetWriteConfig<UserVO> userConfig = new ExcelSheetWriteConfig<UserVO>();

            userConfig.setSheetName("유저");
            userConfig.setCellHeader(new String[]{"regNo", "name", "age"});
            userConfig.setRows(getUsers());
            userConfig.setParseer((UserVO data, Row row, int sheetIndex) -> {
                System.out.println("sheetIndex:" + sheetIndex);
                System.out.println("row:" + row);
                System.out.println("data:" + data);

                // 엑셀 cell 생성 및 값 주입
                Cell cell = row.createCell(0);
                cell.setCellValue(data.getRegNo());
                cell = row.createCell(1);
                cell.setCellValue(data.getName());
                cell = row.createCell(2);
                cell.setCellValue(data.getAge());
            });
            sheetConfigs.add(userConfig);

            // Office Config
            ExcelSheetWriteConfig<OfficeVO> officeConfig = new ExcelSheetWriteConfig<OfficeVO>();

            officeConfig.setSheetName("회사");
            officeConfig.setCellHeader(new String[]{"regNo", "Office"});
            officeConfig.setRows(getOffices());
            officeConfig.setParseer((OfficeVO data, Row row, int sheetIndex) -> {
                System.out.println("sheetIndex:" + sheetIndex);
                System.out.println("row:" + row);
                System.out.println("data:" + data);

                // 엑셀 cell 생성 및 값 주입
                Cell cell = row.createCell(0);
                cell.setCellValue(data.getRegNo());
                cell = row.createCell(1);
                cell.setCellValue(data.getOfficeName());
            });
            sheetConfigs.add(officeConfig);

            ExcelWriteConfig config = ExcelWriteConfig.builder()
                    .subPath(subPath)
                    .viewFileName(viewFileName)
                    .realFileName(realFileName)
                    .excelSheetWriteConfigs(sheetConfigs)
                    .build();

            boolean isWrite = ExcelWriteFactory.write(config);
            System.out.println("Excel Reader write " + isWrite);
            if (isWrite) {
                System.out.println(" write ok ");
            } else {
                System.out.println(" write fail ");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Excel Write end");
        }

    }

    public void doTestRead() {
        System.out.println("------------------------------------ Excel Read start");
        try {
            String subPath = "\\data\\";
            String realFileName = "file.xlsx";

            // TODO Auto-generated method stub
            List<ExcelSheetReadConfig> sheetConfigs = new ArrayList<>();
            sheetConfigs.add(ExcelSheetReadConfig.builder()
                    .rowMeta(new ExcelRowMeta()
                            .addCellMeta(0,"no","NO")
                            .addCellMeta(1,"name","Name")
                            .addCellMeta(2,"age","Age")
                            .addCellMeta(3,"work","Work"))
                    .readRowStartIndex(3)
                    .build());
            sheetConfigs.add(ExcelSheetReadConfig.builder()
                    .readRowStartIndex(1)
                    .rowMeta(new ExcelRowMeta()
                            .addCellMeta(0,"no","NO")
                            .addCellMeta(1,"work","Work"))
                    .build());

            ExcelReadConfig config = ExcelReadConfig.builder()
                    .subPath(subPath)
                    .realFileName(realFileName)
                    .sheetConfigs(sheetConfigs)
                    .build();

            System.out.println("Excel Reader config:" + config);

            ExcelData excelData = ExcelReadFactory.read(config);

            System.out.println("Excel Reader result:" + excelData);

            if (excelData.getSheets().size() == 0) {
                System.out.println(" excel sheets is null ");
            } else {
                System.out.println(" excel sheets count is " + excelData.getSheets().size());
                for (ExcelSheetData sheet : excelData.getSheets()) {
                    System.out.println("SheetName:" + sheet.getSheetName());
                    System.out.println("Rows List" + sheet.getRows());
                    System.out.println("Column Rows List" + sheet.getColumnRows());
                }
                for (ExcelSheetData sheet : excelData.getSheets()) {
                    //sheet.beforeRowIndex();
                    /*
                    System.out.println("getColumnRows:"+ sheet.getColumnRows());
                    System.out.println("getColumnIds:"+ sheet.getConfig().getRowMeta().getColumnIds());
                    */
                    while ( sheet.next() ) {
                        for(String colId:sheet.getConfig().getRowMeta().getColumnIds()) {
                            System.out.print("\t" +colId + ":" + sheet.getString(colId) );
                        }
                        System.out.println("\n");
                    }
                    System.out.println("-----------------------------------------------------------------------------\n");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("------------------------------------ Excel Read end");
        }

    }
}
