package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Test
@interface UnitTest {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@UnitTest
@interface FastUnitTest {

}

class SimpleHelloServiceTest {
	@FastUnitTest
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