package org.example.common.excel.config;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ExcelReadConfig {
    public String subPath;
    public String realFileName;
    public List<ExcelSheetReadConfig> sheetConfigs = new ArrayList<>();
}
