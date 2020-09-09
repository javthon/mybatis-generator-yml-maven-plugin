package com.javthon.mybatisgeneratorbestpractice;

import com.javthon.mybatisgeneratorbestpractice.utils.ConfigurationParser;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        log.info("Generating config file");
        org.mybatis.generator.config.xml.ConfigurationParser cp = new org.mybatis.generator.config.xml.ConfigurationParser(warnings);
        ConfigurationParser creatXml = new ConfigurationParser();
        String ymlPath = Generator.class.getClassLoader().getResource("generatorConfig.yml").getPath();
        ymlPath = java.net.URLDecoder.decode(ymlPath,"utf-8");
        InputStream xml = creatXml.createXML(ymlPath);
        log.info("Config file generated");
        log.info("Parsing config file, please wait");
        Configuration config = cp.parseConfiguration(xml);
        Context context = config.getContexts().get(0);
        // set package name
        context.getJavaModelGeneratorConfiguration(). setTargetProject(context.getJavaModelGeneratorConfiguration().getTargetProject());
        context.getSqlMapGeneratorConfiguration().setTargetProject(context.getSqlMapGeneratorConfiguration().getTargetProject());
        context.getJavaClientGeneratorConfiguration().setTargetProject(context.getJavaClientGeneratorConfiguration().getTargetProject());
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        log.info("Mybatis files are generated");
    }

}
