package com.moxian.common;

import static com.google.common.base.Charsets.UTF_8;
import static org.springframework.boot.context.embedded.MimeMappings.DEFAULT;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.DisposableBean;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.io.CharStreams;
import com.moxian.common.proxy.ThriftServerProxy;


@Slf4j
@Configuration
@Import({BeanConfig.class, SecurityConfig.class})
@EnableJpaRepositories(basePackages = {"com.moxian.deal.dao"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, ManagementSecurityAutoConfiguration.class})
public class ApplicationInitializer extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

	
//    @Bean
//    public InitializingBean populateUser(final UserRepository repository) {
//        return () -> {
//            if (repository.count() == 0) {
//           /*     repository.save(User.builder()
//                        .name("foo")
//                        .password("foo")
//                        .build());*/
//            }
//
//        };
//    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(ApplicationInitializer.class);
    }
    
    /* add thrift begin*/
    
    @Bean
    public InitializingBean thriftServer(ThriftServerProxy thriftServerProxy) {
        return () ->{
            thriftServerProxy.start();
        };
	}


    @Bean
    public DisposableBean shutDownThriftServer(ThriftServerProxy thriftServerProxy) {
        return () ->{
            thriftServerProxy.shutdown();
        };
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
        log.info("Running application version {}", version());
    }

    public static String version() {
        try (final InputStream in = new ClassPathResource("version.txt").getInputStream();
             final InputStreamReader readable = new InputStreamReader(in, UTF_8.name())) {
            return CharStreams.toString(readable).trim();
        } catch (final IOException e) {
            log.warn("Couldn't load application version from file");
            return "1.0.0";
        }
    }
}
