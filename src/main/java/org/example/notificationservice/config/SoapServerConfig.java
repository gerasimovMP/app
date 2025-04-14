package org.example.notificationservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;

@EnableWs
@Configuration
public class SoapServerConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }


    @Bean(name = "notification")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema orderSchema) {
        DefaultWsdl11Definition wsdlDefinition = new DefaultWsdl11Definition();
        wsdlDefinition.setPortTypeName("NotificationPort");
        wsdlDefinition.setLocationUri("/ws");
        wsdlDefinition.setTargetNamespace("http://notificationservice.example.org");
        wsdlDefinition.setSchema(orderSchema);
        return wsdlDefinition;
    }

    @Bean
    public XsdSchema orderSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/orders.xsd"));
    }
}
