package com.javthon.mybatisgeneratorbestpractice;

import com.javthon.mybatisgeneratorbestpractice.utils.XMLUtil;
import java.io.File;
import java.io.FileInputStream;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        System.out.println("Generating config file");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        XMLUtil creatXml = new XMLUtil();
        String ss="F:\\项目\\mybatis-generator-bestpractice\\src\\main\\resources\\config\\test.xml";
        creatXml.createXML("F:\\项目\\mybatis-generator-bestpractice\\src\\main\\resources\\application.yml",ss);
        System.out.println("Config file generated");
        Thread.sleep(1000);
        System.out.println("Parsing config file, please wait");
        Configuration config = cp.parseConfiguration(new FileInputStream(new File("F:\\项目\\mybatis-generator-bestpractice\\src\\main\\resources\\config\\test.xml")));
        // 解决IDEA下运行，多个模块路径冲突问题
//        cpath = cpath.substring(0, cpath.indexOf("target")).replace("file:/", "");
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
