package tobyspring.helloboot;

import java.util.Objects;

/*
	Contoller의 역할은 검증
 */
public class HelloController {
	public String hello(String name) {
		SimpleHelloService helloService = new SimpleHelloService();

		return helloService.sayHello(Objects.requireNonNull(name));
	}
}
