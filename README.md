# DSQL

<p align="left">
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" />
    </a>
    <a target="_blank" href="LICENSE"><img src="https://img.shields.io/:license-Apache%202.0-blue.svg"></a>
    <a target="_blank" href='https://gitee.com/tenmg/dsql'>
        <img src="https://gitee.com/tenmg/dsql/badge/star.svg?theme=white" />
    </a>
    <a href="https://mvnrepository.com/artifact/cn.tenmg/dsql">
        <img alt="maven" src="https://img.shields.io/maven-central/v/cn.tenmg/dsql.svg?style=flat-square">
    </a>
</p>

## 简介

DSQL 的全称是动态结构化查询语言（Dynamic Structured Query Language），他是一种对结构化查询语言（SQL）的一种扩展。DSQL 基于[DSL](https://gitee.com/tenmg/dsl)实现，并在其上封装了配置工厂，可轻松管理和解析复杂的 DSQL。DSQL与 [DSL](https://gitee.com/tenmg/dsl) 一样，使用 `:` 加参数名表示普通参数，使用 `#` 加参数名表示嵌入参数，使用特殊字符 `#[]` 标记动态片段，当实际执行查询时，判断实际传入参数值是否为空（`null`）决定是否保留该片段，从而达到动态执行不同 SQL 的目的。以此来避免程序员手动拼接繁杂的 SQL，使得程序员能从繁杂的业务逻辑中解脱出来。此外，DSQL 还为查询参数提供转换器和过滤器，来对参数进行转换和过滤；同时，DSQL 脚本支持宏，来增强 SQL 的动态逻辑处理能力，避免使用函数导致索引破坏等。

## 起步

以基于Maven项目为例

pom.xml添加依赖，${dsql.version} 为版本号，可定义属性或直接使用版本号替换

```
<!-- https://mvnrepository.com/artifact/cn.tenmg/dsql -->
<dependency>
    <groupId>cn.tenmg</groupId>
    <artifactId>dsql</artifactId>
    <version>${dsql.version}</version>
</dependency>
```

创建DSQL工厂（该过程需要扫描XML配置文件，建议使用单例，使用时注入即可）。

```
String basePackages = "cn.tenmg", suffix = ".dsql.xml";
DSQLFactory factory = new XMLFileDSQLFactory(basePackages, suffix);
```

使用DSQL工厂解析DSQL为NamedSQL对象

```
NamedSQL namedSQL = factory.parse(dsqlId, params);
// 或者
NamedSQL namedSQL = factory.parse(plainDSQLText, params);
```

将NamedSQL对象转换为Script对象

```
Script<List<Object>> sql = factory.toJDBC(namedSQL, params);
// 或者
Script<List<Object>> sql = DSLUtils.toScript(namedSQL.getScript(), namedSQL.getParams(),
				JDBCParamsParser.getInstance());
```

将Script对象提交给JDBC执行

```
……
List<Object> params = sql.getParams();
PreparedStatement ps = con.prepareStatement(sql.getValue());
if (params != null && !params.isEmpty()) {
    for (int i = 0, size = params.size(); i < size; i++) {
        ps.setObject(i + 1, params.get(i));
    }
}	
ps.execute();
……
```

## 参数

### 普通参数

使用 `:` 加参数名表示普通参数，例如，:staffName。

### 嵌入参数

使用 `#` 和参数名表示嵌入参数（例如，`#staffName`）。1.2.2版本开始支持嵌入参数，嵌入参数会被以字符串的形式嵌入到脚本中。**值得注意的是：如果在SQL脚本中使用嵌入参数，会有SQL注入风险，一定注意不要将前端传参直接作为嵌入参数使用，如果使用必需进行合法性检验**。

### 动态参数

动态参数是指，根据具体情况确定是否在动态脚本中生效的参数，动态参数是动态片段的组成部分。动态参数既可以是普通参数，也可以嵌入参数。

### 静态参数

静态参数是相对动态参数而言的，它永远会在动态脚本中生效。在动态片段之外使用的参数就是静态参数。静态参数既可以是普通参数，也可以嵌入参数。

### 参数访问符

参数访问符包括两种，即 `.` 和 `[]`, 使用 `Map` 传参时，优先获取键相等的值，只有键不存在时才会将键降级拆分一一访问对象，直到找到参数并返回，或未找到返回 `null`。其中`.` 用来访问对象的属性，例如 `:staff.name`、`#staff.age`；`[]` 用来访问数组、集合的元素，例如 `:array[0]`、`#map[key]`。理论上，支持任意级嵌套使用，例如`:list[0][1].name`、`#map[key][1].staff.name`。1.2.2版本开始支持参数访问符。

## 动态片段

DSQL 使用特殊字符#[]标记动态片段，并连同动态参数一起构成动态片段。在一些数据库可视化管理工具中 `#[]` 之间的 SQL 被认为是注释，因此多数 DSQL 脚本可以在其上直接运行，几乎所有 DSQL 脚本经过少量修改即可在所有数据库管理工具中运行。动态片段可以是任意 SQL 片段，最常见的是用于查询条件上。

动态片段的魔力来源于巧妙地运用了的一个值：`null`，因为该值往往在结构化查询语言（SQL）中很少用到，而且即便使用也是往往作为特殊的常量使用。

### 例子

假设有一张员工信息表STAFF_INFO，表结构详见如下建表语句：

```
CREATE TABLE STAFF_INFO (
  STAFF_ID VARCHAR(20) NOT NULL,          /*员工编号*/
  STAFF_NAME VARCHAR(30) DEFAULT NULL,    /*员工姓名*/
  DEPARTMENT_ID VARCHAR(10) DEFAULT NULL, /*部门编号*/
  POSITION VARCHAR(30) DEFAULT NULL,      /*所任职位*/
  STATUS VARCHAR(20) DEFAULT 'IN_SERVICE',/*在职状态*/
  PRIMARY KEY (`STAFF_ID`)
);
```

通常，我们经常需要按员工编号或者按员工姓名查询员工信息。这就需要我们对查询条件进行排列组合，一共会存在

```math
2^2=4
```

种可能的 SQL。如果使用 SQL 拼接的技术实现，显然是比较低效的。如果查询条件数量更多，则拼接 SQL 会成为难以想象的难题。为此，我们必须有一种技术帮我们来完成这样的事情，动态片段应运而生。有了动态片段，我们对上述问题就能够轻松解决了。

```
SELECT
  *
FROM STAFF_INFO S
WHERE 1=1
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```
有了上述带动态片段的SQL，DSQL可以自动根据实际情况生成需要执行的SQL。例如：

1. 参数 `staffId` 为 `null`，而 `staffName` 为非 `null` 时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_NAME LIKE :staffName
```

2. 相反，参数 `staffName` 为 `null`，而 `staffId` 为非 `null` 时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_ID = :staffId
```

3. 或者，参数 `staffId`、`staffName` 均为 `null` 时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
```

4. 最后，参数 `staffId`、`staffName` 均为非 `null` 时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_ID = :staffId
   AND S.STAFF_NAME LIKE :staffName
```

## 使用宏

宏是动态结构化查询语言（DSQL）的重要组成部分，它继承自 [DSL](https://gitee.com/tenmg/dsl)，通过宏可以实现一些简单的逻辑处理。宏是基于Java内置的JavaScript引擎实现的，因此其语法是JavaScript语法，而不是Java。目前已实现的宏包括：

```
#[if(……)]
```

```
#[if(……)]
#[else]
```

```
#[if(……)]
#[elseif(……)]
#[else]
```

其中：

```
#[AND STAFF_ID = :staffId]
```

等价于：

```
#[if(:staffId != null) AND STAFF_ID = :staffId]
```
但遇到这种情况，极力推荐使用前者。因为前者代码更简洁，同时不需要运行JavaScript引擎而运行更快。

接上文，假如有如下表数据，以下再给两个运用宏的例子：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---|---|---|---|---
000001 |    赵一飞    | 01       | 总经理        | IN_SERVICE
000002 |    王佳池    | 01       |副总经理       | IN_SERVICE
000003 |    李辰立    | 02       |系统架构师     | IN_SERVICE
000004 |    张彦云    | 03       |系统架构师     | IN_SERVICE
000005 |    孙来笙    | 02       |高级软件工程师 | IN_SERVICE
000006 |    钱等英    | 03       |高级软件工程师 | IN_SERVICE
000007 |    周启      | 02       |软件工程师     | IN_SERVICE
000008 |    吴川宜    | 02       |软件工程师     | IN_SERVICE
000009 |    郑梦雪    | 03       |软件工程师     | IN_SERVICE
000010 |    王成文    | 03       |软件工程师     | IN_SERVICE
000011 |    冯松颖    | 02       |软件工程师     | OUT_OF_SERVICE
000012 |    陈之文    | 03       |软件工程师     | OUT_OF_SERVICE

1. 部门编号为01的员工可以查看所有员工信息，其他员工仅可以查看自己所在部门的员工信息。假设当前员工所在部门参数为 `curDepartmentId`，那么 DSQL 可以这样编写：

```
SELECT
  *
FROM STAFF_INFO S
WHERE #[if(:curDepartmentId == '01') 1=1]
  #[else S.DEPARTMENT_ID = :curDepartmentId]
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```

01部门的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000001 |    赵一飞    | 01       | 总经理        | IN_SERVICE
000002 |    王佳池    | 01       |副总经理       | IN_SERVICE
000003 |    李辰立    | 02       |系统架构师     | IN_SERVICE
000004 |    张彦云    | 03       |系统架构师     | IN_SERVICE
000005 |    孙来笙    | 02       |高级软件工程师 | IN_SERVICE
000006 |    钱等英    | 03       |高级软件工程师 | IN_SERVICE
000007 |    周启      | 02       |软件工程师     | IN_SERVICE
000008 |    吴川宜    | 02       |软件工程师     | IN_SERVICE
000009 |    郑梦雪    | 03       |软件工程师     | IN_SERVICE
000010 |    王成文    | 03       |软件工程师     | IN_SERVICE
000011 |    冯松颖    | 02       |软件工程师     | OUT_OF_SERVICE
000012 |    陈之文    | 03       |软件工程师     | OUT_OF_SERVICE

02部门的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000003 |    李辰立    | 02       |系统架构师     | IN_SERVICE
000005 |    孙来笙    | 02       |高级软件工程师 | IN_SERVICE
000007 |    周启      | 02       |软件工程师     | IN_SERVICE
000008 |    吴川宜    | 02       |软件工程师     | IN_SERVICE
000011 |    冯松颖    | 02       |软件工程师     | OUT_OF_SERVICE

03部门的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000004 |    张彦云    | 03       |系统架构师     | IN_SERVICE
000006 |    钱等英    | 03       |高级软件工程师 | IN_SERVICE
000009 |    郑梦雪    | 03       |软件工程师     | IN_SERVICE
000010 |    王成文    | 03       |软件工程师     | IN_SERVICE
000012 |    陈之文    | 03       |软件工程师     | OUT_OF_SERVICE

2. 部门编号为01的员工可以查看所有员工信息，部门编号为02的员工可以查看本部门员工的信息，其他员工仅可以查看本部门跟自己职位一样的员工信息。假设当前员工职位参数为 `curPosition`，所在部门参数为 `curDepartmentId`，那么 DSQL 可以这样编写：

```
SELECT
  *
FROM STAFF_INFO S
WHERE #[if(:curDepartmentId == '01') 1=1]
  #[elseif(:curDepartmentId == '02') S.DEPARTMENT_ID = :curDepartmentId]
  #[else S.DEPARTMENT_ID = :curDepartmentId AND S.POSITION = :curPosition]
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```

01部门的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000001 |    赵一飞    | 01       | 总经理        | IN_SERVICE
000002 |    王佳池    | 01       |副总经理       | IN_SERVICE
000003 |    李辰立    | 02       |系统架构师     | IN_SERVICE
000004 |    张彦云    | 03       |系统架构师     | IN_SERVICE
000005 |    孙来笙    | 02       |高级软件工程师 | IN_SERVICE
000006 |    钱等英    | 03       |高级软件工程师 | IN_SERVICE
000007 |    周启      | 02       |软件工程师     | IN_SERVICE
000008 |    吴川宜    | 02       |软件工程师     | IN_SERVICE
000009 |    郑梦雪    | 03       |软件工程师     | IN_SERVICE
000010 |    王成文    | 03       |软件工程师     | IN_SERVICE
000011 |    冯松颖    | 02       |软件工程师     | OUT_OF_SERVICE
000012 |    陈之文    | 03       |软件工程师     | OUT_OF_SERVICE

02部门的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000003 |    李辰立    | 02       |系统架构师     | IN_SERVICE
000005 |    孙来笙    | 02       |高级软件工程师 | IN_SERVICE
000007 |    周启      | 02       |软件工程师     | IN_SERVICE
000008 |    吴川宜    | 02       |软件工程师     | IN_SERVICE
000011 |    冯松颖    | 02       |软件工程师     | OUT_OF_SERVICE

其他员工仅可查询本部门职位相同的员工信息，如工号000009的员工的查询数据范围为：

STAFF_ID | STAFF_NAME | DEPARTMENT_ID | POSITION | STATUS
---------|------------|---------------|----------|--------
000009 |    郑梦雪    | 03       |软件工程师     | IN_SERVICE
000010 |    王成文    | 03       |软件工程师     | IN_SERVICE
000012 |    陈之文    | 03       |软件工程师     | OUT_OF_SERVICE

## 参数过滤器

使用参数过滤器可以过滤掉满足特定条件的参数。例如我们经常需要查询在职（IN_SERVICE）员工的信息，页面默认的状态查询条件就是在职（IN_SERVICE），但是有时候我们也需要查询b包括离职（OUT_OF_SERVICE）在内的全部的员工信息。我们除了可以通过不传特定参数或特定参数为 `null` 结合动态片段来实现之外，还可以将参数值设为一个固定常量（比如：ALL）然后通过过滤器过滤掉来实现相同的效果。使用过滤器，必须通过XML文件来配置DSQL。

```xml
<dsql>
    <filter>
        <eq value="ALL" params="status" />
    </filter>
    <script>
        <![CDATA[
            SELECT
              *
            FROM STAFF_INFO S
            WHERE 1=1
              #[AND S.STATUS = :status]
              #[AND S.STAFF_ID = :staffId]
              #[AND S.STAFF_NAME LIKE :staffName]
        ]]>
    </script>
</dsql>
```

### blank

空白字符串过滤器。一个字符都没有或者全部字符为不可见字符则参数会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔。

### eq

等值参数过滤器。参数值等于指定值参数将会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 value 表示比较值，比较之前会将两个值进行类型转换以确保类型一致，优先将比较值转换为参数值的类型。

### gt

大值参数过滤器。参数值大于指定值参数将会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 value 表示比较值，比较之前会将两个值进行类型转换以确保类型一致，优先将比较值转换为参数值的类型。

### gte

大于等于参数过滤器。参数值大于等于指定值参数将会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 value 表示比较值，比较之前会将两个值进行类型转换以确保类型一致，优先将比较值转换为参数值的类型。

### lt

小值参数过滤器。参数值小于指定值参数将会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 value 表示比较值，比较之前会将两个值进行类型转换以确保类型一致，优先将比较值转换为参数值的类型。

### lte

小于等于参数过滤器。参数值小于等于指定值参数将会被过滤。属性 params 表示需要判定的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 value 表示比较值，比较之前会将两个值进行类型转换以确保类型一致，优先将比较值转换为参数值的类型。

## 参数转换器

参数转换器可以将参数提交给 JDBC 执行之前，对参数进行转换。例如：LIKE 查询需要在参数字符串的左右增加百分号（%）以表示模糊查询，java.lang.String 类型的需要转换为 java.util.Date 类型等。一个 LIKE 模糊查询的例子（其中 formatter 属性里面的 ${value} 表示参数值，运行时会替换）：

```xml
<dsql>
    <filter>
        <eq value="ALL" params="status" />
    </filter>
    <converter>
        <wrap-string params="staffName" formatter="%${value}%"/>
    </converter>
    <script>
        <![CDATA[
            SELECT
              *
            FROM STAFF_INFO S
            WHERE 1=1
              #[AND S.STATUS = :status]
              #[AND S.STAFF_ID = :staffId]
              #[AND S.STAFF_NAME LIKE :staffName]
        ]]>
    </script>
</dsql>
```

### to-number

将参数转换为`java.lang.Number` 类型的转换器。通常是从 `java.lang.String` 类型转换为 `java.lang.Number` 类型。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 formatter 表示格式模板，具体的 java.lang.Number 类型会根据 formatter 属性格式化确定。

### to-date

将参数转为 `java.util.Date` 类型的转换器。通常是从 `java.lang.String` 类型转换为 `java.util.Date` 类型。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 formatter 表示格式化模板。日期模板表示详见Java规范。

### date-add

对 `java.util.Date` 类型的参数做加法运算的转换器。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 amount 为整形，表示加法运算的时间量；属性 unit 为时间单位，可选值：`year/month/day/hour/minute/second/millisecond`，分别对应：年/月/日/时/分/秒/毫秒。

### to-string

将参数转换为 `java.lang.String` 类型的转换器。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 formatter 表示格式化模板，当参数值为 `java.lang.Number`、`java.util.Date`、`java.util.Calendar` 的实例时，通过格式模板将对象格式化为字符串（如 `#,###.00`、`yyyy-MM-dd HH:mm:ss` 等）。

### wrap-string

字符串参数包装转换器。用于将字符串按指定模板包装。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 formatter 表示包装模板。默认值为 `%${value}%`，其中 `${value}` 表示参数值，运行时会替换。

### split

将类型为 `java.lang.String` 的非 `null` 参数值进行分割的转换器。属性 params 表示需要转换的参数名称，支持使用 `*` 表示通配符，多个参数名之间使用逗号（,）分隔；属性 regex 分割正则表达式（如 `,` 等），也可以使用 `<regex>` 子标签定义，特别是带有 XML 需要转义的字符时（如 `<regex><![CDATA[&]]><regex>` 等）；limit 表示最大分割子字符串数，可缺省，指定时，必须为大于 1 的正整数。

## 使用技巧

并非所有参数相关的 SQL 片段都需要使用动态片段，应该根据需要确定是否使用动态片段。例如，只允许查询本部门员工信息的情况下，当前部门（`curDepartmentId`）这个参数是必须的，该片段就应该静态化表示：

```xml
<dsql>
    <filter>
        <eq value="ALL" params="status" />
    </filter>
    <converter>
        <wrap-string params="staffName" formatter="%${value}%"/>
    </converter>
    <script>
        <![CDATA[
            SELECT
              *
            FROM STAFF_INFO S
            WHERE S.DEPARTMENT_ID = :curDepartmentId
              #[AND S.STATUS = :status]
              #[AND S.STAFF_ID = :staffId]
              #[AND S.STAFF_NAME LIKE :staffName]
        ]]>
    </script>
</dsql>
```

## 使用注释

1.2.0 版本以后注释中的命名参数表达式（如 `:paramName`）不再被认为是参数，而是会被原样保留，同样，动态片段也会被原样保留。也就是说，注释内的所有内容都会被原封不动地保留。

注释可以在动态片段内部，动态片段内部的注释会跟随动态片段保留而保留，去除而去除；注释也可以在动态片段外部，动态片段外部的注释会被完整保留在脚本中。单行注释的前缀和多行注释的前、后缀都可以在 `dsl.properties` [配置文件](#%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6) 中自定义，最多支持两个字符。

对于单行注释前缀和多行注释前缀，使用一个字符时，不能使用字符`#`；使用两个字符时，不能使用字符`#[`。对于多行注释后缀，使用一个字符时，不能使用字符`]`。

单行注释的前缀默认为`--`或`//`，例子：


```xml
<dsql>
    <filter>
        <eq value="ALL" params="status" />
    </filter>
    <converter>
        <wrap-string params="staffName" formatter="%${value}%"/>
    </converter>
    <script>
        <![CDATA[
            SELECT
              *
            FROM STAFF_INFO S
            WHERE S.DEPARTMENT_ID = :curDepartmentId
              #[AND S.STATUS = :status -- 这是单行注释……]
              #[AND S.STAFF_ID = :staffId] // 这是单行注释……
              #[AND S.STAFF_NAME LIKE :staffName /* 这是多行
               注释…… */]
              /* 这是多行
                 注释…… */
        ]]>
    </script>
</dsql>
```

## 配置文件

通过 `dsl.properties` 配置文件可以调整注释等的配置，详见参见 [DSL 配置文件](https://gitee.com/tenmg/dsl/tree/master#%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)。

## 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

## 相关链接

DSL开源地址：https://gitee.com/tenmg/dsl
