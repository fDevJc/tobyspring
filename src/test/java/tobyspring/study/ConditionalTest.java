package tobyspring.study;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalTest {
	@Test
	void conditional() throws Exception {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(Config1.class);
		ac.refresh();

		MyBean bean = ac.getBean(MyBean.class);

		AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
		ac2.register(Config2.class);	//@BooleanConditional(false)
		ac2.refresh();

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
			() -> ac2.getBean(MyBean.class));
	}

	@Test
	void conditionalTest() throws Exception {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner();
		contextRunner.withUserConfiguration(Config1.class)
			.run(context -> {
				Assertions.assertThat(context).hasSingleBean(MyBean.class);
				Assertions.assertThat(context).hasSingleBean(Config1.class);
			});
		new ApplicationContextRunner().withUserConfiguration(Config2.class)
			.run(context -> {
				Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
			});
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Conditional(BooleanCondition.class)
	@interface BooleanConditional {
		boolean value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Conditional(TrueCondition.class)
	@interface TrueConditional {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Conditional(FalseCondition.class)
	@interface FalseConditional {
	}

	static class MyBean {
	}

	@Configuration
	@BooleanConditional(true)
	static class Config1 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}

	}

	@Configuration
	@BooleanConditional(false)
	static class Config2 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}
	}

	static class TrueCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return true;
		}
	}

	static class FalseCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return false;
		}
	}

	static class BooleanCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
			Boolean value = (Boolean)annotationAttributes.get("value");
			return value;
		}
	}
}
