package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HelloBootTest
public class JdbcTemplateTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void init() {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS member(name varchar(50) primary key, count int)");
	}

	@Test
	void insertAndQuery() throws Exception {
	    //given
		jdbcTemplate.update("INSERT INTO member VALUES(?,?)", "Yang", 3);
		jdbcTemplate.update("INSERT INTO member VALUES(?,?)", "Spring", 1);

	    //when
		Long ret = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Long.class);

		//then
		Assertions.assertThat(ret).isEqualTo(2);
	}
}
