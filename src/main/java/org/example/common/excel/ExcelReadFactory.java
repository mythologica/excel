package org.example.common.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.example.common.excel.config.ExcelReadConfig;
import org.example.common.excel.config.ExcelSheetReadConfig;
import org.example.common.excel.result.ExcelData;
import org.example.common.excel.result.ExcelSheetData;
import org.example.common.excel.utils.ExcelUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReadFactory {

    public static ExcelData read(ExcelReadConfig config) throws Exception {

        String fullFileName = ExcelUtils.getWritePath(config.subPath)+config.realFileName;
        List<ExcelSheetReadConfig> sheetConfigs = config.sheetConfigs;

        List<ExcelSheetData> excelSheetData = new ArrayList<>();

        OPCPackage opc = null;
        List<ExcelSheetHandler> sheetHandlers = new ArrayList<ExcelSheetHandler>();
        try {
            File file = new File(fullFileName);

            // org.apache.poi.openxml4j.opc.OPCPackage
            opc = OPCPackage.open(file);

            // org.apache.poi.xssf.eventusermodel.XSSFReader
            XSSFReader xssfReader = new XSSFReader(opc);

            // org.apache.poi.xssf.model.StylesTable
            StylesTable styles = xssfReader.getStylesTable();

            // org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

            ExcelSheetHandler sheetHandler = null;
            InputStream inputStream = null;
            InputSource inputSource = null;
            ContentHandler handle = null;
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            int sheetIndex = 0;

            while (sheets.hasNext()) {

                // 엑셀의 시트를 하나만 가져오기입니다.
                // 여러개일경우 while문으로 추출하셔야 됩니다.
                try {
                    ExcelSheetReadConfig sheetReadConfig = sheetConfigs.get(sheetIndex);

                    inputStream = sheets.next();

                    // org.xml.sax.InputSource
                    inputSource = new InputSource(inputStream);

                    // org.xml.sax.Contenthandler
                    sheetHandler = new ExcelSheetHandler();
                    sheetHandler.setReadConfig(sheetReadConfig);
                    sheetHandler.setSheetName(sheets.getSheetName());
                    sheetHandlers.add(sheetHandler);
                    handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);

                    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                    saxParserFactory.setNamespaceAware(true);
                    SAXParser parser = saxParserFactory.newSAXParser();
                    XMLReader xmlReader = parser.getXMLReader();
                    xmlReader.setContentHandler(handle);

                    xmlReader.parse(inputSource);

                    excelSheetData.add(ExcelSheetData.builder()
                            .config(sheetReadConfig)
                            .sheetName(sheetHandler.getSheetName())
                            .rows(sheetHandler.getRows())
                            .columnRows(sheetHandler.getColumnRows())
                            .build());

                    sheetIndex++;

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }

        } catch (Exception e) {
            // 에러 발생했을때 하시고 싶은 TO-DO
        } finally {
            if (opc != null) {
                opc.close();
            }
        }

        //return sheetHandlers;
        return ExcelData.builder().sheets(excelSheetData).build();

    }


}
