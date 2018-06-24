CopyDB
=========================
本工具用于将数据从一个库中导出，或者将导出的数据导入到另外的库。
这个工具是给开发人员定位问题使用。

这个工具的本地目录结构如下：

```
\--- CopyDB
     +--- config
     |    \--- application.properties\
     +--- copydb.jar
     +--- copydb.sh
     \--- somelibs.jar

```

具体使用方法如下：
## 填写配置文件
* 修改配置文件application.properties

```
#定义第一个数据源src1
database.src1.url=jdbc:sqlserver://xx.xx.xx.xx:1433;databaseName=dbName
database.src1.username=userName
database.src1.password=password
database.src1.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#根据需要可以定义多个数据源，这里定义第二个数据源src2
database.src2.url=jdbc:sqlserver://xx.xx.xx.xx:1433;databaseName=dbName
database.src2.username=userName
database.src2.password=password
database.src2.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

#定义一个数据集Students
dataset.students.table=students
dataset.students.sql=select * from students with(nolock) where school='No1School'

#根据需要可以定义多个数据集，这里定义第二个数据集schools
dataset.schools.table=schools
dataset.schools.sql=select * from schools with(nolock)

```

## 从数据库导出数据
执行命令启动导出
java -jar copydb.jar --from=db:src1 --to=file:output.json --datasets=students,schools

## 将数据导入数据库
执行命令启动导出
java -jar copydb.jar --from=file:output.json --to=db:src1 --datasets=students,schools
