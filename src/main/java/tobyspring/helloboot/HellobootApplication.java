package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/*
			org.apache.catalina.startup.Tomcat;
			- 임베디드 톰캣을 제공
			- 여러 설정정보를 넣어야됨
			- 스프링부트에서 여러 설정정보를 기본적으로 세팅해주는 TomcatServletWebServerFactory가 존재
*/
public class HellobootApplication {

	public static void main(String[] args) {
		//Spring Container
		//기존 GenericWebApplicationContext을 확장하여 onRefresh 메소드 오버라이딩
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){
			@Override
			protected void onRefresh() {
				super.onRefresh();
				//추상화
				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				//ServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();

				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					//servlet container에 servlet을 등록, 매핑
					//서블릿을 프론트컨트롤러 하나만 두고 공통처리인 인증,보안,다국어 처리등을 담당
					servletContext.addServlet("dispatcherServlet",
						new DispatcherServlet(this)	//WebApplicationContext를 사용
					).addMapping("/*");
				});
				webServer.start();    //tomcat servlet container가 실행
			}
		};
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();    //bean object 생성, templateMethod


	}
}
