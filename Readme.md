
English | [简体中文](https://github.com/javthon/mybatis-generator-bestpractice/blob/master/Readme-CN.md)<br>
![license](https://img.shields.io/badge/license-MIT-blue.svg)
![jdk](https://img.shields.io/badge/jdk-1.8%2B-blue.svg)
![language](https://img.shields.io/badge/language-java-yellow.svg)


## Purpose
- Use yml configuration to simplify the xml configuration used by MyBatis Generator by default
- Supports convenient configuration of common plugins such as annotation generation, lombok, swagger, etc.
- Introduce the benefits of using certain configurations or plugins
- Try to find the optimal configuration for different scenarios


## Content
- [Environmental requirements](#Environmental requirements)
- [How to use](#How to use)
- [Configuration](#Configuration)
- [Plugin Introduction](#Plugin Introduction)
- [TODO] (#TODO)
- [Contributing](#contributing)


## Environmental requirements
- Make sure you have installed Java8 and a IDE such as Intellij Idea and Eclipse
- To use lombok, you need to install the lombok plugin in the IDE  


## How to use
Step 1: Download the source code<br>
Step 2: Modify the configuration according to your understanding of the configuration in the source code, or modify the configuration according to the following [Configuration](#Configuration) instructions <br>
Step 3: Run Generator.java to generate code



## Configuration
### Complete configuration example
```
mybatisGenerator:
    datasource:
        url: jdbc:mysql://localhost:3306/test
        username: root
        password: root

    targetPackage:
        model: com.example.domain
        mapper: com.example.dao
        javaXmlFilesSamePackage: true

    targetRuntime: MyBatis3
    mapperSuffixName: dao
    java8: false
    disableExample: true

    plugins:
        comment: true
        lombok: true
        swagger: false
        mapperAnnotation: false
        serializable: false

    tables:
        - user
        - role
```
### Configuration overview
Attribute | Type | Default | Required | Description
--- | --- | --- | --- |--- 
datasource | Map |  | true | database connection information, details see "datasource Configuration" below, currently only supports mysql
targetPackage| Map| | true| The package path of the generated code, see "targetPackage configuration" below
targetRuntime| String| MyBatis3|true|mybatis generator targetRuntime,see targetRuntime options below
mapperSuffixName|String|mapper|false|The suffix name of the mapper class or xml file. If this attribute is set to dao and the table name is user, it will generate UserDao.java and UserDao.xml. If targetRuntime is set to MyBatis3DynamicSql, this attribute will not work
java8|Boolean|false|false|If true, the generated model date field will use Java8's LocalDateTime or LocalDate, otherwise use Date
disableExample|Boolean|true|false|This attribute only takes effect when the targetRuntime is MyBatis3. When it is true, the mapper will not generate "by example" code
plugins|Map||false| Configure whether to enable annotations, lombok, swagger, mapperAnnotation, serializable and other plugins, see plugin configuration below for details
tables|List||false|Multiple table names, see generatorConfig.yml sample and you'll know how to configure it

#### datasource configuration
Attribute | Type | Required | Description
--- | --- | --- |--- 
url|String|true|database connection, for example：jdbc:mysql://localhost:3306/test currently only supports mysql
username|String|true|database user
password|String|true|database password


#### targetPackage configuration
Attribute | Type | Required | Description   
--- | --- | --- |--- 
model|String|false|model package name, for example: com.example.domain
mapper|String|false|Package path of generated java interfaces and xml mappers, for example: com.example.mapper
javaXmlFilesSamePackage|Boolean|false|if true，java interfaces and xml mappers will be generated in one package，If false, the xml mapper code will be generated under the resources directory. This attribute is only available when the targetRuntime is MyBatis3Simple or MyBatis3


#### plugin configuration
Attribute | Type | Required | Description   
--- | --- | --- |--- 
comment|Boolean|false|whether to generate comments above the fields
lombok|Boolean|false|whether to use lombok, no setter and getter
swagger|Boolean|false|whether to use swagger2 annotations
mapperAnnotation|Boolean|false|whether to add @Mapper annotation on the mapper class
serializable|Boolean|false|whether to implement the Serializable interface

#### targetRuntime options
Value|Description
--- | ---
MyBatis3DynamicSql|The generated code relies on the MyBatis dynamic SQL library. The generated code provides great flexibility for query construction. Does not generate XML. mybatis generator 1.4.0 officially recommends this method
MyBatis3Simple|Generate mapper java interface and xml configuration file. There is no "by example" or "selective" method, the code is more concise
MyBatis3|Generate mapper java interface and xml configuration file. There are "by example" or "selective" methods, the code is more verbose

## Plugin Introduction
#### Before using any plugin
```
import java.util.Date;

public class Role {
    private Long id;

    private String name;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
```
#### Comment Plugin
It will generate a note of the corresponding field in the database above each field：
```
import java.util.Date;

public class Role {
    /**
     * id
     */
    private Long id;

    /**
     * role name
     */
    private String name;

    /**
     * role remark
     */
    private String remark;

    /**
     * role create time
     */
    private Date createTime;

    /**
     * role update time
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
```
#### Lombok Plugin
No setter and getter, makes code clean：
```
import java.util.Date;
import lombok.Data;

/**
 * role
 * @author Tensorflow
 * @date 2020-09-08 22:24:45
 */
@Data
public class Role {
    private Long id;

    private String name;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
```

#### Swagger Plugin
If you are using swagger2 in your project, this may be helpful：
```
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="com.example.domain.Role")
public class Role {
    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="role name")
    private String name;

    @ApiModelProperty(value="role remark")
    private String remark;

    @ApiModelProperty(value="create time")
    private Date createTime;

    @ApiModelProperty(value="update time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
```

#### mapperAnnotation Plugin
Add @Mapper annotation on java mapper interface

#### serializable Plugin
Make models implement the Serializable interface

## TODO
- Support more databases
- Support more useful plugins
- Explore the pros and cons of MyBatis3DynamicSql and MyBatis3
- Make a maven plugin


## Contributing
If the database you are using is not supported, or does not match your database version, please new issues or join the development of this project.<br>
If you find errors in this project or have good ideas, please join<br>
Your contributions are always welcome!




