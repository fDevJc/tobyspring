package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
	@Test
	void configuration() throws Exception {
		MyConfig myConfig = new MyConfig();
		Bean1 bean1 = myConfig.bean1();
		Bean2 bean2 = myConfig.bean2();
		Assertions.assertThat(bean1.common).isNotSameAs(bean2.common);
	}

	@Test
	void springConfiguration() throws Exception {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(MyConfig.class);
		ac.refresh();

		Bean1 bean1 = ac.getBean(Bean1.class);
		Bean2 bean2 = ac.getBean(Bean2.class);

		Assertions.assertThat(bean1.common).isSameAs(bean2.common);

	}
	@Test
	void myConfiguration() throws Exception {
		MyConfig myConfig = new MyConfigProxy();
		Bean1 bean1 = myConfig.bean1();
		Bean2 bean2 = myConfig.bean2();

		Assertions.assertThat(bean1.common).isSameAs(bean2.common);
	}

	//스프링의 configuration은 proxy가 빈으로 등록된다 아래예시 처럼
	static class MyConfigProxy extends MyConfig {
		private Common common;

		@Override
		Common common() {
			if (this.common == null) {
				common = super.common();
			}
			return common;
		}
	}

	@Configuration
	// @Configuration(proxyBeanMethods = false) spring 5.2에서 추가 해당 설정을 하게되면 proxy를 만들지 않음
	static class MyConfig {
		@Bean
		Common common() {
			return new Common();
		}

		@Bean
		Bean1 bean1() {
			return new Bean1(common());
		}

		@Bean
		Bean2 bean2() {
			return new Bean2(common());
		}
	}

	static class Bean1 {
		private final Common common;

		Bean1(Common common) {
			this.common = common;
		}
	}

	static class Bean2 {
		private final Common common;

		Bean2(Common common) {
			this.common = common;
		}
	}

	static class Common {
	}
}
