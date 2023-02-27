package tobyspring.helloboot;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
	Contoller의 역할은 검증
 */
@RestController
public class HelloController {
	private final HelloService helloService;

	public HelloController(HelloService helloService) {
		this.helloService = helloService;
	}

	@GetMapping("/hello")
	public String hello(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException();
		}
		return helloService.sayHello(Objects.requireNonNull(name));
	}
}
