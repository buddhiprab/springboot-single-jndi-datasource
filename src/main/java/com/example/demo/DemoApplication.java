package com.example.demo;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatWebServer(tomcat);
			}
			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setName("jdbc/apidb");
				resource.setType(DataSource.class.getName());
				//resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
//				resource.setProperty("factory", "com.zaxxer.hikari.HikariDataSource");
				resource.setProperty("factory", "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory");
				resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
				resource.setProperty("url", "jdbc:mysql://localhost:3306/apidb?useSSL=false&allowPublicKeyRetrieval=true");
				resource.setProperty("password", "Mouse123");
				resource.setProperty("username", "root");
				context.getNamingResources().addResource(resource);
				System.out.println(context.getNamingResources().findResource("jdbc/apidb"));
			}
		};
	}
}
