package com.javthon.mybatisgeneratorbestpractice;

public class Config {
    public final static String DRIVER_MYSQL="com.mysql.cj.jdbc.Driver";
    public final static String DRIVER_SQL_SERVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public final static String DRIVER_SQL_ORACLE="oracle.jdbc.driver.OracleDriver";


    public final static String URL_MYSQL_PREFIX="jdbc:mysql://";
    public final static String URL_SQL_SERVER_PREFIX="jdbc:sqlserver://";
    public final static String URL_ORACLE_PREFIX="jdbc:oracle:thin:@";

    public final static String URL_SQL_SERVER_SUFFIX=";databasename=";
    public final static String URL_MYSQL_SUFFIX="/";
    public final static String URL_ORACLE_SUFFIX=URL_MYSQL_SUFFIX;


}
