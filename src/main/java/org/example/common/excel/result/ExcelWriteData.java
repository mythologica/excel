package org.example.common.excel.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExcelWriteData {
    private String filePath;
    private String realFilNm;
    private String viewFileNm;
}
