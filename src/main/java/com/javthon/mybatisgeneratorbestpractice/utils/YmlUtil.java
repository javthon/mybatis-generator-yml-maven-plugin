package com.javthon.mybatisgeneratorbestpractice.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YmlUtil {
    public static Map<String,Object> getObjectMapFromSource(String filePath){
        Yaml yaml = new Yaml();
        InputStream is = null;
        try {
             is=new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return yaml.loadAs(is, Map.class);
    }


    public static void main(String[] args) {
//        Map<String, Object> source = YmlUtil.getObjectMapFromSource();
//        System.out.println(source);
    }


}
