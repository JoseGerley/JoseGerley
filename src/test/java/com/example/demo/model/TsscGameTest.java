package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.service.TsscGameService;
import com.example.demo.service.TsscTopicService;
import com.example.demo.model.TsscGame;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TsscGameTest {

	@Autowired
	TsscGameService  service;
	@Autowired
	TsscTopicService topicService;
	TsscGame tsscGame_testObject_1, tsscGame_testObject_2, tsscGame_testObject_3, tsscGame_testObject_4, tsscGame_testObject_5, tsscGame_testObject_6;
	
	@BeforeEach
	void setUp()
	{
		
		tsscGame_testObject_1 = new TsscGame();
		tsscGame_testObject_1.setNGroups(45);
		tsscGame_testObject_1.setNSprints(44);
		tsscGame_testObject_1.setName("Mi juego 1");
		
		tsscGame_testObject_2 = new TsscGame();
		tsscGame_testObject_2.setNGroups(22);
		tsscGame_testObject_2.setNSprints(11);
		tsscGame_testObject_2.setName("Mi juego 2");
		
		tsscGame_testObject_3 = new TsscGame();
		tsscGame_testObject_3.setNGroups(5);
		tsscGame_testObject_3.setNSprints(0);
		tsscGame_testObject_3.setName("Mi juego 3");
		
		tsscGame_testObject_4 = new TsscGame();
		tsscGame_testObject_4.setNGroups(0);
		tsscGame_testObject_4.setNSprints(25);
		tsscGame_testObject_4.setName("Mi juego 4");
		
		tsscGame_testObject_5 = new TsscGame();
		tsscGame_testObject_5.setNGroups(0);
		tsscGame_testObject_5.setNSprints(0);
		tsscGame_testObject_5.setName("Mi juego 5");
	
		tsscGame_testObject_6 = new TsscGame();
		tsscGame_testObject_6.setNGroups(50);
		tsscGame_testObject_6.setNSprints(550);
		tsscGame_testObject_6.setName("Mi juego 6");
	}
	
	@AfterEach
	void destroy()
	{
		service.deleteAll();
	}
	
	@Nested
	@DisplayName("Game: Save Integration Test")
	class SaveTest{
		@Test
		@DisplayName("Save a valid topic test")
		void saveValidGame() {
			TsscGame aux;
			service.save(tsscGame_testObject_1);
			aux = service.findById(tsscGame_testObject_1.getId()).get();
			assertNotNull(aux);
			assertEquals(tsscGame_testObject_1.getName(), aux.getName());
		}
		
		@Test
		@DisplayName("Game is valid but doesent exists")
		void saveValidGameTopicNull() {
			//TsscGame aux;
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(5);
			topic.setDefaultSprints(5);
			tsscGame_testObject_1.setTsscTopic(topic);
			service.save(tsscGame_testObject_1, topic.getId());
			assertFalse(service.findById(tsscGame_testObject_1.getId()).isPresent());
			
		}
		
		@Test
		@DisplayName("Game is valid and exists")
		void saveValidGameTopicExists() {
			TsscGame aux;
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(5L);
			topic.setDefaultSprints(5L);
			tsscGame_testObject_1.setTsscTopic(topic);
			topicService.save(topic);
			service.save(tsscGame_testObject_1, topic.getId());
			aux = service.findById(tsscGame_testObject_1.getId()).get();
			assertEquals(tsscGame_testObject_1.getName(), aux.getName());
			assertEquals(tsscGame_testObject_1.getTsscTopic().getDefaultGroups(), 5L);
			assertEquals(tsscGame_testObject_1.getTsscTopic().getDefaultSprints(), 5L);
		}
		
		@Test
		@DisplayName("Save multiple valid topics test")
		void saveMultipleValidGames()
		{

			service.save(tsscGame_testObject_1);
			service.save(tsscGame_testObject_2);
			service.save(tsscGame_testObject_6);
			TsscGame aux1,aux2,aux3;
			aux1 = service.findById(tsscGame_testObject_1.getId()).get();
			aux2 = service.findById(tsscGame_testObject_2.getId()).get();
			aux3 = service.findById(tsscGame_testObject_6.getId()).get();
			assertAll(
					() -> assertEquals(tsscGame_testObject_1.getName(), aux1.getName()),
					() -> assertEquals(tsscGame_testObject_2.getName(), aux2.getName()),
					() -> assertEquals(tsscGame_testObject_6.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), aux1.getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), aux2.getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), aux2.getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), aux1.getName()),
					() -> assertTrue(service.existById(tsscGame_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_6.getId()))
					    );
		}
		@Test
		@DisplayName("Save multiple invalid topics test")
		void saveMultipleInvalidGames()
		{

			service.save(tsscGame_testObject_3);
			service.save(tsscGame_testObject_4);
			service.save(tsscGame_testObject_5);
			assertAll(
					() -> assertFalse(service.findById(tsscGame_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_5.getId()).isPresent())

					
					    );
		}
		
		@Test
		@DisplayName("Save multiple invalid and valid topics test")
		void saveMultipleInvalidAndInvalidGames()
		{
			service.save(tsscGame_testObject_1);
			service.save(tsscGame_testObject_2);
			service.save(tsscGame_testObject_6);
			service.save(tsscGame_testObject_3);
			service.save(tsscGame_testObject_4);
			service.save(tsscGame_testObject_5);
			assertAll(
					() -> assertFalse(service.findById(tsscGame_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_5.getId()).isPresent()),
					() -> assertEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertTrue(service.existById(tsscGame_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_6.getId()))
					
					    );
		}
	}
	
	@Nested
	@DisplayName("Game: Update Integration Test")
	class UpdateTest{
		
		@Test
		@DisplayName("edit normal test")
		void editNormalGame() {
			service.save(tsscGame_testObject_1);
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 45),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 44)
					);
			tsscGame_testObject_1.setNGroups(5);
			tsscGame_testObject_1.setNSprints(6);
			service.save(tsscGame_testObject_1);
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 5),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 6)
					);
		}
		
		@Test
		@DisplayName("edit topic invalid topic test")
		void editNormalGameInvalidTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(5L);
			topic.setDefaultSprints(5L);
			tsscGame_testObject_1.setTsscTopic(topic);
			topicService.save(topic);
			service.save(tsscGame_testObject_1, topic.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 45),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 44),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultGroups(),5L),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultSprints(),5L)
					);
			tsscGame_testObject_1.setNGroups(5);
			tsscGame_testObject_1.setNSprints(6);
			TsscTopic topic2 = new TsscTopic();
			topic2.setDefaultGroups(0);
			topic2.setDefaultSprints(0);
			tsscGame_testObject_1.setTsscTopic(topic2);
			topicService.save(topic2);
			service.save(tsscGame_testObject_1, topic2.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 45),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 44),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultGroups(),5L),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultSprints(),5L)
					);
		}
		
		@Test
		@DisplayName("edit topic valid topic test")
		void editNormalGameValidTopic() {
			TsscTopic topic = new TsscTopic();
			topic.setDefaultGroups(5L);
			topic.setDefaultSprints(5L);
			tsscGame_testObject_1.setTsscTopic(topic);
			topicService.save(topic);
			service.save(tsscGame_testObject_1, topic.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 45),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 44),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultGroups(),5L),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultSprints(),5L)
					);
			tsscGame_testObject_1.setNGroups(5);
			tsscGame_testObject_1.setNSprints(6);
			TsscTopic topic2 = new TsscTopic();
			topic2.setDefaultGroups(7L);
			topic2.setDefaultSprints(7L);
			tsscGame_testObject_1.setTsscTopic(topic2);
			topicService.save(topic2);
			service.save(tsscGame_testObject_1, topic2.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNGroups(), 5),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getNSprints(), 6),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultGroups(),7L),
					() -> assertEquals(service.findById(tsscGame_testObject_1.getId()).get().getTsscTopic().getDefaultSprints(),7L)
					);
		}
		
		@Test
		@DisplayName("edit a valid topic test")
		void editValidGame() {
			service.save(tsscGame_testObject_1);
			long id = tsscGame_testObject_2.getId(); 
			tsscGame_testObject_2.setId(tsscGame_testObject_1.getId());
			service.save(tsscGame_testObject_2);
			assertAll(() -> assertEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					  () -> assertNotEquals(tsscGame_testObject_1.getNGroups(), service.findById(tsscGame_testObject_1.getId()).get().getNGroups()),
					  () -> assertNotEquals(tsscGame_testObject_1.getNSprints(), service.findById(tsscGame_testObject_1.getId()).get().getNSprints()),
					  () -> assertEquals(tsscGame_testObject_2.getNGroups(), service.findById(tsscGame_testObject_1.getId()).get().getNGroups()),
					  () -> assertEquals(tsscGame_testObject_2.getNSprints(), service.findById(tsscGame_testObject_1.getId()).get().getNSprints())
					    );
		}
		
		@Test
		@DisplayName("edit multiple topics test")
		void editMultipleGames() {
			service.save(tsscGame_testObject_1);
			service.save(tsscGame_testObject_2);
			service.save(tsscGame_testObject_6);
			long id1,id2,id3;
			id1 = tsscGame_testObject_1.getId();
			id2 = tsscGame_testObject_2.getId();
			id3 = tsscGame_testObject_6.getId();
			tsscGame_testObject_2.setId(id1);
			tsscGame_testObject_6.setId(id2);
			tsscGame_testObject_1.setId(id3);
			service.save(tsscGame_testObject_1);
			service.save(tsscGame_testObject_2);
			service.save(tsscGame_testObject_6);
			assertAll(
					() -> assertEquals(tsscGame_testObject_1.getName(), service.findById(id3).get().getName()),
					() -> assertEquals(tsscGame_testObject_1.getNGroups(), service.findById(id3).get().getNGroups()),
					() -> assertEquals(tsscGame_testObject_1.getNSprints(), service.findById(id3).get().getNSprints()),
					() -> assertEquals(tsscGame_testObject_2.getName(), service.findById(id1).get().getName()),
					() -> assertEquals(tsscGame_testObject_2.getNGroups(), service.findById(id1).get().getNGroups()),
					() -> assertEquals(tsscGame_testObject_2.getNSprints(), service.findById(id1).get().getNSprints()),
					() -> assertEquals(tsscGame_testObject_6.getName(), service.findById(id2).get().getName()),
					() -> assertEquals(tsscGame_testObject_6.getNGroups(), service.findById(id2).get().getNGroups()),
					() -> assertEquals(tsscGame_testObject_6.getNSprints(), service.findById(id2).get().getNSprints()));
					

		}

	}
	
	@Nested
	@DisplayName("Game: Save2 Integration Test")
	class Save2Test{
		TsscTopic topic;
		TsscTimecontrol time;
		TsscStory story;
		@BeforeEach
		void setUp() {
			topic = new TsscTopic();
			topic.setDefaultGroups(5);
			topic.setDefaultSprints(5);
			story = new TsscStory();
			time = new TsscTimecontrol();
			story.setDescription("Mi historia");
			time.setName("Mi time");
			topic.addTsscStory(story);
			topic.addTsscTimecontrol(time);
			topicService.save(topic);
			tsscGame_testObject_1.setTsscTopic(topic);
			tsscGame_testObject_2.setTsscTopic(topic);
			tsscGame_testObject_3.setTsscTopic(topic);
			tsscGame_testObject_4.setTsscTopic(topic);
			tsscGame_testObject_5.setTsscTopic(topic);
			tsscGame_testObject_6.setTsscTopic(topic);
			
		}
		
		
		@Test
		@DisplayName("Save a valid topic test")
		void saveValidGame() {
			TsscGame aux;
			service.save2(tsscGame_testObject_1, topic.getId());
			aux = service.findById(tsscGame_testObject_1.getId()).get();
			assertNotNull(aux);
			assertEquals(tsscGame_testObject_1.getName(), aux.getName());
		}
		
		@Test
		@DisplayName("Save a valid topic test valid add list of topics")
		void saveValidGameList() {
			TsscGame aux;
			service.save2(tsscGame_testObject_1, topic.getId());
			aux = service.findById(tsscGame_testObject_1.getId()).get();
			assertNotNull(aux);
			assertEquals(tsscGame_testObject_1.getName(), aux.getName());
			assertTrue(tsscGame_testObject_1.getTsscTopic().getTsscStories().contains(story));
			assertTrue(tsscGame_testObject_1.getTsscTopic().getTsscTimecontrols().contains(time));
		}
		
		
		@Test
		@DisplayName("Save multiple valid topics test")
		void saveMultipleValidGames()
		{

			service.save2(tsscGame_testObject_1, topic.getId());
			service.save2(tsscGame_testObject_2, topic.getId());
			service.save2(tsscGame_testObject_6, topic.getId());
			TsscGame aux1,aux2,aux3;
			aux1 = service.findById(tsscGame_testObject_1.getId()).get();
			aux2 = service.findById(tsscGame_testObject_2.getId()).get();
			aux3 = service.findById(tsscGame_testObject_6.getId()).get();
			assertAll(
					() -> assertEquals(tsscGame_testObject_1.getName(), aux1.getName()),
					() -> assertEquals(tsscGame_testObject_2.getName(), aux2.getName()),
					() -> assertEquals(tsscGame_testObject_6.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), aux1.getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), aux3.getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), aux2.getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), aux2.getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), aux1.getName()),
					() -> assertTrue(service.existById(tsscGame_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_6.getId()))
					    );
		}
		@Test
		@DisplayName("Save multiple invalid topics test")
		void saveMultipleInvalidGames()
		{

			service.save2(tsscGame_testObject_3, topic.getId());
			service.save2(tsscGame_testObject_4, topic.getId());
			service.save2(tsscGame_testObject_5, topic.getId());
			assertAll(
					() -> assertFalse(service.findById(tsscGame_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_5.getId()).isPresent())

					
					    );
		}
		
		@Test
		@DisplayName("Save multiple invalid and valid topics test")
		void saveMultipleInvalidAndInvalidGames()
		{
			service.save2(tsscGame_testObject_1, topic.getId());
			service.save2(tsscGame_testObject_2, topic.getId());
			service.save2(tsscGame_testObject_6, topic.getId());
			service.save2(tsscGame_testObject_3, topic.getId());
			service.save2(tsscGame_testObject_4, topic.getId());
			service.save2(tsscGame_testObject_5, topic.getId());
			assertAll(
					() -> assertFalse(service.findById(tsscGame_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscGame_testObject_5.getId()).isPresent()),
					() -> assertEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_2.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_1.getName(), service.findById(tsscGame_testObject_6.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_1.getId()).get().getName()),
					() -> assertNotEquals(tsscGame_testObject_6.getName(), service.findById(tsscGame_testObject_2.getId()).get().getName()),
					() -> assertTrue(service.existById(tsscGame_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscGame_testObject_6.getId()))
					
					    );
		}
	}
	
	
}
