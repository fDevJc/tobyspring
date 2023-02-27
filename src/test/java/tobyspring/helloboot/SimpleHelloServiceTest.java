package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleHelloServiceTest {
	@Test
	void hello() throws Exception {
		SimpleHelloService simpleHelloService = new SimpleHelloService();
		String ret = simpleHelloService.sayHello("Test");

		assertThat(ret).isEqualTo("Hello Test");
	}

	@Test
	void helloDecorator() throws Exception {
		HelloDecorator helloDecorator = new HelloDecorator(name -> name);
		String ret = helloDecorator.sayHello("Test");
		Assertions.assertThat(ret).isEqualTo("*Test*");
	}
}