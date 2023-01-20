package cn.tenmg.dsql.factory;

import java.util.List;
import java.util.Map;

import cn.tenmg.dsl.ParamsConverter;
import cn.tenmg.dsl.ParamsFilter;
import cn.tenmg.dsl.Script;
import cn.tenmg.dsl.context.DefaultDSLContext;
import cn.tenmg.dsl.parser.JDBCParamsParser;
import cn.tenmg.dsl.utils.DSLUtils;
import cn.tenmg.dsql.DSQLFactory;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.dsql.config.model.Converter;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.config.model.Filter;

/**
 * 抽象动态结构化查询语言工厂
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public abstract class AbstractDSQLFactory implements DSQLFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5184151389554035362L;

	abstract Map<String, Dsql> getDsqls();

	@Override
	public Dsql getDsql(String id) {
		return getDsqls().get(id);
	}

	@Override
	public String getScript(String id) {
		Dsql dsql = getDsql(id);
		return dsql == null ? null : dsql.getScript();
	}

	@Override
	public NamedSQL parse(Dsql dsql, Object... params) {
		return new NamedSQL(dsql.getId(), DSLUtils.parse(
				new DefaultDSLContext(getParamsConverters(dsql), getParamsFilters(dsql)), dsql.getScript(), params));
	}

	@Override
	public NamedSQL parse(Dsql dsql, Object params) {
		return new NamedSQL(dsql.getId(), DSLUtils.parse(
				new DefaultDSLContext(getParamsConverters(dsql), getParamsFilters(dsql)), dsql.getScript(), params));
	}

	@Override
	public NamedSQL parse(String dsql, Object... params) {
		Dsql obj = getDsql(dsql);
		if (obj == null) {
			return new NamedSQL(DSLUtils.parse(dsql, params));
		} else {
			return new NamedSQL(obj.getId(), DSLUtils.parse(
					new DefaultDSLContext(getParamsConverters(obj), getParamsFilters(obj)), obj.getScript(), params));
		}
	}

	@Override
	public NamedSQL parse(String dsql, Object params) {
		Dsql obj = getDsql(dsql);
		if (obj == null) {
			return new NamedSQL(DSLUtils.parse(dsql, params));
		} else {
			return new NamedSQL(obj.getId(), DSLUtils.parse(
					new DefaultDSLContext(getParamsConverters(obj), getParamsFilters(obj)), obj.getScript(), params));
		}
	}

	@Override
	public Script<List<Object>> toJDBC(NamedSQL namedSQL) {
		return toJDBC(namedSQL.getScript(), namedSQL.getParams());
	}

	@Override
	public Script<List<Object>> toJDBC(String namedscript, Map<String, ?> params) {
		return DSLUtils.toScript(namedscript, params, JDBCParamsParser.getInstance());
	}

	private static List<ParamsConverter<?>> getParamsConverters(Dsql dsql) {
		Converter converter = dsql.getConverter();
		if (converter == null) {
			return null;
		}
		return converter.getParamsConverters();
	}

	private static List<ParamsFilter> getParamsFilters(Dsql dsql) {
		Filter filter = dsql.getFilter();
		if (filter == null) {
			return null;
		}
		return filter.getParamsFilters();
	}
}
