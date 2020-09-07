package com.javthon.mybatisgeneratorbestpractice.utils;

import com.google.common.base.CaseFormat;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLUtil {


    /**
     * 生成xml
     */
    public void createXML(String ymlPath, String xmlPath) throws ParserConfigurationException {
        // Create document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        //Create doc type
        DOMImplementation domImpl = document.getImplementation();
        DocumentType doctype = domImpl.createDocumentType("generatorConfiguration", "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN", "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
        document.appendChild(doctype);
        Element bookstore = document.createElement("generatorConfiguration");
        //向bookstore根节点中添加子节点book
        Element context = document.createElement("context");
        context.setAttribute("id","simple");
        context.setAttribute("targetRuntime","MyBatis3");

        Map<String, Object> objectMapFromSource = YmlUtil.getObjectMapFromSource(ymlPath);
        Map<String, Object> objectMap = (Map<String, Object>) objectMapFromSource.get("mybatisGenerator");


        Map<String, Object> mapperConfig = (Map<String, Object>) objectMap.get("mapperConfig");
        Boolean comment = (Boolean) mapperConfig.get("comment");
        Boolean lombok = (Boolean) mapperConfig.get("lombok");
        Boolean swagger = (Boolean) mapperConfig.get("swagger");

        Boolean enableCountByExample = (Boolean) mapperConfig.get("enableCountByExample");
        Boolean enableUpdateByExample = (Boolean) mapperConfig.get("enableUpdateByExample");
        Boolean enableDeleteByExample = (Boolean) mapperConfig.get("enableDeleteByExample");
        Boolean enableSelectByExample = (Boolean) mapperConfig.get("enableSelectByExample");
        Boolean selectByExampleQueryId = (Boolean) mapperConfig.get("selectByExampleQueryId");

        String mapperSuffixName = (String) mapperConfig.get("mapperSuffixName");
        Map<String,String> tableConfig = new HashMap<>();
        tableConfig.put("enableCountByExample",String.valueOf(enableCountByExample));
        tableConfig.put("enableUpdateByExample",String.valueOf(enableUpdateByExample));
        tableConfig.put("enableDeleteByExample",String.valueOf(enableDeleteByExample));
        tableConfig.put("enableSelectByExample",String.valueOf(enableSelectByExample));
        tableConfig.put("selectByExampleQueryId",String.valueOf(selectByExampleQueryId));

        List<String> tables = ( List<String>) objectMap.get("tables");

        //  swagger
        if(swagger){
            appendSwaggerPlugin(document,context);
        }
        if(lombok){
            appendLombokPlugin(document,context);
        }

//        if(comment){
//            appendCommentPlugin(document,context);
//        }

        disableDefaultComment(document,context);
        ///////mysql////////////
        Map<String, Object> datasource = (Map<String, Object>)objectMap.get("datasource");
        String url = (String) datasource.get("url");
        String username = (String) datasource.get("username");
        String password = String.valueOf(datasource.get("password"));
        appendConnectionConfig(document,context,url,username,password);
        ///////mysql////////////
        setJavaTypeResolver(document,context);

        ///////targetPackage/////////
        Map<String, Object> targetPackage = (Map<String, Object>) objectMap.get("targetPackage");
        String model = (String) targetPackage.get("model");
        String javaMapper = (String) targetPackage.get("javaMapper");
        String xmlMapper = (String) targetPackage.get("xmlMapper");
        appendTargetPackage(document,context,model,javaMapper,xmlMapper);
        ///////targetPackage/////////

        appendTables(document,context,tables,mapperSuffixName,tableConfig);
        bookstore.appendChild(context);



        //将bookstore节点（已经包含了book）添加到dom树中
        document.appendChild(bookstore);
        //创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            //创建Transformer对象
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");

            tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, document.getDoctype().getPublicId());
            tf.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());

            tf.transform(new DOMSource(document),new StreamResult(new File(xmlPath)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates Swagger2 annotations on models
     * <plugin type="com.javthon.mybatisgeneratorbestpractice.plugins.Swagger2Plugin">
     * 		<property name="apiModelAnnotationPackage" value="io.swagger.annotations.ApiModel" />
     * 		<property name="apiModelPropertyAnnotationPackage" value="io.swagger.annotations.ApiModelProperty" />
     * </plugin>
     * @param document
     * @param context
     */
    public void appendSwaggerPlugin(Document document, Element context){
        Element pluginSwagger = document.createElement("plugin");
        pluginSwagger.setAttribute("type","com.javthon.mybatisgeneratorbestpractice.plugins.Swagger2Plugin");
        Element pluginSwaggerProperty1 = document.createElement("property");
        pluginSwaggerProperty1.setAttribute("name","apiModelAnnotationPackage");
        pluginSwaggerProperty1.setAttribute("value","io.swagger.annotations.ApiModel");
        Element pluginSwaggerProperty2 = document.createElement("property");
        pluginSwaggerProperty2.setAttribute("name","apiModelPropertyAnnotationPackage");
        pluginSwaggerProperty2.setAttribute("value","io.swagger.annotations.ApiModelProperty");
        pluginSwagger.appendChild(pluginSwaggerProperty1);
        pluginSwagger.appendChild(pluginSwaggerProperty2);
        context.appendChild(pluginSwagger);
    }


    /**
     * Generates lombok annotations on models
     * <plugin type="com.javthon.mybatisgeneratorbestpractice.plugins.LombokPlugin" >
     * 		<property name="hasLombok" value="true"/>
     * 	</plugin>
     * @param document
     * @param context
     */
    public void appendLombokPlugin(Document document, Element context){
        Element pluginSwagger = document.createElement("plugin");
        pluginSwagger.setAttribute("type","com.javthon.mybatisgeneratorbestpractice.plugins.LombokPlugin");
        Element pluginSwaggerProperty1 = document.createElement("property");
        pluginSwaggerProperty1.setAttribute("name","hasLombok");
        pluginSwaggerProperty1.setAttribute("value","true");
        pluginSwagger.appendChild(pluginSwaggerProperty1);
        context.appendChild(pluginSwagger);
    }

    /**
     * Generates lombok annotations on models
     * @param document
     * @param context
     */
    public void appendCommentPlugin(Document document, Element context){
        Element pluginSwagger = document.createElement("plugin");
        pluginSwagger.setAttribute("type","com.javthon.mybatisgeneratorbestpractice.plugins.CommentPlugin");
        Element pluginSwaggerProperty1 = document.createElement("property");
        pluginSwaggerProperty1.setAttribute("name","hasLombok");
        pluginSwaggerProperty1.setAttribute("value","true");
        pluginSwagger.appendChild(pluginSwaggerProperty1);
        context.appendChild(pluginSwagger);
    }


    public void appendConnectionConfig(Document document, Element context, String url, String user, String password){
        Element pluginSwagger = document.createElement("jdbcConnection");
        pluginSwagger.setAttribute("driverClass","com.mysql.cj.jdbc.Driver");
        pluginSwagger.setAttribute("connectionURL",url);
        pluginSwagger.setAttribute("userId",user);
        pluginSwagger.setAttribute("password",password);
        context.appendChild(pluginSwagger);
    }

    public void disableDefaultComment(Document document, Element context){
        Element temp = document.createElement("commentGenerator");
        Element temp1 = document.createElement("property");
        temp1.setAttribute("name","suppressAllComments");
        temp1.setAttribute("value","true");
        Element temp2 = document.createElement("property");
        temp2.setAttribute("name","suppressDate");
        temp2.setAttribute("value","true");
        temp.appendChild(temp1);
        temp.appendChild(temp2);

        context.appendChild(temp);
    }
    public void setJavaTypeResolver(Document document, Element context){
        Element temp3 = document.createElement("javaTypeResolver");
        Element temp4 = document.createElement("property");
        temp4.setAttribute("name","forceBigDecimals");
        temp4.setAttribute("value","false");
        Element temp5 = document.createElement("property");
        temp5.setAttribute("name","useJSR310Types");
        temp5.setAttribute("value","true");
        temp3.appendChild(temp4);
        temp3.appendChild(temp5);
        context.appendChild(temp3);
    }

    public void appendTargetPackage(Document document, Element context,String modelPackage, String javaMapperPackage, String xmlMapperPackage){
        Element pluginSwagger = document.createElement("javaModelGenerator");
        pluginSwagger.setAttribute("targetPackage",modelPackage);
        pluginSwagger.setAttribute("targetProject","src/main/java");
        context.appendChild(pluginSwagger);

        Element pluginSwagger1 = document.createElement("sqlMapGenerator");
        pluginSwagger1.setAttribute("targetPackage",javaMapperPackage);
        pluginSwagger1.setAttribute("targetProject","src/main/resources");
        context.appendChild(pluginSwagger1);

        Element pluginSwagger2 = document.createElement("javaClientGenerator");
        pluginSwagger2.setAttribute("type","XMLMAPPER");
        pluginSwagger2.setAttribute("targetPackage",xmlMapperPackage);
        pluginSwagger2.setAttribute("targetProject","src/main/java");
        context.appendChild(pluginSwagger2);

    }


    public void appendTables(Document document, Element context, List<String> tableNames, String mapperSuffixName, Map<String,String> map){
        for(String tableName : tableNames){
            Element pluginSwagger = document.createElement("table");
            pluginSwagger.setAttribute("tableName",tableName);
            Set<String> keys = map.keySet();
            for(String key : keys){
                pluginSwagger.setAttribute(key,map.get(key));
            }
            String mapperName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)+CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, mapperSuffixName);
            pluginSwagger.setAttribute("mapperName",mapperName);
            context.appendChild(pluginSwagger);
        }

    }

    public static void main(String[] args) throws ParserConfigurationException {
//        XMLUtil creatXml = new XMLUtil();
//        creatXml.createXML();
    }

}