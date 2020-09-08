package com.javthon.mybatisgeneratorbestpractice;

import com.javthon.mybatisgeneratorbestpractice.utils.ConfigurationParser;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        System.out.println("Generating config file");
        org.mybatis.generator.config.xml.ConfigurationParser cp = new org.mybatis.generator.config.xml.ConfigurationParser(warnings);
        ConfigurationParser creatXml = new ConfigurationParser();
//        String ymlPath="F:\\项目\\mybatis-generator-bestpractice\\target\\classes\\generatorConfig.yml";
        String ymlPath = Generator.class.getClassLoader().getResource("generatorConfig.yml").getPath();//获取文件路径
        ymlPath = java.net.URLDecoder.decode(ymlPath,"utf-8");
        InputStream xml = creatXml.createXML(ymlPath);
        System.out.println("Config file generated");
        System.out.println("Parsing config file, please wait");
        Configuration config = cp.parseConfiguration(xml);
        Context context = config.getContexts().get(0);
        context.getJavaModelGeneratorConfiguration(). setTargetProject("src/main/java");
        context.getSqlMapGeneratorConfiguration().setTargetProject("src/main/java");
        context.getJavaClientGeneratorConfiguration().setTargetProject("src/main/java");
        ////////////////////
        context.getJavaModelGeneratorConfiguration(). setTargetProject(context.getJavaModelGeneratorConfiguration().getTargetProject());
        context.getSqlMapGeneratorConfiguration().setTargetProject(context.getSqlMapGeneratorConfiguration().getTargetProject());
        context.getJavaClientGeneratorConfiguration().setTargetProject(context.getJavaClientGeneratorConfiguration().getTargetProject());
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("All jobs are done");
    }

}
