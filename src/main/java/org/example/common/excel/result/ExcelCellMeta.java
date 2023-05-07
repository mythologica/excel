package org.example.common.excel.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExcelCellMeta {
    private int cellIndex;
    private int columnIndex;
    private String id;
    private String name;
}
