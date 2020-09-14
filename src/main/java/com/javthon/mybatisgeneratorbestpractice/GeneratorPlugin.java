package com.javthon.mybatisgeneratorbestpractice;

import com.javthon.mybatisgeneratorbestpractice.utils.ConfigurationParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Mojo(name = "generate")
public class GeneratorPlugin extends AbstractMojo {

    @Parameter(name="configurationFile", defaultValue = "src/main/resources/generatorConfig.yml", readonly = true )
    private File configurationFile;

    @SneakyThrows
    @Override
    public void execute() {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        log.info("Loading configuration file");
        org.mybatis.generator.config.xml.ConfigurationParser cp = new org.mybatis.generator.config.xml.ConfigurationParser(warnings);
        ConfigurationParser creatXml = new ConfigurationParser();
        InputStream xml = creatXml.createXML(configurationFile);
        log.info("Configuration file loaded");
        log.info("Parsing configuration file, please wait...");
        Configuration config = cp.parseConfiguration(xml);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        log.info("Mybatis code files are successfully generated");
    }
}
