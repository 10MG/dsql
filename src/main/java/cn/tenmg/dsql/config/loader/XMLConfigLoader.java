package cn.tenmg.dsql.config.loader;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import cn.tenmg.dsql.config.ConfigLoader;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.config.model.Dsqls;
import cn.tenmg.dsql.exception.IllegalConfigException;

/**
 * XML配置加载器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class XMLConfigLoader implements ConfigLoader {

	private static final XMLConfigLoader INSTANCE = new XMLConfigLoader();

	private XMLConfigLoader() {
		super();
	}

	public static final XMLConfigLoader getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Dsql> load(String s) {
		try {
			return convert((Dsqls) createUnmarshaller().unmarshal(new StringReader(s)));
		} catch (JAXBException e) {
			throw new IllegalConfigException("加载DSQL配置失败", e);
		}
	}

	@Override
	public List<Dsql> load(File file) {
		try {
			return convert((Dsqls) createUnmarshaller().unmarshal(file));
		} catch (JAXBException e) {
			throw new IllegalConfigException("加载DSQL配置失败", e);
		}
	}

	@Override
	public List<Dsql> load(FileReader fr) {
		try {
			return convert((Dsqls) createUnmarshaller().unmarshal(fr));
		} catch (JAXBException e) {
			throw new IllegalConfigException("加载DSQL配置失败", e);
		}
	}

	@Override
	public List<Dsql> load(InputStream is) {
		try {
			return convert((Dsqls) createUnmarshaller().unmarshal(is));
		} catch (JAXBException e) {
			throw new IllegalConfigException("加载DSQL配置失败", e);
		}
	}

	private Unmarshaller createUnmarshaller() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Dsqls.class);
		return context.createUnmarshaller();
	}

	private List<Dsql> convert(Dsqls dsqls) {
		if (dsqls == null) {
			return null;
		}
		return dsqls.getDsqls();
	}
}
