package com.happy3w.etl.copydb.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSetDefine {
    private String table;
    private String sql;
}
