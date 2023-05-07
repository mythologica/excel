package org.example.common.excel;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.binary.XSSFBSheetHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.example.common.excel.config.ExcelSheetReadConfig;

public class ExcelSheetHandler implements SheetContentsHandler {

    private int currentCol = -1;
    private int currRowNum = 0;

    String filePath = "";

    private final List<String> excelSheetNames = new ArrayList<String>();

    private String sheetName = "";

    private final List<List<String>> columnRows = new ArrayList<>();

    private final List<List<String>> rows = new ArrayList<List<String>>();    // 실제 엑셀을 파싱해서 담아지는 데이터
    private final List<String> row = new ArrayList<String>();
    private List<String> header = new ArrayList<String>();

    private ExcelSheetReadConfig config;

    public void setReadConfig(ExcelSheetReadConfig config) {
        this.config = config;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public List<List<String>> getRows() {
        return this.rows;
    }

    public List<List<String>> getColumnRows() { return this.columnRows; }

    @Override
    public void startRow(int arg0) {
        this.currentCol = -1;
        this.currRowNum = arg0;
    }

    @Override
    public void cell(String columnName, String value, XSSFComment var3) {
        int iCol = (new CellReference(columnName)).getCol();
        int emptyCol = iCol - this.currentCol - 1;

        //TODO: config에 따라 start , end를 제어한다.
        //TODO: end는 데이터 양이 많으면 end 기준으로 데이터 양이 적으면 기존 로직 그대로 적용 -1일 경우 해당 로직은 그대로 세팅한다.

        for (int i = 0; i < emptyCol; i++) {
            this.row.add("");
        }
        this.currentCol = iCol;
        this.row.add(value);
    }

    @Override
    public void endRow(int rowNum) {

        if (rowNum < this.config.getReadRowStartIndex()) {
            this.header = new ArrayList(row);
        } else {
            if (this.row.size() < this.header.size()) {
                for (int i = this.row.size(); i < this.header.size(); i++) {
                    this.row.add("");
                }
            }

            List<String> currCells = new ArrayList<String>(this.row);

            this.rows.add(currCells);

            int sidx = this.config.getReadColumnStartIndex();
            int eidx = this.config.getReadColumnEndIndex() > 0 ? this.config.getReadColumnEndIndex() : currCells.size();

            List<String> columnRow = new ArrayList<>();
            for(int c=0;c<currCells.size();c++) {
                if( c >= sidx && c <= eidx ) {
                    columnRow.add( String.valueOf(currCells.get(c)) );
                }
            }
            this.columnRows.add(columnRow);
        }
        row.clear();
    }

    @Override
    public void hyperlinkCell(String arg0, String arg1, String arg2, String arg3, XSSFComment arg4) {
        // TODO Auto-generated method stub
    }

}