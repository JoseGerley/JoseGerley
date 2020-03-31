package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import lombok.extern.java.Log;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.TsscGame;
import com.example.demo.model.TsscStory;
import com.example.demo.model.TsscTimecontrol;
import com.example.demo.model.TsscTopic;
import com.example.demo.repository.TsscGameRepository;
import com.example.demo.repository.TsscTopicRepository;

@Log
@RunWith(MockitoJUnitRunner.class)
class TsscGameServiceTest {

	@Mock
	private TsscGameRepository gameRepository;
	
	@Mock
	private TsscTopicRepository topicRepository;
	
	@InjectMocks
	private TsscGameService gameService;
	
	

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		
		
	}

	@Nested
	@DisplayName("Test TsscGameService Save")
	class GuardadoTest{
		
		@BeforeClass
		public void setUp(){
			log.info("Prueba save");
			gameRepository.deleteAll();
		}
		
		@DisplayName("Game is null")
		@Test
		public void testGameNull() {
			TsscGame aux = null;
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		
		@DisplayName("Sprints is equal to 0")
		@Test
		public void testGameSprintsEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(0);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Sprints is negative")
		@Test
		public void testGameSprintsBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(-1);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Groups is equal to 0")
		@Test
		public void testGameGroupsEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(0);
			aux.setNSprints(1);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Groups is negative")
		@Test
		public void testGameGroupsBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(-1);
			aux.setNSprints(0);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are equal to 0")
		@Test
		public void testGameBothEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(0);
			aux.setNSprints(0);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are negative")
		@Test
		public void testGameBothBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(-1);
			aux.setNSprints(-1);
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are positive")
		@Test
		public void testGameBothAboveZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			assertTrue(gameService.save(aux));
		}
		
		@DisplayName("Topic is valid but doesent exists")
		@Test
		public void testGameWithNonExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			long id = 1;
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(false);
			when(topicRepository.findById(id)).thenReturn(null);
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertFalse(gameService.save(aux, id));
		}
		
		@DisplayName("Topis is valid and exists")
		@Test
		public void testGameWithExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			long id = 1;
			topic.setId(id);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertTrue(gameService.save(aux, id));
		}
		
		
	}
	
	
	@Nested
	@DisplayName("Test TsscGameService Save for Update")
	class ActualizarTest{
		
		TsscGame game;

		@BeforeEach
		public void setUp(){
			game = new TsscGame();
			game.setNGroups(1);
			game.setNSprints(1);

		}
		
		@DisplayName("Change for Sprint = 0")
		@Test
		public void testGameSprintsChangeZero() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNSprints(0);
			assertFalse(gameService.save(aux));
		}
		
		@DisplayName("Change for negative Sprints")
		@Test
		public void testGameSprintsChangeNegative() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNSprints(-1);
			assertFalse(gameService.save(aux));
		}
		
		@DisplayName("Change for Groups = 0")
		@Test
		public void testGameGroupsChangeZero() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNGroups(0);
			assertFalse(gameService.save(aux));
		}
		
		@DisplayName("Change for negative Group")
		@Test
		public void testGameGroupsChangeNegative() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNGroups(-1);
			assertFalse(gameService.save(aux));
		}
		
		@DisplayName("Valid Change Sprint")
		@Test
		public void testGameSprintsChangeValid() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNSprints(2);
			assertTrue(gameService.save(aux));
		}
		
		@DisplayName("Valid Change Sprint")
		@Test
		public void testGameGroupsChangeValid() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNGroups(2);
			assertTrue(gameService.save(aux));
		}
		
		@DisplayName("Valid Change Groups")
		@Test
		public void testGameBothChangeValid() {
			assertTrue(gameService.save(game));
			TsscGame aux = game;
			aux.setNGroups(2);
			aux.setNSprints(2);
			assertTrue(gameService.save(aux));
		}
		
		@DisplayName("Topic is change for invalid value")
		@Test
		public void testGameWithNonExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			long id = 1;
			topic.setId(id);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertTrue(gameService.save(aux, id));
			topic.setDefaultGroups(0);
			topic.setDefaultSprints(0);
			id = 5;
			topic.setId(id);
			topicRepository.save(topic);
			when(topicRepository.existsById(id)).thenReturn(false);
			when(topicRepository.findById(id)).thenReturn(null);
			aux.setTsscTopic(topic);
			assertFalse(gameService.save(aux, id));
			
		}
		
		@DisplayName("Topis is change for valid value")
		@Test
		public void testGameWithExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			long id = 1;
			topic.setId(id);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertTrue(gameService.save(aux, id));
			topic.setDefaultGroups(2);
			topic.setDefaultSprints(2);
			id = 5;
			topic.setId(id);
			topicRepository.save(topic);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			aux.setTsscTopic(topic);
			assertTrue(gameService.save(aux, id));
		}
		
	}


	@Nested
	@DisplayName("Test TsscGameService Save2")
	class GuardadoTest2{
		long id;
		@BeforeClass
		public void setUp(){
			log.info("Prueba save");
			gameRepository.deleteAll();
			id = 0;
		}
		
		@DisplayName("Game is null")
		@Test
		public void testGameNull() {
			TsscGame aux = null;
			assertFalse(gameService.save(aux));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		
		@DisplayName("Sprints is equal to 0")
		@Test
		public void testGameSprintsEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(0);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Sprints is negative")
		@Test
		public void testGameSprintsBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(-1);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Groups is equal to 0")
		@Test
		public void testGameGroupsEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(0);
			aux.setNSprints(1);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Groups is negative")
		@Test
		public void testGameGroupsBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(-1);
			aux.setNSprints(0);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are equal to 0")
		@Test
		public void testGameBothEqualZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(0);
			aux.setNSprints(0);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are negative")
		@Test
		public void testGameBothBelowZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(-1);
			aux.setNSprints(-1);
			assertFalse(gameService.save2(aux, id));
			Mockito.verifyZeroInteractions(gameRepository);
		}
		
		@DisplayName("Both are positive")
		@Test
		public void testGameBothAboveZero() {
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			assertTrue(gameService.save2(aux, id));
		}
		
		@DisplayName("Topic is valid but doesent exists")
		@Test
		public void testGameWithNonExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(false);
			when(topicRepository.findById(id)).thenReturn(null);
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertFalse(gameService.save(aux, id));
		}
		
		@DisplayName("Topis is valid and exists")
		@Test
		public void testGameWithExistsTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			topic.setId(id);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			assertTrue(gameService.save(aux, id));
		}
		
		@DisplayName("Check if the list TimeControl are the same")
		@Test
		public void testGameCheckTimeControl() {
			TsscTimecontrol timecontrolOne = new TsscTimecontrol();
			TsscTimecontrol timecontrolTwo = new TsscTimecontrol();
			TsscTimecontrol timecontrolThree = new TsscTimecontrol();
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			topic.addTsscTimecontrol(timecontrolOne);
			topic.addTsscTimecontrol(timecontrolTwo);
			topic.addTsscTimecontrol(timecontrolThree);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			List<TsscTimecontrol> listTimecontrol = aux.getTsscTopic().getTsscTimecontrols();
			aux.getTsscTimecontrols().addAll(listTimecontrol);
			assertTrue(gameService.save2(aux, id));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolOne));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolTwo));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolThree));
		}
		
		@DisplayName("Check if the list Stories are the same")
		@Test
		public void testGameCheckStoires() {
			TsscStory storyOne = new TsscStory();
			TsscStory storyTwo = new TsscStory();
			TsscStory storyThree = new TsscStory();
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			topic.addTsscStory(storyOne);
			topic.addTsscStory(storyTwo);
			topic.addTsscStory(storyThree);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			List<TsscStory> listStory = aux.getTsscTopic().getTsscStories();
			aux.getTsscStories().addAll(listStory);
			assertTrue(gameService.save2(aux, id));
			assertTrue(aux.getTsscStories().contains(storyOne));
			assertTrue(aux.getTsscStories().contains(storyTwo));
			assertTrue(aux.getTsscStories().contains(storyThree));
		}
		
		@DisplayName("Check if the list both are the same")
		@Test
		public void testGameCheckBoth() {
			TsscStory storyOne = new TsscStory();
			TsscStory storyTwo = new TsscStory();
			TsscStory storyThree = new TsscStory();
			TsscTimecontrol timecontrolOne = new TsscTimecontrol();
			TsscTimecontrol timecontrolTwo = new TsscTimecontrol();
			TsscTimecontrol timecontrolThree = new TsscTimecontrol();
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(1);
			topic.setDefaultSprints(1);
			topic.addTsscStory(storyOne);
			topic.addTsscStory(storyTwo);
			topic.addTsscStory(storyThree);
			topic.addTsscTimecontrol(timecontrolOne);
			topic.addTsscTimecontrol(timecontrolTwo);
			topic.addTsscTimecontrol(timecontrolThree);
			topicRepository.save(topic);
			topic.setId(id);
			when(topicRepository.existsById(id)).thenReturn(true);
			when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
			TsscGame aux = new TsscGame();
			aux.setNGroups(1);
			aux.setNSprints(1);
			aux.setTsscTopic(topic);
			List<TsscTimecontrol> listTimecontrol = aux.getTsscTopic().getTsscTimecontrols();
			aux.getTsscTimecontrols().addAll(listTimecontrol);
			List<TsscStory> listStory = aux.getTsscTopic().getTsscStories();
			aux.getTsscStories().addAll(listStory);
			assertTrue(gameService.save2(aux, id));
			assertTrue(aux.getTsscStories().contains(storyOne));
			assertTrue(aux.getTsscStories().contains(storyTwo));
			assertTrue(aux.getTsscStories().contains(storyThree));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolOne));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolTwo));
			assertTrue(aux.getTsscTimecontrols().contains(timecontrolThree));
		}
	}
	
	
}
