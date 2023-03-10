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

	private static HelloRepository helloRepositoryStub = new HelloRepository() {
		@Override
		public Hello findMember(String name) {
			return null;
		}

		@Override
		public void increaseCount(String name) {

		}
	};

	@FastUnitTest
	void hello() throws Exception {
		SimpleHelloService simpleHelloService = new SimpleHelloService(helloRepositoryStub);

		String ret = simpleHelloService.sayHello("Test");

		assertThat(ret).isEqualTo("Hello Test");
	}

	@Test
	void helloDecorator() throws Exception {
		HelloDecorator helloDecorator = new HelloDecorator(new HelloService() {
			@Override
			public String sayHello(String name) {
				return name;
			}

			@Override
			public int countOf(String name) {
				return 0;
			}
		});
		String ret = helloDecorator.sayHello("Test");
		Assertions.assertThat(ret).isEqualTo("*Test*");
	}
}