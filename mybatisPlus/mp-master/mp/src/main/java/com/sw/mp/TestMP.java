package com.sw.mp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.mysql.jdbc.Connection;

public class TestMP {
	/**
	 * 代码生成 示例代码
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		String filePath = System.getProperty("java.class.path"); // 运行包当前路径
		Properties p = new Properties();
		String fileDir = "src/main/resources/";
		if (filePath.endsWith(".jar")) {
			// 此时的路径是"E:\workspace\Demorun\Demorun_fat.jar"，用"/"分割不行
			// 下面的语句输出是-1，应该改为lastIndexOf("\\")或者lastIndexOf(File.separator)
			// 调试的时候请注释这里
			fileDir = filePath.substring(0,filePath.lastIndexOf(File.separator) + 1);
		}
		FileInputStream fis = new FileInputStream(new File(fileDir + "config.properties"));
		InputStreamReader isr = new InputStreamReader(fis, "utf-8");// 将文件编码指定为utf-8
																	// 解决中文乱码问题
		p.load(isr);
		String projectName = p.getProperty("projectName");
		String commonPath = p.getProperty("commonPath");
		String javaOutputDir = commonPath + projectName + "\\src\\main\\java";
		String pageOutputDir = commonPath + projectName + "\\src\\main\\webapp\\";
		String XMLOutputDir = commonPath + projectName + "\\src\\main\\resources\\" + projectName + "\\dal\\sqlmap\\";
		String FilterOutputDir = commonPath + projectName + "\\src\\main\\java\\com\\siwill\\" + projectName
				+ "\\dal\\datajsonfilter\\";
		//作者
		String authName = p.getProperty("authName");
		String serviceName = "%sManager";
		String serviceNameImpl = "%sManagerImpl";
		String baseTemplatesDir = p.getProperty("baseTemplatesDir");
		//模板路径
		String XMLControllerDir = baseTemplatesDir + p.getProperty("XMLControllerDir");
		String XMLServiceDir = baseTemplatesDir + p.getProperty("XMLServiceDir");
		String XMLServiceImplDir = baseTemplatesDir + p.getProperty("XMLServiceImplDir");
		String XMLEntityDir = baseTemplatesDir + p.getProperty("XMLEntityDir");
		String XMLMapperDir = baseTemplatesDir + p.getProperty("XMLMapperDir");
		String XMLMapperXMLDir = baseTemplatesDir + p.getProperty("XMLMapperXMLDir");
		String XMLViewPageDir = baseTemplatesDir + p.getProperty("XMLViewPageDir");
		String XMLEditPageDir = baseTemplatesDir + p.getProperty("XMLEditPageDir");
		String XMLFilterDir = baseTemplatesDir + p.getProperty("XMLFilterDir");
		List<String> list = new ArrayList<String>();
		String tableName = p.getProperty("tableName");
		if (tableName != null) {
			String []tableNames = tableName.split(";");
			for(String name:tableNames ){
				list.add(name);
			}
		}
		// 不需要生成代码的表
		String[] exit_tables = p.getProperty("exit_tables").split(";");
		String driver = p.getProperty("driver");
		String url = p.getProperty("url");
		String user = p.getProperty("user");
		String password = p.getProperty("password");
		if (list.size() == 0) {
			Class.forName(driver);
			Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			else {
				System.err.println("connect filed");
			}
			// 获取所有表名
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				list.add(rs.getString(3));
				// System.out.println("表名：" + rs.getString(3));
			}
		}
		// 1. 全局配置
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(true) // 是否支持AR模式
				.setOpen(false).setAuthor(authName) // 作者
				.setOutputDir(javaOutputDir) // 生成路径
				.setFileOverride(true) // 文件覆盖
				.setIdType(IdType.AUTO) // 主键策略
				.setServiceName(serviceName) // 设置生成的service接口的名字的首字母是否为I
				// IEmployeeService
				.setBaseResultMap(true).setBaseColumnList(true);
		config.setServiceImplName(serviceNameImpl);
		// 2. 数据源配置
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setDbType(DbType.MYSQL) // 设置数据库类型
				.setDriverName(driver).setUrl(url).setUsername(user).setPassword(password);
		// 3. 包名策略配置
		PackageConfig pkConfig = new PackageConfig();
		pkConfig.setParent("com.siwill");
		pkConfig.setModuleName(projectName);
		pkConfig.setMapper("dal.dao");
		pkConfig.setEntity("dal.dataobject");
		pkConfig.setService("biz");
		pkConfig.setServiceImpl("biz.impl");
		pkConfig.setXml(null);
		// 4定义模板
		TemplateConfig tc = new TemplateConfig();
		tc.setController(XMLControllerDir);
		tc.setService(XMLServiceDir);
		tc.setServiceImpl(XMLServiceImplDir);
		tc.setEntity(XMLEntityDir);
		tc.setMapper(XMLMapperDir);
		tc.setXml(null);
		// tc.setXml("/templatesMybatis/mapper.xml.vm");
		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig(XMLMapperXMLDir) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return XMLOutputDir + tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		focList.add(new FileOutConfig(XMLViewPageDir) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				if(tableInfo.getName().split("_").length<3){
					return pageOutputDir + tableInfo.getName() + "\\templates\\screen\\"
							+ tableInfo.getName() + "\\" + "view.vm";
				}
				return pageOutputDir + tableInfo.getName().split("_")[0] + "\\templates\\screen\\"
						+ tableInfo.getName().split("_")[1] + "\\" + "view"
						+ tableInfo.getName().split("_")[2].substring(0, 1).toUpperCase()
						+ tableInfo.getName().split("_")[2].substring(1) + ".vm";
			}
		});
		focList.add(new FileOutConfig(XMLEditPageDir) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				if(tableInfo.getName().split("_").length<3){
					return pageOutputDir + tableInfo.getName() + "\\templates\\screen\\"
							+ tableInfo.getName() + "\\" + "edit.vm";
				}
				// 自定义输入文件名称
				return pageOutputDir + tableInfo.getName().split("_")[0] + "\\templates\\screen\\"
						+ tableInfo.getName().split("_")[1] + "\\" + "edit"
						+ tableInfo.getName().split("_")[2].substring(0, 1).toUpperCase()
						+ tableInfo.getName().split("_")[2].substring(1) + ".vm";
			}
		});
		focList.add(new FileOutConfig(XMLFilterDir) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				List<TableField> list = new ArrayList<TableField>();
				list = tableInfo.getFields();
				int flag = 0;
				for (TableField field : list) {
					if (field.getType().contains("date")) {
						flag = 1;
						break;
					}
				}
				if (flag == 0) {
					 return FilterOutputDir+"filter";
				} else {
					// 自定义输入文件名称
					return FilterOutputDir + tableInfo.getEntityName() + "Filter.java";
				}
			}
		});
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				this.setMap(map);
			}
		};
		// 5. 整合配置
		for (String include : list) {
			for (String exitTable : exit_tables) {
				if (include.toUpperCase().equals(exitTable.toUpperCase())) {
					break;
				}
			}
			if (include.split("_").length < 3) {
				continue;
			}
			AutoGenerator1 ag = new AutoGenerator1();
			injectionConfig.setFileOutConfigList(focList);
			// action的包路径有第一个下划线"_"前面的字母决定
			String s = include.split("_")[0].toUpperCase();
			config.setControllerName(include.split("_")[1].substring(0, 1).toUpperCase()
					+ include.split("_")[1].substring(1) + include.split("_")[2].substring(0, 1).toUpperCase()
					+ include.split("_")[2].substring(1) + "Action");
			pkConfig.setController("webx." + s.toLowerCase() + ".module.action");
			ag.setCfg(injectionConfig);
			ag.setGlobalConfig(config).setDataSource(dsConfig).setPackageInfo(pkConfig);
			ag.setTemplate(tc);
			// 6. 策略配置
			StrategyConfig stConfig = new StrategyConfig();
			stConfig.setCapitalMode(true) // 全局大写命名
					.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
					// .setTablePrefix("sys_")
					.setInclude(include); // 生成的表

			ag.setStrategy(stConfig);
			// 7. 执行
			ag.execute();
		}
	}
}
