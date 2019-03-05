package com.battcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Levin
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
