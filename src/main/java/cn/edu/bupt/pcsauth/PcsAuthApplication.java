package cn.edu.bupt.pcsauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

/**
 * 入口
 * @author arron
 * @date 19-5-22
 * @param
 * @return
 */
@EnableResourceServer
@EnableEurekaClient
@SpringBootApplication
@ComponentScan({"cn.edu.bupt"})
public class PcsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcsAuthApplication.class, args);
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","\"|{}");

    }
}
