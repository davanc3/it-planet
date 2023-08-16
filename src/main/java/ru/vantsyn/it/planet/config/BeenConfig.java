package ru.vantsyn.it.planet.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import ru.vantsyn.it.planet.filter.CheckAuthorizeFilter;

@Configuration
public class BeenConfig {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public FilterRegistrationBean<CheckAuthorizeFilter> checkAuthorizeFilterRegistrationBean(){
        FilterRegistrationBean<CheckAuthorizeFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CheckAuthorizeFilter());
        registrationBean.addUrlPatterns("/registration");
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
