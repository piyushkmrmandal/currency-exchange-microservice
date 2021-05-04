package com.microservice.limitsservice.controller;

import com.microservice.limitsservice.bean.LimitConfiguration;
import com.microservice.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfigurations(){
        return new LimitConfiguration(configuration.getMaximum(),
                configuration.getMinimum());
    }

    @GetMapping("/fault-tolerance")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfig")
    public LimitConfiguration retrieveConfigurations(){
        throw new RuntimeException("Service thrown Exception");
    }

    public LimitConfiguration fallbackRetrieveConfig(){
        return new LimitConfiguration(0,
               0);
    }


}
