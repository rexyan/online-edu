package com.yanrs.edu.teacher;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * @author
 * @since 2018/12/13
 */
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mbg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");

        gc.setAuthor("rex");  // 代码作者 -----------------------【根据实际情况修改】 -----------------------
        gc.setOpen(false);  //生成后是否打开资源管理器
        gc.setFileOverride(false);  //重新生成时文件是否覆盖
        gc.setServiceName("%sService");  //去掉Service接口的首字母I, 不添加名称类似于 IUserService
        gc.setIdType(IdType.ID_WORKER);  //主键策略
        gc.setDateType(DateType.ONLY_DATE);  //如果数据库中字段是日期类型，那么生成的实体类中的属性也是日期类型
        gc.setSwagger2(true);  //开启Swagger2模式

        mbg.setGlobalConfig(gc);

        // 3、数据源配置  -----------------------【根据实际情况修改】 -----------------------
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/edu");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mbg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("teacher");  // -----------------------【根据实际情况修改】 -----------------------
        pc.setParent("com.yanrs.edu");  // setModuleName 和 setParent 的值合起来为完成的包路径 com.yanrs.edu  -----------------------【根据实际情况修改】 -----------------------

        pc.setController("controller");  // 在 com.yanrs.edu 下生成  controller 包
        pc.setEntity("entity");  // 在 com.yanrs.edu 下生成  entity 包
        pc.setService("service");  // 在 com.yanrs.edu 下生成  service 包
        pc.setMapper("mapper");  // 在 com.yanrs.edu 下生成  mapper 包
        mbg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("edu_chapter", "edu_video");  // 数据库表的名称， 如果是多个表 strategy.setInclude("表1", "表2"); -----------------------【根据实际情况修改】 -----------------------

        strategy.setNaming(NamingStrategy.underline_to_camel); //数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_");  //生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);  //数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true);  // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setRestControllerStyle(true);  //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true);  //url中驼峰转连字符
        mbg.setStrategy(strategy);

        // 6、执行
        mbg.execute();
    }
}
