package com.anynines.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anynines.queue.Receiver;
import com.anynines.queue.Sender;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ApplicationTests {

	final String TEST_MESSAGE = "test message";

	@Autowired
	private Sender sender;
	
	@Autowired
	private Receiver receiver;
	
	@Test
	public void sendingMessageWillBeReceived() throws Exception {
		sender.send(TEST_MESSAGE);
		Mockito.verify(receiver, Mockito.times(1)).receiveMessage(TEST_MESSAGE);
	}

}
