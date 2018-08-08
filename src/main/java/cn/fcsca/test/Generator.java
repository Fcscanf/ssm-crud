package cn.fcsca.test;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generator
 *
 * @description Mybatis逆向工程工具类，在测试包运行降低耦合
 * @author Fcscanf@樊乘乘
 * @date 2018-08-05 下午 16:51
 */
public class Generator {
    public static void main(String[] args) throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException {
        List<String> warings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mdb.xml");
        ConfigurationParser configurationParser = new ConfigurationParser(warings);
        Configuration configuration = configurationParser.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,callback,warings);
        myBatisGenerator.generate(null);
    }
}
