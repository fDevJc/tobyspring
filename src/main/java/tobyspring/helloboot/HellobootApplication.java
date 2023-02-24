package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/*
			org.apache.catalina.startup.Tomcat;
			- 임베디드 톰캣을 제공
			- 여러 설정정보를 넣어야됨
			- 스프링부트에서 여러 설정정보를 기본적으로 세팅해주는 TomcatServletWebServerFactory가 존재
*/
@Configuration
@ComponentScan
public class HellobootApplication {
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	public static void main(String[] args) {
		//Spring Container
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
				DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

				WebServer webServer = serverFactory.getWebServer(servletContext -> servletContext.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/*"));

				webServer.start();    //tomcat servlet container가 실행
			}
		};
		applicationContext.register(HellobootApplication.class);    //설정정보가 여기 있다
		applicationContext.refresh();    //bean object 생성, templateMethod

	}
}
