package org.example.common.excel.config;

import lombok.Builder;
import lombok.Getter;
import org.example.common.excel.result.ExcelRowMeta;

@Builder
@Getter
public class ExcelSheetReadConfig {
    /**
     * row meta
     */
    private ExcelRowMeta rowMeta = new ExcelRowMeta();
    /**
     * 시트의 시작 행의 위치
     */
    private int readRowStartIndex = 0;
    /**
     * 한행의 읽을 시작 컬럼 위치
     */
    private int readColumnStartIndex = 0;
    /**
     * 한행의 읽을 마지막 컬럼 위치
     * 0이면 parser에서 판단한다 1이상이면 선언된 내용으로 마지막 위치를 정한다.
     */
    private int readColumnEndIndex = 0;
}
