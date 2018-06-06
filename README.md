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
## 填写配置文件
* 修改配置文件application-export.properties
```
#定义第一个数据源src1
database.src1.url=jdbc:sqlserver://10.204.8.17:1433;databaseName=pcgscct_sit
database.src1.username=s_SCCT
database.src1.password=Initial1
database.src1.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#根据需要可以定义多个数据源，这里定义第二个数据源src2
database.src2.url=jdbc:sqlserver://10.204.8.18:1433;databaseName=scct_stage
database.src2.username=s_SCCT
database.src2.password=Initial1
database.src2.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#定义一个数据集shpmntStatusStage
dataset.shpmntStatusStage.table=shpmntStatusStage
dataset.shpmntStatusStage.sql=select * from shpmntStatusStage with(nolock) where DLVRY_NUM='5221175600'

#根据需要可以定义多个数据集，这里定义第二个数据集deliveryOdrStage
dataset.deliveryOdrStage.table=deliveryOdrStage
dataset.deliveryOdrStage.sql=select * from deliveryOdrStage with(nolock) where DLVRY_NUM='5221175600'

```

## 从数据库导出数据
执行命令启动导出
java -jar copydb.jar --from=db:src1 --to=file:output.json --datasets=shpmntStatusStage,deliveryOdrStage

## 将数据导入数据库
执行命令启动导出
java -jar copydb.jar --from=file:output.json --to=db:src1 --datasets=shpmntStatusStage,deliveryOdrStage
