CopyDB
=========================
本工具用于将数据从一个库中导出，或者将导出的数据导入到另外的库。
这个工具是给开发人员定位问题使用。

这个工具的本地目录结构如下：

```
\--- CopyDB
     +--- config
     |    +--- application.properties
     |    +--- application-export.properties
     |    \--- application-import.properties
     +--- copydb.jar
     +--- copydb.sh
     \--- somelibs.jar

```

具体使用方法如下：
## 从数据库导出数据
* 修改配置文件application-export.properties
```
#定义第一个数据源src1
src1.datasource.url=jdbc:sqlserver://10.204.8.17:1433;databaseName=pcgscct_sit
src1.datasource.username=s_SCCT
src1.datasource.password=Initial1
src1.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#根据需要可以定义多个数据源，这里定义第二个数据源src2
src2.datasource.url=jdbc:sqlserver://10.204.8.18:1433;databaseName=scct_stage
src2.datasource.username=s_SCCT
src2.datasource.password=Initial1
src2.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#定义一个动作export
action.export.name=shpmntStatusStage
action.export.type=export
action.export.datasource=src1
action.export.sql=select * from shpmntStatusStage with(nolock) where DLVRY_NUM='5221175600'
action.export.file=output.json

#根据需要可以定义多个动作，这里定义第二个动作export2
action.export2.name=deliveryOdrStage
action.export2.type=export
action.export2.datasource=src2
action.export2.sql=select * from deliveryOdrStage with(nolock) where DLVRY_NUM='5221175600'
action.export2.file=output.json

```
执行命令启动导出
java -jar copydb.jar -Dspring.profiles.active=export export export2

## 将数据导入数据库
* 修改配置文件application-import.properties
```
#定义第一个数据源src1
src1.datasource.url=jdbc:sqlserver://10.204.8.17:1433;databaseName=pcgscct_sit
src1.datasource.username=s_SCCT
src1.datasource.password=Initial1
src1.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#定义一个动作import
action.import.name=shpmntStatusStage
action.import.type=import
action.import.datasource=src1
action.import.source.file=output.json
action.import.source.name=shpmntStatusStage
action.import.target.table=shpmntStatusStage

#定义第二个动作import2
action.import2.name=deliveryOdrStage
action.import2.type=import
action.import2.datasource=src1
action.import2.source.file=output.json
action.import2.source.name=deliveryOdrStage
action.import2.target.table=deliveryOdrStage

```
执行命令启动导出
java -jar copydb.jar -Dspring.profiles.active=import import import2
