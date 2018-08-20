package com.anynines.app;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.anynines.queue.Receiver;

@Profile("test")
@Configuration
public class TestConfiguration {
	@Bean
    @Primary
    public Receiver receiver() {
        return Mockito.spy(Receiver.class);
    }
}
