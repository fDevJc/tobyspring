package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloControllerTest {
	@Test
	void helloController() throws Exception {
		HelloController helloController = new HelloController(name -> name);

		String ret = helloController.hello("Test");

		Assertions.assertThat(ret).isEqualTo("Test");
	}

	@Test
	void failsHelloController() throws Exception {
		HelloController helloController = new HelloController(name -> name);

		Assertions.assertThatThrownBy(() -> helloController.hello(null))
			.isInstanceOf(IllegalArgumentException.class);

		Assertions.assertThatThrownBy(() -> helloController.hello(""))
			.isInstanceOf(IllegalArgumentException.class);
	}
}