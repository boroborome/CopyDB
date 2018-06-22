package com.happy3w.etl.copydb.model;

import com.happy3w.etl.copydb.model.type.AbstractDataTypeAdapter;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnDefine {
    private String name;
    private String dataType;
    private int size;
    private boolean autoIncrease;   // IS_AUTOINCREMENT
    private boolean nullable; // IS_NULLABLE

    public AbstractDataTypeAdapter findAdapter() {
        return AbstractDataTypeAdapter.parseByName(dataType);
    }
}
