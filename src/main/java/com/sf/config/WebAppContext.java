package com.sf.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.sf.util.Parametros;


/**
 * @author Petri Kainulainen
 */
@Configuration
@ComponentScan(basePackages = {
        "com.sf.social.signinmvc.common.controller",
        "com.sf.social.signinmvc.security.controller",
        "com.sf.social.signinmvc.user.controller",
        "com.gja.gestionCasos.casos",
        "com.gja.gestionCasos",
        "com.sf.config"
})

@EnableWebMvc
public class WebAppContext extends WebMvcConfigurerAdapter {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        //javaMailSender.setProtocol("SMTP");
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.transport.protocol", "smtp");
//        mailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        mailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        mailProperties.setProperty("mail.smtp.starttls.enable","true");
        javaMailSender.setJavaMailProperties(mailProperties);
        javaMailSender.setHost(Parametros.getMailHost());
        javaMailSender.setPort(Parametros.getMailPort());
        javaMailSender.setUsername(Parametros.getCorreoEnvioDocumentos());
        javaMailSender.setPassword(Parametros.getPasswordCorreoEnvios());

        return javaMailSender;
    }

 /*  @Bean @Scope(value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
     public CommonsMultipartResolver getMultipartResolver() {
         CommonsMultipartResolver mr = new CommonsMultipartResolver();
         mr.setMaxUploadSize(50000000);
         if (user_is_not_admin) {
             
         }
         return mr;
     }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128KB");
        factory.setMaxRequestSize("128KB");
        return factory.createMultipartConfig();
    }
*/
    
    @Bean
    public MultipartResolver multipartResolver() {
    	CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    	resolver.setMaxUploadSize(Parametros.getMaxSizeArchivo()*1024L*1024L);
    	resolver.setMaxInMemorySize(Parametros.getMaxInSizeMemory()*1024*1024);
        return resolver;
    }

    /*@Bean(name="wordViewResolver")
    public ViewResolver getwordViewResolver() {
    	UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setOrder(1);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".docx");

        return viewResolver;
    }*/
}
