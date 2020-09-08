

![license](https://img.shields.io/badge/license-MIT-blue.svg)
![jdk](https://img.shields.io/badge/jdk-1.8%2B-blue.svg)
![language](https://img.shields.io/badge/language-java-yellow.svg)


## What is it?
This project is a yml configuration file version instead of original xml configuration file version of mybatis generator. Using yml configuration to simplify the configuration and add most useful plugins.
This project is currently only support MySQL database, if you are using other databases, open an enhancement issue or become a contributor.



## Contents
- [Requirements](#requirements)
- [Get started](#get-started)
- [Configuration](#configuration)
- [Tips](#tips)
- [TODO](#todo)
- [Contributing](#contributing)


## Requirements
- Jinspector requires JDK1.8+ installed  
- You need to know about yaml language, if you don't know this language, it doesn't matter, cause it's quite simple. You don't need to know all about this language, just follow the configuration sample and you will know how to configure it easily   


## Get Started
Step 1: download the source code 
Step 2: modify the yml config file according to the [configuration](#configuration) rules below  
Step 3: run Generator.java 



## Configuration

Overview configuration
Attribute | Type | Default Value | Required | Description
--- | --- | --- | --- |--- 
datasource | Map |  | true | mysql database connection info, for more details, see "Configuration of datasource" below
targetPackage| Map| | true| package names of mybatis model and sql files, for more details, see "Configuration of targetPackage" below
targetRuntime| String| MyBatis3Simple|true|mybatis generator runtime, options see "Options of targetRuntime" below
mapperSuffixName|String|mapper|false|mapper xml or java file suffix,for example, if set this attribute dao,and  the table name is user, it will generate UserDao.java and UserDao.xml, this attrubute won't work if targetRuntime set to MyBatis3DynamicSql
java8|Boolean|false|false|if true, the model date fields will use LocalDateTime or LocalDate, else use Date
plugins|Map||false|mybatis plugins to enable, suports comment, lombok, swagger, mapperAnnotation, serializable and so on, for more details, see "Configuration of plugins" below
tables|List||true|List of tables you want to generate mybatis files

Configuration of datasource
Attribute | Type | Required | Description
--- | --- | --- |--- 
url|String|true|the url of your database, for example:jdbc:mysql://localhost:3306/test currently only support mysql
username|String|true|database user
password|String|true|database password


Configuration of targetPackage
Attribute | Type | Required | Description   
--- | --- | --- |--- 
model|String|true|model package name, for example:com.example.domain
javaMapper|String|true|java mapper package name, for example:com.example.mapper
xmlMapper|String|true|xml mapper package name, for example:com.example.mapper, it is only avalible when targetRuntime is MyBatis3Simple or MyBatis3, and the source code will be generated to resources folder


Configuration of plugins
Attribute | Type | Required | Description   
--- | --- | --- |--- 
comment|Boolean|false|if true, comment will be generated over all fields
lombok|Boolean|false|add lombok support
swagger|Boolean|false|
mapperAnnotation|Boolean|false|
serializable|Boolean|false|

Options of targetRuntime
Value|Description
--- | ---
MyBatis3DynamicSql|The generated code is dependent on the MyBatis Dynamic SQL Library. The generated code allows tremendous flexibility in query construction. Does not generate XML
MyBatis3Simple|Generates MyBatis3 compatible XML and SQL or MyBatis3 compatible annotated interfaces with no XML. No "by example" or "selective" methods are generated
MyBatis3|Generates MyBatis3 compatible XML and SQL or MyBatis3 compatible annotated interfaces with no XML. With "by example" or "selective" methods are generated





## Tips
Here are some inspectCommand tips:
```
netstat -ano        # look for ports
tasklist         # look processes on windows
ps aux | grep xxx.jar         # find process on linux
......
Waiting for contibuters' advice
```

## TODO
- web interface
- auto recover function
- use netty instead of tomcat for better performance
- complete the code comments and documents
- built-in inspect commands and auto find services
- set up docker version


## Contributing
Your contributions are always welcome!




