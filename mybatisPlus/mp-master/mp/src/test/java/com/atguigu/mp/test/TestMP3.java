package com.atguigu.mp.test;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;

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
public class TestMP3 {
	/**
	 * 代码生成 示例代码
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testGenerator() throws SQLException, ClassNotFoundException {
		String projectName = "gis";
		String dataBaseName="gis";
		String tableName="datas_interface_test1";
		String commonPath="E:\\idea\\workspace1\\";
		String javaOutputDir = commonPath+projectName+"\\src\\main\\java";
		String FilterOutputDir = commonPath+projectName+"\\src\\main\\java\\com\\siwill\\"+projectName+"\\dal\\datajsonfilter\\";
		String pageOutputDir = commonPath+projectName+"\\src\\main\\webapp\\";
		String XMLOutputDir = commonPath+projectName+"\\src\\main\\resources\\"+projectName+"\\dal\\sqlmap\\";
		List<String> list = new ArrayList<String>();
		//不需要生成代码的表
		String [] exit_tables={"SYS_USER"};
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/"+dataBaseName;
		String user = "root";
		String password = "123456";
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
			//list.add(rs.getString(3));
			// System.out.println("表名：" + rs.getString(3));
		}
		// 1. 全局配置
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(true) // 是否支持AR模式
				.setOpen(false).setAuthor("yegang") // 作者
				.setOutputDir(javaOutputDir) // 生成路径
				.setFileOverride(true) // 文件覆盖
				.setIdType(IdType.AUTO) // 主键策略
				.setServiceName("%sManager") // 设置生成的service接口的名字的首字母是否为I
				// IEmployeeService
				.setBaseResultMap(true).setBaseColumnList(true);
		config.setServiceImplName("%sManagerImpl");
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
		tc.setController("/templatesMybatis/controller.java.vm");
		tc.setService("/templatesMybatis/service.java.vm");
		tc.setServiceImpl("/templatesMybatis/serviceImpl.java.vm");
		tc.setEntity("/templatesMybatis/entity.java.vm");
		tc.setEntityKt(null);
		tc.setMapper("/templatesMybatis/mapper.java.vm");
		tc.setXml(null);
		// tc.setXml("/templatesMybatis/mapper.xml.vm");
		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/templatesMybatis/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return XMLOutputDir+ tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		focList.add(new FileOutConfig("/templatesMybatis/view.html.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return pageOutputDir+tableInfo.getName().split("_")[0]+"\\templates\\screen\\"+tableInfo.getName().split("_")[1]+"\\"
						+ "view"+tableInfo.getName().split("_")[2].substring(0, 1).toUpperCase()+tableInfo.getName().split("_")[2].substring(1) + ".vm";
			}
		});
		focList.add(new FileOutConfig("/templatesMybatis/edit.html.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return pageOutputDir+tableInfo.getName().split("_")[0]+"\\templates\\screen\\"+tableInfo.getName().split("_")[1]+"\\"
						+ "edit"+tableInfo.getName().split("_")[2].substring(0, 1).toUpperCase()+tableInfo.getName().split("_")[2].substring(1) + ".vm";
			}
		});
		focList.add(new FileOutConfig("/templatesMybatis/filter.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				 List<TableField> list = new ArrayList<TableField>();
				 list =tableInfo.getFields(); 
				 int flag=0;
				 for(TableField field:list){
					 if(field.getType().contains("date")){
						 flag=1;
						 break;
					 }
				 }
				 if(flag==0){
					 return FilterOutputDir+"filter";
				 }else{
				// 自定义输入文件名称
				return FilterOutputDir+tableInfo.getEntityName() + "Filter.java";
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
		list.add(tableName);
		for (String include : list) {
			for(String exitTable:exit_tables){
				if(include.equals(exitTable)){
					break;
				}
				if(include.split("_").length<3){
					break;
				}
			}
			AutoGenerator ag = new AutoGenerator();
			injectionConfig.setFileOutConfigList(focList);
			//action的包路径有第一个下划线"_"前面的字母决定
			String s = include.split("_")[0].toUpperCase();
			config.setControllerName(include.split("_")[1].substring(0, 1).toUpperCase()+include.split("_")[1].substring(1)+include.split("_")[2].substring(0, 1).toUpperCase()+include.split("_")[2].substring(1)+"Action");
			pkConfig.setController("webx."+s.toLowerCase()+".module.action");
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
