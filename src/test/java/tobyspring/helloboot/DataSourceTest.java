package tobyspring.helloboot;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

// @SpringBootTest : @SpringBootApplication 에 영향을 받네
// @ExtendWith(SpringExtension.class)	// spring context를 이용한 테스트가 가능
// @ContextConfiguration(classes = HellobootApplication.class)	//빈들을 로딩할 정보, 시작점
// @TestPropertySource("classpath:/application.properties")	//application.properties 를 로딩하는 건 스프링부트 그러므로 설정이 필요
// @Transactional
@HelloBootTest
public class DataSourceTest {
	@Autowired
	DataSource dataSource;

	@Test
	void connect() throws Exception {
		Connection connection = dataSource.getConnection();
		connection.close();
	}
}
