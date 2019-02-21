package com.denis.test.server;

import com.denis.test.server.config.WebConfig;
import com.denis.test.server.repository.FirstRepository;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {
    private final static String DISPATCHER = "dispatcher";

    //Запускается перед разворотом контекста
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Создание контекста
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //Регистрация конфига
        ctx.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));


        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, new DispatcherServlet(ctx));
        servlet.addMapping("/");
        //Порядок запуска сервлета
        servlet.setLoadOnStartup(1);
    }
}
