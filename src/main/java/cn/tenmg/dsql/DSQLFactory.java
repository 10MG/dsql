package cn.tenmg.dsql;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.tenmg.dsl.Script;
import cn.tenmg.dsql.config.model.Dsql;

/**
 * 动态结构化查询语言工厂
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public interface DSQLFactory extends Serializable {

	/**
	 * 根据指定编号获取动态结构化查询语言(DSQL)对象
	 * 
	 * @param id
	 *            指定编号
	 * @return DSQL对象
	 */
	Dsql getDsql(String id);

	/**
	 * 根据指定编号获取动态结构化查询语言(DSQL)脚本
	 * 
	 * @param id
	 *            指定编号
	 * @return DSQL脚本
	 */
	String getScript(String id);

	/**
	 * 根据指定的参数params分析转换动态结构化查询语言(DSQL)为使用命名参数的结构化查询语言（SQL）对象。dsql可以是工厂中动态结构化查询语言(DSQL)的编号(id)，也可以是动态结构化查询语言(DSQL)脚本
	 * 
	 * @param dsql
	 *            动态结构化查询语言（DSQL)的编号(id)或者动态结构化查询语言（DSQL）脚本
	 * @param params
	 *            参数列表(分别列出参数名和参数值，或使用一个Map对象)
	 * @return SQL对象
	 */
	NamedSQL parse(String dsql, Object... params);

	/**
	 * 根据指定的参数params分析转换动态结构化查询语言(DSQL)为使用命名参数的结构化查询语言（SQL）对象。dsql可以是工厂中动态SQL的编号(id)，也可以是动态结构化查询语言(DSQL)脚本
	 * 
	 * @param dsql
	 *            动态结构化查询语言（DSQL)的编号(id)或者动态结构化查询语言（DSQL）脚本
	 * @param params
	 *            参数列表
	 * @return SQL对象
	 */
	NamedSQL parse(String dsql, Map<String, ?> params);

	/**
	 * 将使用命名参数的结构化查询语言（SQL）对象转换为JDBC可执行的脚本对象（含可执行脚本及对应的参数列表）
	 * 
	 * @param namedSQL
	 *            使用命名参数的结构化查询语言（SQL）对象
	 * @return 返回JDBC可执行的脚本对象
	 */
	Script<List<Object>> toJDBC(NamedSQL namedSQL);

	/**
	 * 将使用命名参数的结构化查询语言（SQL）对象转换为JDBC可执行的脚本对象（含可执行脚本及对应的参数列表）
	 * 
	 * @param namedscript
	 *            含有命名参数的脚本
	 * @param params
	 *            查询对照表
	 * @return 返回JDBC可执行的脚本对象
	 */
	Script<List<Object>> toJDBC(String namedscript, Map<String, ?> params);
}
