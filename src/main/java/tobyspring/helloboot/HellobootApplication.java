package tobyspring.helloboot;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class HellobootApplication {

	public static void main(String[] args) {
		/*
			org.apache.catalina.startup.Tomcat;
			- 임베디드 톰캣을 제공
			- 여러 설정정보를 넣어야됨
			- 스프링부트에서 여러 설정정보를 기본적으로 세팅해주는 TomcatServletWebServerFactory가 존재
		 */

		//추상화
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		//ServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();

		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			//servlet container에 servlet을 등록, 매핑
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					String name = req.getParameter("name");

					resp.setStatus(HttpStatus.OK.value());
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
					resp.getWriter().println("Hello Servlet" + name);

				}
			}).addMapping("/hello");
		});

		webServer.start();	//tomcat servlet container가 실행


	}

}
