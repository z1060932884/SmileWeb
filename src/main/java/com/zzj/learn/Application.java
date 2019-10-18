package com.zzj.learn;

import com.zzj.learn.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//spring-boot应用标识
@SpringBootApplication
//mapper接口类扫描包配置
@MapperScan("com.zzj.learn.dao")
@ComponentScan(basePackages = {"com.zzj.learn","org.n3r.idworker"})
public class Application extends SpringBootServletInitializer{
    @Bean
    public SpringUtil getSpingUtil() {
        return new SpringUtil();
    }

    public static void main(String[] args){
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
//        SpringApplication.run(App.class,args);
        new Application()
                .configure(new SpringApplicationBuilder(Application.class))
                .run(args);
    }

}
