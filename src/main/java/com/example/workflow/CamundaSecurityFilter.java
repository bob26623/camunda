package com.example.workflow;

import javax.servlet.Filter;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import io.digitalstate.camunda.authentication.jwt.ProcessEngineAuthenticationFilterJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaSecurityFilter {
    @Value("${camunda.rest-api.jwt.secret-path}")
    String jwtSecretPath;

    @Value("${camunda.rest-api.jwt.validator-class}")
    String jwtValidatorClass;

    @Bean
    public FilterRegistrationBean processEngineAuthenticationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("camunda-auth");
        //registration.setName("camunda-jwt-auth");
        registration.setFilter(getProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider",
                "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
                //"io.digitalstate.camunda.authentication.jwt.AuthenticationFilterJwt");
        //registration.addInitParameter("jwt-secret-path", jwtSecretPath);
        //registration.addInitParameter("jwt-validator", jwtValidatorClass);
        registration.addUrlPatterns("/engine-rest/*");
        return registration;
    }

    @Bean
    public Filter getProcessEngineAuthenticationFilter() {
        return new ProcessEngineAuthenticationFilter();
        //return new ProcessEngineAuthenticationFilterJwt();
    }
}