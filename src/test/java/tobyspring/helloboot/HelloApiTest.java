package tobyspring.helloboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HelloApiTest {
	private static final String LOCAL_HOST = "http://localhost:";
	private static final String CONTEXT_PATH = "/app";
	private static final String PORT = "9090";
	private static final String URI = LOCAL_HOST + PORT + CONTEXT_PATH;

	@Test
	void helloApi() {
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<String> res = rest.getForEntity(URI + "/hello?name={name}", String.class, "Spring");

		//status code 200
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		//header content-type text/plain
		assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
		//body Hello Spring
		assertThat(res.getBody()).isEqualTo("*Hello Spring*");
	}

	@Test
	void failsHelloApi() {
		TestRestTemplate rest = new TestRestTemplate();

		ResponseEntity<String> res = rest.getForEntity(URI + "/hello?name={name}", String.class, "");

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
