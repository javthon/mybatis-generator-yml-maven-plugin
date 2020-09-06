package com.javthon.mybatisgeneratorbestpractice;

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
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(Generator.class.getClassLoader().getResourceAsStream("config/test.xml"));
        // 解决IDEA下运行，多个模块路径冲突问题
        String cpath = Generator.class.getClassLoader().getResource("config/test.xml").toString();
        cpath = cpath.substring(0, cpath.indexOf("target")).replace("file:/", "");
        Context context = config.getContexts().get(0);
        context.getJavaModelGeneratorConfiguration().setTargetProject(cpath+context.getJavaModelGeneratorConfiguration().getTargetProject());
        context.getSqlMapGeneratorConfiguration().setTargetProject(cpath+context.getSqlMapGeneratorConfiguration().getTargetProject());
        context.getJavaClientGeneratorConfiguration().setTargetProject(cpath+context.getJavaClientGeneratorConfiguration().getTargetProject());
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
