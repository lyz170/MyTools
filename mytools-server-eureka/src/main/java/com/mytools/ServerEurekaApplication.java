package com.mytools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Root Document: https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/.
 * Version: 2.2.2.RELEASE
 * <p>
 * To include Eureka Server in your project, use the starter with a group ID of 'org.springframework.cloud'
 * and an artifact ID of 'spring-cloud-starter-netflix-eureka-server' in pom.xml.
 * See: https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#netflix-eureka-server-starter.
 * <p>
 * Usage:
 * http://{hostname}:{port}/server-eureka
 * http://{hostname}:{port}/server-eureka/apps/{service-id}
 * e.g.
 * http://localhost:10001/server-eureka
 * http://localhost:10001/server-eureka/eureka/apps/server-config
 */
@SpringBootApplication
@EnableEurekaServer
public class ServerEurekaApplication implements CommandLineRunner {

    @Value("${app.property.env}")
    public String environment;

    private static final Logger logger = LoggerFactory.getLogger(ServerEurekaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerEurekaApplication.class, args);
        logger.info("<<<<<<<<<< Application Started! >>>>>>>>>>");
        logger.info("<<<<<<<<<< Application name: server-eureka >>>>>>>>>>");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(String.format("<<<<<<<<<< Environment : %s >>>>>>>>>>", environment.toUpperCase()));
    }
}