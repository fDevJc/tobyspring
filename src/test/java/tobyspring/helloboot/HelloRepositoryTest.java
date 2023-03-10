package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HelloBootTest
public class HelloRepositoryTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	HelloRepository helloRepository;

	@Test
	void findHelloFailed() throws Exception {
		Hello ret = helloRepository.findMember("yang");
		assertThat(ret).isNull();
	}

	@Test
	void increaseCount() throws Exception {
		assertThat(helloRepository.countOf("Yang")).isEqualTo(0);

		helloRepository.increaseCount("Yang");
		assertThat(helloRepository.countOf("Yang")).isEqualTo(1);

		helloRepository.increaseCount("Yang");
		assertThat(helloRepository.countOf("Yang")).isEqualTo(2);
	}
}
