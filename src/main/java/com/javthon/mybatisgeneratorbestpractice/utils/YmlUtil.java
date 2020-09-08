package com.javthon.mybatisgeneratorbestpractice.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class YmlUtil {

    /**
     * Load yml file from a specific file path
     */
    public static Map<String,Object> getObjectMapFromSource(String filePath){
        Yaml yaml = new Yaml();
        InputStream is = null;
        try {
             is=new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
        }
        return yaml.loadAs(is, Map.class);
    }

}
