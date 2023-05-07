package org.example.common.excel.result;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class ExcelRowMeta {
    private Map<Integer,ExcelCellMeta> indexs = new HashMap<>();
    private Map<String,ExcelCellMeta> ids = new HashMap<>();
    private Map<String,ExcelCellMeta> names = new HashMap<>();
    private List<ExcelCellMeta> cellMetas = new ArrayList<>();

    private List<Integer> columnIndexs = new ArrayList<>();
    private List<String> columnIds = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();

    public ExcelRowMeta addCellMeta(int cellIndex , String id , String name) {
        ExcelCellMeta cellMeta = ExcelCellMeta.builder()
                                                .cellIndex(cellIndex)
                                                .id(id)
                                                .name(name)
                                                .build();
        this.indexs.put(cellIndex,cellMeta);
        this.ids.put(id,cellMeta);
        this.names.put(name,cellMeta);
        this.cellMetas.add(cellMeta);

        int columnIndex = this.columnIndexs.size();

        this.columnIndexs.add(columnIndex);
        this.columnIds.add(id);
        this.columnNames.add(name);

        return this;
    }

    public int getCellCount() {
        return this.cellMetas.size();
    }

    public ExcelCellMeta getColumnByIndex(int index) {
        return this.indexs.get(index);
    }

    public ExcelCellMeta getColumnById(String id) {
        return this.ids.get(id);
    }
    public ExcelCellMeta getColumnByName(String name) {
        return this.names.get(name);
    }
}
