package tobyspring.helloboot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
/*
			org.apache.catalina.startup.Tomcat;
			- 임베디드 톰캣을 제공
			- 여러 설정정보를 넣어야됨
			- 스프링부트에서 여러 설정정보를 기본적으로 세팅해주는 TomcatServletWebServerFactory가 존재
*/
public class HellobootApplication {

	public static void main(String[] args) {
		//Spring Container
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();	//bean object 생성

		//추상화
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		//ServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();

		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			//servlet container에 servlet을 등록, 매핑
			//서블릿을 프론트컨트롤러 하나만 두고 공통처리인 인증,보안,다국어 처리등을 담당
			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					//공통처리 로직
					//...
					//공통처리 로직
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {    //Mapping
						String name = req.getParameter("name");    //파라미터 추출, 바인딩

						HelloController helloController = applicationContext.getBean(HelloController.class);
						String ret = helloController.hello(name);

						//응답
						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret);
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				}
			}).addMapping("/*");
		});
		webServer.start();    //tomcat servlet container가 실행
	}
}
