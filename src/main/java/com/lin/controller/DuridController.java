package com.lin.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.Beans;
import java.util.HashMap;

//把配置类装配到spring容器中，相当于spring的配置文件
@Configuration
public class DuridController {
    //标注的方法所返回的对象注册到 Spring 容器中。
    @Bean
    //把配置文件中的前缀为spring，datasource的属性装配到DruidDataSource()中
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
//后台监控
    @Bean
    public ServletRegistrationBean a(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
//        后台需要有人登录， 账号密码配置
        HashMap<String, String> iniParameters = new HashMap<>();
//        增加配置
        iniParameters.put("loginUsername","admin");//登录的key值，loginUsername和loginPassword是固定的
        iniParameters.put("loginPassword","123456");
//
//        //允许谁能访问
//        iniParameters.put("allow","localhost");

        bean.setInitParameters(iniParameters);//初始化参数
        return bean;
    }

//    过滤器
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        HashMap<String, String> iniParameters = new HashMap<>();

        iniParameters.put("exclusions","*.js,*.css./druid/*");
        bean.setInitParameters(iniParameters);
        return bean;
    }
}
