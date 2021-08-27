# DSQL

# 简介

DSQL的全称是动态结构化查询语言（Dynamic Structured Query Language），他是一种对结构化查询语言（SQL）的一种扩展。DSQL基于[DSL](https://gitee.com/tenmg/dsl)实现，使用特殊字符#[]标记动态片段，当实际执行查询时，判断实际传入参数值是否为空（null）决定是否保留该片段，从而达到动态执行不同查询的目的。以此来避免程序员手动拼接繁杂的SQL，使得程序员能从繁杂的业务逻辑中解脱出来。此外，DSQL还为查询参数增加过滤器和转换器，来对参数进行过滤和类型转换等；同时，DSQL脚本支持宏，来增强SQL的动态逻辑处理能力，避免使用函数导致索引破坏等。

# 动态片段


DSQL使用特殊字符#[]标记动态片段，在一些数据库可视化管理工具中#[]之间的SQL被认为是注释，因此多数DSQL脚本可以在其上直接运行，几乎所有DSQL脚本经过少量修改即可在所有数据库管理工具中运行。动态片段可以是任意SQL片段，最常见的是用于查询条件上，查询参数使用冒号加参数名表示（例如，:staffName），这与许多数据库管理工具的表示方式是一致的。

动态片段的魔力来源于巧妙地运用了的一个值：空(null)，因为该值往往在结构化查询语言(SQL)中很少用到，而且即便使用也是往往作为特殊的常量使用。

## 例子

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

种可能的SQL。如果使用SQL拼接的技术实现，显然是比较低效的。如果查询条件数量更多，则拼接SQL会成为难以想象的难题。为此，我们必须有一种技术帮我们来完成这样的事情，动态片段应运而生。有了动态片段，我们对上述问题就能够轻松解决了。

```
SELECT
  *
FROM STAFF_INFO S
WHERE 1=1
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```
有了上述带动态片段的SQL，Sqltool可以自动根据实际情况生成需要执行的SQL。例如：

1. 参数staffId为空（null），而staffName为非空（非null）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_NAME LIKE :staffName
```

2. 相反，参数staffName为空（null），而staffId为非空（非null）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_ID = :staffId
```

3. 或者，参数staffId、staffName均为空（null）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
```

4. 最后，参数staffId、staffName均为非空（非null）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE 1=1
   AND S.STAFF_ID = :staffId
   AND S.STAFF_NAME LIKE :staffName
```

# 宏

宏是动态结构化查询语言（DSQL）的重要组成部分，通过宏可以实现一些简单的逻辑处理。宏是基于Java内置的JavaScript引擎实现的，因此其语法是JavaScript语法，而不是Java。目前已实现的宏包括：

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

1. 部门编号为01的员工可以查看所有员工信息，其他员工仅可以查看自己所在部门的员工信息。假设当前员工所在部门参数为curDepartmentId，那么DSQL可以这样编写：

```
SELECT
  *
FROM STAFF_INFO S
WHERE #[if(:curDepartmentId == '01') 1=1]
  #[else S.DEPARTMENT_ID = :curDepartmentId]
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```

2. 部门编号为01的员工可以查看所有员工信息，部门编号为02和03的员工可以查看本部门员工的信息，其他员工仅可以查看本部门跟自己职位一样的员工信息。假设当前员工职位参数为curPosition，所在部门参数为curDepartmentId，那么DSQL可以这样编写：

```
SELECT
  *
FROM STAFF_INFO S
WHERE #[if(:curDepartmentId == '01') 1=1]
  #[elseif(:curDepartmentId == '02' || :curDepartmentId == '03') S.DEPARTMENT_ID = :curDepartmentId]
  #[else S.DEPARTMENT_ID = :curDepartmentId AND S.POSITION = :curPosition]
  #[AND S.STAFF_ID = :staffId]
  #[AND S.STAFF_NAME LIKE :staffName]
```

# 参数过滤器

使用参数过滤器可以过滤掉满足特定条件的参数。例如我们经常需要查询在职（IN_SERVICE）员工的信息，页面默认的状态查询条件就是在职（IN_SERVICE），但是有时候我们也需要查询b包括离职（OUT_OF_SERVICE）在内的全部的员工信息。我们除了可以通过不传特定参数或特定参数为空（null）结合动态片段来实现之外，还可以将参数值设为一个固定常量（比如：ALL）然后通过过滤器过滤掉来实现相同的效果。使用过滤器，必须通过XML文件来配置DSQL。

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

## blank

空白字符串过滤器。一个字符都没有或者全部字符为不可见字符则参数会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔。

## eq

等值参数过滤器。参数值等于指定值参数将会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔；属性value表示比较值，如遇到类型不是java.lang.String的参数，Sqltool会自动根据类型转换为java.lang.String类型。

## gt

大值参数过滤器。参数值大于指定值参数将会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔；属性value表示比较值，如遇到类型不是java.lang.String的参数，Sqltool会自动根据类型转换为java.lang.String类型。

## gte

大于等于参数过滤器。参数值大于等于指定值参数将会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔；属性value表示比较值，如遇到类型不是java.lang.String的参数，Sqltool会自动根据类型转换为java.lang.String类型。

## lt

小值参数过滤器。参数值小于指定值参数将会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔；属性value表示比较值，如遇到类型不是java.lang.String的参数，Sqltool会自动根据类型转换为java.lang.String类型。

## lte

小于等于参数过滤器。参数值小于等于指定值参数将会被过滤。属性params表示需要判定的参数名称，多个参数名之间使用逗号（,）分隔；属性value表示比较值，如遇到类型不是java.lang.String的参数，Sqltool会自动根据类型转换为java.lang.String类型。

# 参数转换器

参数转换器可以将参数提交给JDBC执行之前，对参数进行转换。例如：LIKE查询需要在参数字符串的左右增加百分号（%）以表示模糊查询，java.lang.String类型的需要转换为java.util.Date类型等。一个LIKE模糊查询的例子（其中formatter属性里面的${value}表示参数值，运行时会替换）：

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

## to-number

参数数字类型转换器。用于将参数转换为数值类型，通常是从java.lang.String类型转换为java.lang.Number类型。属性params表示需要转换的参数名称，多个参数名之间使用逗号（,）分隔；属性formatter表示格式化模板，具体的java.lang.Number类型会根据formatter属性格式化确定。

## to-date

参数日期类型转换器。用于将参数转换为java.util.Date类型，通常是从java.lang.String类型转换为java.util.Date类型。属性params表示需要转换的参数名称，多个参数名之间使用逗号（,）分隔；属性formatter表示格式化模板。日期模板表示详见Java规范。

## wrap-string

字符串参数包装转换器。用于将字符串按指定模板包装。属性params表示需要转换的参数名称，多个参数名之间使用逗号（,）分隔；属性formatter表示包装模板。默认值为“%${value}%”，其中${value}表示参数值，运行时会替换。

# 特别注意

并非所有参数相关的SQL片段都需要使用动态片段，应该根据需要确定是否使用动态片段。例如，只允许查询本部门员工信息的情况下，当前部门（curDepartmentId）这个参数是必须的，该片段就应该静态化表示：

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

# 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

# 相关链接

DSL开源地址：https://gitee.com/tenmg/dsl
