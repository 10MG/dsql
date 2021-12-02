package cn.tenmg.dsql.config;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;

import cn.tenmg.dsql.config.model.Dsql;

/**
 * 配置加载器
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public interface ConfigLoader {

	/**
	 * 加载DSQL配置
	 * 
	 * @param s
	 *            配置字符串
	 * @return DSQL列表
	 */
	List<Dsql> load(String s);

	/**
	 * 加载DSQL配置
	 * 
	 * @param file
	 *            配置文件
	 * @return DSQL列表
	 */
	List<Dsql> load(File file);

	/**
	 * 加载DSQL配置
	 * 
	 * @param fr
	 *            文件读取器
	 * @return DSQL列表
	 */
	List<Dsql> load(FileReader fr);

	/**
	 * 加载DSQL配置
	 * 
	 * @param is
	 *            输入流
	 * @return DSQL列表
	 */
	List<Dsql> load(InputStream is);
}
