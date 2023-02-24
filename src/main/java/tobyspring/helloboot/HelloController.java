package tobyspring.helloboot;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
	Contoller의 역할은 검증
 */
@RequestMapping
public class HelloController {
	private final HelloService helloService;

	public HelloController(HelloService helloService) {
		this.helloService = helloService;
	}

	//
	@GetMapping("/hello")
	@ResponseBody
	public String hello(String name) {
		return helloService.sayHello(Objects.requireNonNull(name));
	}
}
