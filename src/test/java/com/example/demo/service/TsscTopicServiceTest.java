package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.model.TsscTopic;
import com.example.demo.repository.TsscTopicRepository;

import lombok.extern.java.Log;

@Log
@RunWith(MockitoJUnitRunner.class)
class TsscTopicServiceTest {
	
	@Mock
	private TsscTopicRepository topicRepository;

	@InjectMocks
	private TsscTopicService topicService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		
	}

	@Nested
	@DisplayName("Test TsscTopicService Save")
	class GuardadoTest{
		
		@BeforeClass
		public void setUp(){
			log.info("Prueba save");
			topicRepository.deleteAll();
		}
		
		@DisplayName("Topic is null")
		@Test
		public void testTopicNull() {
			TsscTopic aux = null;
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		
		@DisplayName("Sprints is equal to 0")
		@Test
		public void testTopicSprintsEqualZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(1);
			aux.setDefaultSprints(0);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Sprints is negative")
		@Test
		public void testTopicSprintsBelowZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(1);
			aux.setDefaultSprints(-1);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Groups is equal to 0")
		@Test
		public void testTopicGroupsEqualZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(0);
			aux.setDefaultSprints(1);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Groups is negative")
		@Test
		public void testTopicGroupsBelowZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(-1);
			aux.setDefaultSprints(0);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Both are equal to 0")
		@Test
		public void testTopicBothEqualZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(0);
			aux.setDefaultSprints(0);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Both are negative")
		@Test
		public void testTopicBothBelowZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(-1);
			aux.setDefaultSprints(-1);
			assertFalse(topicService.save(aux));
			Mockito.verifyZeroInteractions(topicRepository);
		}
		
		@DisplayName("Both are positive")
		@Test
		public void testTopicBothAboveZero() {
			TsscTopic aux = new TsscTopic();
			aux.setDefaultGroups(1);
			aux.setDefaultSprints(1);
			assertTrue(topicService.save(aux));
		}
	}
	
	@Nested
	@DisplayName("Test TsscTopicService Save for Update")
	class ActualizarTest{
		
		TsscTopic topic;

		@BeforeEach
		public void setUp(){
			topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);

		}
		
		@DisplayName("Change for Sprint = 0")
		@Test
		public void testTopicSprintsChangeZero() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultSprints(0);
			assertFalse(topicService.save(aux));
		}
		
		@DisplayName("Change for negative Sprints")
		@Test
		public void testTopicSprintsChangeNegative() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultSprints(-1);
			assertFalse(topicService.save(aux));
		}
		
		@DisplayName("Change for Groups = 0")
		@Test
		public void testTopicGroupsChangeZero() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultGroups(0);
			assertFalse(topicService.save(aux));
		}
		
		@DisplayName("Change for negative Group")
		@Test
		public void testTopicGroupsChangeNegative() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultGroups(-1);
			assertFalse(topicService.save(aux));
		}
		
		@DisplayName("Valid Change Sprint")
		@Test
		public void testTopicSprintsChangeValid() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultSprints(2);
			assertTrue(topicService.save(aux));
		}
		
		@DisplayName("Valid Change Sprint")
		@Test
		public void testTopicGroupsChangeValid() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultGroups(2);
			assertTrue(topicService.save(aux));
		}
		
		@DisplayName("Valid Change Groups")
		@Test
		public void testTopicBothChangeValid() {
			assertTrue(topicService.save(topic));
			TsscTopic aux = topic;
			aux.setDefaultGroups(2);
			aux.setDefaultSprints(2);
			assertTrue(topicService.save(aux));
		}
	}

}
