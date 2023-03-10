package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@HelloBootTest
public class HelloServiceCountTest {
	@Autowired
	HelloService helloService;

	@Autowired
	HelloRepository helloRepository;

	@Test
	void sayHelloIncreaseCount() throws Exception {
		IntStream.rangeClosed(1, 10).forEach(count -> {
			helloService.sayHello("Yang");
			assertThat(helloRepository.countOf("Yang")).isEqualTo(count);
		});
	}
}
