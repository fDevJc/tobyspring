package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloControllerTest {
	private static HelloService helloService = new HelloService() {
		@Override
		public String sayHello(String name) {
			return name;
		}

		@Override
		public int countOf(String name) {
			return 0;
		}
	};

	@Test
	void helloController() throws Exception {
		HelloController helloController = new HelloController(helloService);

		String ret = helloController.hello("Test");

		Assertions.assertThat(ret).isEqualTo("Test");
	}

	@Test
	void failsHelloController() throws Exception {
		HelloController helloController = new HelloController(helloService);

		Assertions.assertThatThrownBy(() -> helloController.hello(null))
			.isInstanceOf(IllegalArgumentException.class);

		Assertions.assertThatThrownBy(() -> helloController.hello(""))
			.isInstanceOf(IllegalArgumentException.class);
	}
}