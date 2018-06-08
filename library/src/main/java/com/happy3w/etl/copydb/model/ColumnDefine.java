package com.happy3w.etl.copydb.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnDefine {
    private String name;
    private String dataType;
}
