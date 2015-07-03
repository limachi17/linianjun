package com.moxian.quartz;

import static com.google.common.base.Charsets.UTF_8;
import static org.springframework.boot.context.embedded.MimeMappings.DEFAULT;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import com.google.common.io.CharStreams;
import com.moxian.quartz.exercise.CronTriggerExample;
import com.moxian.quartz.validation.SensitiveWordsInitializer;

@Slf4j
@Configuration
@Import({ BeanConfig.class, SecurityConfig.class, QuartzConfig.class,
		ScheduledProcessor.class })
// @EnableJpaRepositories(basePackages = {"com.moxian.module1.dao"})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class,
		ManagementSecurityAutoConfiguration.class })
public class ApplicationInitializer extends SpringBootServletInitializer
		implements EmbeddedServletContainerCustomizer {

	/*
	 * @Bean public InitializingBean populateUser(final UserRepository
	 * repository) { return () -> { if (repository.count() == 0) {
	 * repository.save(User.builder() .name("foo") .password("foo") .build()); }
	 * 
	 * }; }
	 */

	@Bean
	public CronTriggerExample cronTriggerExample() {
		return new CronTriggerExample();
	}
	
	

	@Bean
	public InitializingBean populateUser(CronTriggerExample cronTriggerExample) {
		return () -> {
			log.info("cronTriggerExample is " + cronTriggerExample);
			cronTriggerExample.run();
		};
	}

	@Bean
	public InitializingBean sensetiveWordsInitialize(
			SensitiveWordsInitializer sensetiveWordsInitializer) {
		return () -> {
			SensitiveWordsConfig.sensetiveWords = sensetiveWordsInitializer
					.getAllSensetiveWords();
		};
	}

	/*
	 * @Bean public DisposableBean sensetiveWordsShutDown() { return () ->{ //
	 * thriftServerProxy.shutdown(); }; }
	 */

	@Override
	protected SpringApplicationBuilder configure(
			final SpringApplicationBuilder application) {
		return application.sources(ApplicationInitializer.class);
	}

	@Override
	public void customize(final ConfigurableEmbeddedServletContainer container) {
		final MimeMappings mappings = new MimeMappings(DEFAULT);
		mappings.add("html", MediaType.TEXT_HTML + ";charset=utf-8");
		mappings.add("woff", "application/font-woff;charset=utf-8");
		container.setMimeMappings(mappings);
	}

	public static void main(final String[] args) {
		SpringApplication.run(ApplicationInitializer.class, args);
		/*
		 * CronTriggerExample ce = new CronTriggerExample(); try { ce.run(); }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		log.info("Running application version {}", version());
	}

	public static String version() {
		try (final InputStream in = new ClassPathResource("version.txt")
				.getInputStream();
				final InputStreamReader readable = new InputStreamReader(in,
						UTF_8.name())) {
			return CharStreams.toString(readable).trim();
		} catch (final IOException e) {
			log.warn("Couldn't load application version from file");
			return "1.0.0";
		}
	}
}
