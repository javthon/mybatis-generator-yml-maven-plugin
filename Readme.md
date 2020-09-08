

![license](https://img.shields.io/badge/license-MIT-blue.svg)
![jdk](https://img.shields.io/badge/jdk-1.8%2B-blue.svg)
![language](https://img.shields.io/badge/language-java-yellow.svg)


## 造轮子目的
- 使用yml配置来简化MyBatis Generator默认使用的xml配置
- 支持注释生成、lombok、swagger等常用插件的方便配置
- 介绍使用某种配置或插件的好处
- 试图寻找针对不同情景的最优配置


## 内容
- [环境要求](#环境要求)
- [如何使用](#如何使用)
- [配置](#配置)
- [插件介绍](#插件介绍)
- [待完善](#待完善)
- [Contributing](#contributing)


## 环境要求
- 安装好Java8和一个IDE 
- 如要使用lombok，需要在IDE中安装lombok插件   


## 如何使用
Step 1: 下载源码<br>
Step 2: 按您对源码中配置的理解修改配置 或根据以下[配置](#配置) 说明修改配置 <br> 
Step 3: 运行Generator.java生成代码 



## 配置
### 完整配置样例
```
mybatisGenerator:
    datasource:
        url: jdbc:mysql://35.234.41.130:3306/JS_mysql_db
        username: root
        password: Woshixxf666

    targetPackage:
        model: com.example.domain
        javaMapper: com.example.dao
        xmlMapper: com.example.dao

    targetRuntime: MyBatis3
    mapperSuffixName: mapper
    java8: false
    disableExample: true

    plugins:
        comment: true
        lombok: true
        swagger: false
        mapperAnnotation: true
        serializable: true

    tables:
        - user
        - role
```
### 配置概览
属性 | 类型 | 默认值 | 是否必须 | 描述
--- | --- | --- | --- |--- 
datasource | Map |  | 是 | 数据库的连接信息, 见下方的"数据源配置"，当前只支持mysql
targetPackage| Map| | 是| 生成代码的包路径，见下方targetPackage配置
targetRuntime| String| MyBatis3Simple|是|mybatis generator生产代码的格式,见下方targetRuntime可选项
mapperSuffixName|String|mapper|否|mapper类或xml文件的后缀名,如果将此属性设置为dao，并且表名是user，它将生成UserDao.java和UserDao.xml，如果targetRuntime设置为MyBatis3DynamicSql，则此属性将不起作用
java8|Boolean|false|否|如果为true，则生成模型日期字段将使用Java8的LocalDateTime或LocalDate，否则使用Date
disableExample|Boolean|true|否|此属性仅在targetRuntime为MyBatis3生效，为true时mapper不生成"by example"代码
plugins|Map||否| 配置是否开启注释, lombok, swagger, mapperAnnotation, serializable等插件, 详情见下方插件配置
tables|List||是|多个表格名，配置方式见generatorConfig.yml样例

#### 数据源配置
属性 | 类型 | 是否必须 | 描述
--- | --- | --- |--- 
url|String|是|数据库连接, 如：jdbc:mysql://localhost:3306/test 当前只支持mysql
username|String|是|数据库用户名
password|String|是|数据库密码


#### targetPackage配置
属性 | 类型 | 是否必须 | 描述   
--- | --- | --- |--- 
model|String|否|生成模型的包路径, 如：com.example.domain
javaMapper|String|否|生成的java mapper的包路径, 如:com.example.mapper
xmlMapper|String|否|生成的xml mapper的包路径, 如：com.example.mapper, 该属性仅在targetRuntime为MyBatis3Simple或MyBatis3时可用，xml mapper代码将生成至resources文件夹下


#### 插件配置
属性 | 类型 | 是否必须 | 描述   
--- | --- | --- |--- 
comment|Boolean|否|是否开启model的注释
lombok|Boolean|否|是否使用lombok，不生成setter和getter
swagger|Boolean|否|是否使用swagger2注解
mapperAnnotation|Boolean|否|是否在mapper类上加@Mapper注解
serializable|Boolean|否|是否实现Serializable接口

#### targetRuntime的可选值
值|描述
--- | ---
MyBatis3DynamicSql|生成的代码依赖于MyBatis动态SQL库。 生成的代码为查询构造提供了极大的灵活性。 不生成XML。mybatis generator 1.4.0官方推荐此方式
MyBatis3Simple|生成mapper java接口和xml配置文件。没有"by example" 或者"selective"方法，代码较简洁
MyBatis3|生成mapper java接口和xml配置文件。有"by example" 或者 "selective" 方法，代码比较啰嗦

## 插件介绍
#### 未使用任何插件前代码
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
#### 注释插件
使用后在每个字段上方生成数据库中对应字段的注释：
```
import java.util.Date;

public class Role {
    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
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
#### lombok插件
使用后没有setter和getter，大大简化了代码：
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

#### swagger插件
项目中使用swagger2作为接口测试框架的可以使用此插件：
```
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="com.example.domain.Role")
public class Role {
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="角色名称")
    private String name;

    @ApiModelProperty(value="角色备注")
    private String remark;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
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

#### mapperAnnotation插件
在java mapper接口上添加了@Mapper注解

#### serializable插件
model实现了Serializable接口

## 待完善
- 支持更多数据库
- 支持更多实用的插件
- 探索MyBatis3DynamicSql和MyBatis3的优劣
- 做成maven插件


## Contributing
Your contributions are always welcome!




