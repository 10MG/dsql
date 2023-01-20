package cn.tenmg.dsql.factory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cn.tenmg.dsl.ParamsConverter;
import cn.tenmg.dsl.ParamsFilter;
import cn.tenmg.dsl.utils.DateUtils;
import cn.tenmg.dsl.utils.MapUtils;
import cn.tenmg.dsql.DSQLFactory;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.dsql.ParamsConverterTest;
import cn.tenmg.dsql.ParamsFilterTest;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.model.converter.SplitTest;
import cn.tenmg.dsql.model.converter.WrapStringTest;

/**
 * 基于XML文件配置的动态结构化查询语言工厂测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 */
public class XMLFileDSQLFactoryTest {

	private static final String expectedSQL = "SELECT STAFF_ID, STAFF_NAME, POSITION, STATE, CREATE_TIME FROM STAFF_INFO WHERE enabled = :enabled AND STATE = :state AND CREATE_TIME >= :beginDate AND CREATE_TIME < :endDate AND POSITION in (:positions) AND STAFF_NAME LIKE :staffName AND 0 != :noteq AND 0 <= :notgt AND 0 < :notgte AND 0 >= :notlt AND 0 > :notlte ORDER BY STAFF_NAME";

	private DSQLFactory factory = new XMLFileDSQLFactory("dsql");

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void test() {
		String enabled = "1", beginDate = "2021-07-02", endDate = "2023-01-19", positions = SplitTest.positions,
				staffName = WrapStringTest.staffName, nullValue = null, emptyString = "", blankSpace = " ";
		int state = 1, eq = 0, noteq = 1, gt = 1, notgt = 0, gte = 0, notgte = -1, lt = -1, notlt = 0, lte = 0,
				notlte = 1, others = 9;
		Map<String, Object> params = MapUtils.newMapBuilder(new HashMap<String, Object>()).put("enabled", enabled)
				.put("state", state).put("beginDate", beginDate).put("endDate", endDate).put("positions", positions)
				.put("staffName", staffName).put("null", nullValue).put("emptyString", emptyString)
				.put("blankSpace", blankSpace).put("eq", eq).put("noteq", noteq).put("gt", gt).put("notgt", notgt)
				.put("gte", gte).put("notgte", notgte).put("lt", lt).put("notlt", notlt).put("lte", lte)
				.put("notlte", notlte).put("others", others).build();
		Dsql dsql = factory.getDsql("find_staff_info");

		// 加载参数转换器测试类
		ParamsConverterTest paramsConverterTest;
		Map<Class<? extends ParamsConverter<?>>, ParamsConverterTest> paramsConverterTests = new HashMap<Class<? extends ParamsConverter<?>>, ParamsConverterTest>();
		for (Iterator<ParamsConverterTest> it = ServiceLoader.load(ParamsConverterTest.class).iterator(); it
				.hasNext();) {
			paramsConverterTest = it.next();
			paramsConverterTests.put(paramsConverterTest.getParamsHandlerType(), paramsConverterTest);
		}

		// 对参数转换器进行测试
		ParamsConverter<?> converter;
		for (Iterator<ParamsConverter<?>> it = dsql.getConverter().getParamsConverters().iterator(); it.hasNext();) {
			converter = it.next();
			paramsConverterTests.get(converter.getClass()).test(converter);
		}

		// 加载参数过滤器测试类
		ParamsFilterTest paramsFilterTest;
		Map<Class<? extends ParamsFilter>, ParamsFilterTest> paramsFilterTests = new HashMap<Class<? extends ParamsFilter>, ParamsFilterTest>();
		for (Iterator<ParamsFilterTest> it = ServiceLoader.load(ParamsFilterTest.class).iterator(); it.hasNext();) {
			paramsFilterTest = it.next();
			paramsFilterTests.put(paramsFilterTest.getParamsHandlerType(), paramsFilterTest);
		}
		// 对参数过滤器进行测试
		ParamsFilter filter;
		for (Iterator<ParamsFilter> it = dsql.getFilter().getParamsFilters().iterator(); it.hasNext();) {
			filter = it.next();
			paramsFilterTests.get(filter.getClass()).test(filter);
		}

		// 解析DSQL
		NamedSQL namedSQL = factory.parse(dsql, params);

		// 测试解析脚本正确性
		Assertions.assertEquals(expectedSQL, namedSQL.getScript().trim().replaceAll("[\\s]+", " "));

		// 测试解析参数正确性
		Map<String, Object> usedParams = namedSQL.getParams();
		Assertions.assertNotEquals(enabled, usedParams.get("enabled"));
		Assertions.assertEquals(DateUtils.parse(beginDate, "yyyy-MM-dd"), usedParams.get("beginDate"));
		Assertions.assertEquals(DateUtils.addDays(DateUtils.parse(endDate, "yyyy-MM-dd"), 1),
				usedParams.get("endDate"));
		Assertions.assertArrayEquals(positions.split(SplitTest.regex, SplitTest.limit),
				(String[]) usedParams.get("positions"));
		Assertions.assertEquals(WrapStringTest.expectedAfterWrap, usedParams.get("staffName"));
		Assertions.assertFalse(usedParams.containsKey("null"));
		Assertions.assertFalse(usedParams.containsKey("emptyString"));
		Assertions.assertFalse(usedParams.containsKey("blankSpace"));
		Assertions.assertFalse(usedParams.containsKey("eq"));
		Assertions.assertEquals(noteq, usedParams.get("noteq"));
		Assertions.assertFalse(usedParams.containsKey("gt"));
		Assertions.assertEquals(notgt, usedParams.get("notgt"));
		Assertions.assertFalse(usedParams.containsKey("gte"));
		Assertions.assertEquals(notgte, usedParams.get("notgte"));
		Assertions.assertFalse(usedParams.containsKey("lt"));
		Assertions.assertEquals(notlt, usedParams.get("notlt"));
		Assertions.assertFalse(usedParams.containsKey("lte"));
		Assertions.assertEquals(notlte, usedParams.get("notlte"));
		Assertions.assertFalse(usedParams.containsKey("others"));
	}
}