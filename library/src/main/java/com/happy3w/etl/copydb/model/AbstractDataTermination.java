/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.happy3w.etl.copydb.model;

import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This define DataTermination which can support save and load data function.
 * @author boroborome
 */
@Getter
public abstract class AbstractDataTermination {
    private final String type;

    protected AbstractDataTermination(String type) {
        this.type = type;
    }

    public abstract void initializeByString(String terminationString, IConfigSuppler configSuppler);
    public abstract void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) throws SQLException;
    public abstract DataSetValue read(String dataSetName, IConfigSuppler configSuppler) throws SQLException;
    public abstract DataSetValue read(DataSetDefine dataSetDefine) throws SQLException;
    public abstract void close() throws IOException;
}
