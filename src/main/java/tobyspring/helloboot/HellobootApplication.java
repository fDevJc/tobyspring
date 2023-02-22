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
import org.springframework.http.HttpMethod;
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
			HelloController helloController = new HelloController();

			//servlet container에 servlet을 등록, 매핑
			//서블릿을 프론트컨트롤러 하나만 두고 공통처리인 인증,보안,다국어 처리등을 담당
			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					//공통처리 로직
					//...
					//공통처리 로직

					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {	//Mapping
						String name = req.getParameter("name");	//파라미터 추출, 바인딩

						String ret = helloController.hello(name);

						//응답
						resp.setStatus(HttpStatus.OK.value());
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret);
					} else if (req.getRequestURI().equals("/user")) {
						//
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				}
			}).addMapping("/*");
		});

		webServer.start();	//tomcat servlet container가 실행


	}

}
