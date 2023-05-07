package org.example.common.excel.config;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class ExcelWriteConfig {

    public String subPath;
    public String realFileName;
    public String viewFileName;

    /**
     * template 파일 명
     */
    //public String templateFileName;

    /**
     * 최대 access 행수 (30000만개씩 쪼개서 넣어야 한다. 그 이하는 상관 없음)
     */
    public int rowAccessWindwoSize = 3000;

    /**
     * 각 시트에 대한 조건
     */
    public List<ExcelSheetWriteConfig> excelSheetWriteConfigs = new ArrayList<>();
}
