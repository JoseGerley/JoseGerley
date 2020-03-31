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

import java.math.BigDecimal;

import org.hibernate.cfg.annotations.SetBinder;
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
import com.example.demo.service.TsscStoryService;
import com.example.demo.service.TsscStoryService;
import com.example.demo.service.TsscTopicService;
import com.example.demo.model.TsscStory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TsscStoryTest {

	@Autowired
	TsscStoryService  service;
	@Autowired
	TsscGameService gameService;
	TsscStory tsscStory_testObject_1, tsscStory_testObject_2, tsscStory_testObject_3, tsscStory_testObject_4, tsscStory_testObject_5, tsscStory_testObject_6;
	
	@BeforeEach
	void setUp()
	{
		
		tsscStory_testObject_1 = new TsscStory();
		tsscStory_testObject_1.setBusinessValue(BigDecimal.valueOf(45L));
		tsscStory_testObject_1.setInitialSprint(BigDecimal.valueOf(44L));
		tsscStory_testObject_1.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_1.setDescription("Mi juego 1");
		
		tsscStory_testObject_2 = new TsscStory();
		tsscStory_testObject_2.setBusinessValue(BigDecimal.valueOf(22));
		tsscStory_testObject_2.setInitialSprint(BigDecimal.valueOf(11));
		tsscStory_testObject_2.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_2.setDescription("Mi juego 2");
		
		tsscStory_testObject_3 = new TsscStory();
		tsscStory_testObject_3.setBusinessValue(BigDecimal.valueOf(5));
		tsscStory_testObject_3.setInitialSprint(BigDecimal.valueOf(0));
		tsscStory_testObject_3.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_3.setDescription("Mi juego 3");
		
		tsscStory_testObject_4 = new TsscStory();
		tsscStory_testObject_4.setBusinessValue(BigDecimal.valueOf(0));
		tsscStory_testObject_4.setInitialSprint(BigDecimal.valueOf(25));
		tsscStory_testObject_4.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_4.setDescription("Mi juego 4");
		
		tsscStory_testObject_5 = new TsscStory();
		tsscStory_testObject_5.setBusinessValue(BigDecimal.valueOf(0));
		tsscStory_testObject_5.setInitialSprint(BigDecimal.valueOf(100));
		tsscStory_testObject_5.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_5.setDescription("Mi juego 5");
	
		tsscStory_testObject_6 = new TsscStory();
		tsscStory_testObject_6.setBusinessValue(BigDecimal.valueOf(50));
		tsscStory_testObject_6.setInitialSprint(BigDecimal.valueOf(550));
		tsscStory_testObject_6.setPriority(BigDecimal.valueOf(43L));
		tsscStory_testObject_6.setDescription("Mi juego 6");
	}
	
	@AfterEach
	void destroy()
	{
		service.deleteAll();
	}
	
	@Nested
	@DisplayName("Story: Save Integration Test")
	class SaveTest{
		TsscGame game;
		@BeforeEach
		void setUp() {
			gameService.deleteAll();
			game = new TsscGame();
			game.setNGroups(5);
			game.setNSprints(5);
			game.setName("Mi Game");
			gameService.save(game);
		}
		@Test
		@DisplayName("Save a valid story test")
		void saveValidGame() {
			TsscStory aux;
			tsscStory_testObject_1.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			aux = service.findById(tsscStory_testObject_1.getId()).get();
			assertNotNull(aux);
			assertEquals(tsscStory_testObject_1.getDescription(), aux.getDescription());
		}
		
		
		@Test
		@DisplayName("Save multiple valid storys test")
		void saveMultipleValidGames()
		{
			tsscStory_testObject_1.setTsscGame(game);
			tsscStory_testObject_2.setTsscGame(game);
			tsscStory_testObject_6.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			service.save(tsscStory_testObject_2, game.getId());
			service.save(tsscStory_testObject_6, game.getId());
			TsscStory aux1,aux2,aux3;
			aux1 = service.findById(tsscStory_testObject_1.getId()).get();
			aux2 = service.findById(tsscStory_testObject_2.getId()).get();
			aux3 = service.findById(tsscStory_testObject_6.getId()).get();
			assertAll(
					() -> assertEquals(tsscStory_testObject_1.getDescription(), aux1.getDescription()),
					() -> assertEquals(tsscStory_testObject_2.getDescription(), aux2.getDescription()),
					() -> assertEquals(tsscStory_testObject_6.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_2.getDescription(), aux1.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_2.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_1.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_1.getDescription(), aux2.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_6.getDescription(), aux2.getDescription()),
					() -> assertNotEquals(tsscStory_testObject_6.getDescription(), aux1.getDescription()),
					() -> assertTrue(service.existById(tsscStory_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscStory_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscStory_testObject_6.getId()))
					    );
		}
		@Test
		@DisplayName("Save multiple invalid storys test")
		void saveMultipleInvalidGames()
		{
			tsscStory_testObject_3.setTsscGame(game);
			tsscStory_testObject_4.setTsscGame(game);
			tsscStory_testObject_5.setTsscGame(game);
			service.save(tsscStory_testObject_3, game.getId());
			service.save(tsscStory_testObject_4, game.getId());
			service.save(tsscStory_testObject_5, game.getId());
			assertAll(
					() -> assertFalse(service.findById(tsscStory_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscStory_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscStory_testObject_5.getId()).isPresent())

					
					    );
		}
		
		@Test
		@DisplayName("Save multiple invalid and valid storys test")
		void saveMultipleInvalidAndInvalidGames()
		{
			tsscStory_testObject_3.setTsscGame(game);
			tsscStory_testObject_4.setTsscGame(game);
			tsscStory_testObject_5.setTsscGame(game);
			tsscStory_testObject_1.setTsscGame(game);
			tsscStory_testObject_2.setTsscGame(game);
			tsscStory_testObject_6.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			service.save(tsscStory_testObject_2, game.getId());
			service.save(tsscStory_testObject_6, game.getId());
			service.save(tsscStory_testObject_3, game.getId());
			service.save(tsscStory_testObject_4, game.getId());
			service.save(tsscStory_testObject_5, game.getId());
			assertAll(
					() -> assertFalse(service.findById(tsscStory_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscStory_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscStory_testObject_5.getId()).isPresent()),
					() -> assertEquals(tsscStory_testObject_1.getDescription(), service.findById(tsscStory_testObject_1.getId()).get().getDescription()),
					() -> assertEquals(tsscStory_testObject_2.getDescription(), service.findById(tsscStory_testObject_2.getId()).get().getDescription()),
					() -> assertEquals(tsscStory_testObject_6.getDescription(), service.findById(tsscStory_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_2.getDescription(), service.findById(tsscStory_testObject_1.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_2.getDescription(), service.findById(tsscStory_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_1.getDescription(), service.findById(tsscStory_testObject_2.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_1.getDescription(), service.findById(tsscStory_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_6.getDescription(), service.findById(tsscStory_testObject_1.getId()).get().getDescription()),
					() -> assertNotEquals(tsscStory_testObject_6.getDescription(), service.findById(tsscStory_testObject_2.getId()).get().getDescription()),
					() -> assertTrue(service.existById(tsscStory_testObject_1.getId())),
					() -> assertTrue(service.existById(tsscStory_testObject_2.getId())),
					() -> assertTrue(service.existById(tsscStory_testObject_6.getId()))
					
					    );
		}
	}
	
	@Nested
	@DisplayName("Game: Update Integration Test")
	class UpdateTest{
		
		TsscGame game;
		@BeforeEach
		void setUp() {
			gameService.deleteAll();
			game = new TsscGame();
			game.setNGroups(5);
			game.setNSprints(5);
			game.setName("Mi Game");
			gameService.save(game);
		}
		
		@Test
		@DisplayName("edit normal test")
		void editNormalGame() {
			tsscStory_testObject_1.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscStory_testObject_1.getId()).get().getBusinessValue().longValue(), 45),
					() -> assertEquals(service.findById(tsscStory_testObject_1.getId()).get().getInitialSprint().longValue(), 44)
					);
			tsscStory_testObject_1.setBusinessValue(BigDecimal.valueOf(5));
			tsscStory_testObject_1.setInitialSprint(BigDecimal.valueOf(6));
			service.save(tsscStory_testObject_1, game.getId());
			assertAll(
					() -> assertEquals(service.findById(tsscStory_testObject_1.getId()).get().getBusinessValue().longValue(), 5),
					() -> assertEquals(service.findById(tsscStory_testObject_1.getId()).get().getInitialSprint().longValue(), 6)
					);
		}
		
		@Test
		@DisplayName("edit a valid story test")
		void editValidGame() {
			tsscStory_testObject_1.setTsscGame(game);
			tsscStory_testObject_2.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			long id = tsscStory_testObject_2.getId(); 
			tsscStory_testObject_2.setId(tsscStory_testObject_1.getId());
			service.save(tsscStory_testObject_2, game.getId());
			assertAll(() -> assertEquals(tsscStory_testObject_2.getDescription(), service.findById(tsscStory_testObject_1.getId()).get().getDescription()),
					  () -> assertNotEquals(tsscStory_testObject_1.getBusinessValue().longValue(), service.findById(tsscStory_testObject_1.getId()).get().getBusinessValue().longValue()),
					  () -> assertNotEquals(tsscStory_testObject_1.getInitialSprint().longValue(), service.findById(tsscStory_testObject_1.getId()).get().getInitialSprint().longValue()),
					  () -> assertEquals(tsscStory_testObject_2.getBusinessValue().longValue(), service.findById(tsscStory_testObject_1.getId()).get().getBusinessValue().longValue()),
					  () -> assertEquals(tsscStory_testObject_2.getInitialSprint().longValue(), service.findById(tsscStory_testObject_1.getId()).get().getInitialSprint().longValue())
					    );
		}
		
		@Test
		@DisplayName("edit multiple stories test")
		void editMultipleGames() {
			tsscStory_testObject_1.setTsscGame(game);
			tsscStory_testObject_2.setTsscGame(game);
			tsscStory_testObject_6.setTsscGame(game);
			service.save(tsscStory_testObject_1, game.getId());
			service.save(tsscStory_testObject_2, game.getId());
			service.save(tsscStory_testObject_6, game.getId());
			long id1,id2,id3;
			id1 = tsscStory_testObject_1.getId();
			id2 = tsscStory_testObject_2.getId();
			id3 = tsscStory_testObject_6.getId();
			tsscStory_testObject_2.setId(id1);
			tsscStory_testObject_6.setId(id2);
			tsscStory_testObject_1.setId(id3);
			service.save(tsscStory_testObject_1, game.getId());
			service.save(tsscStory_testObject_2, game.getId());
			service.save(tsscStory_testObject_6, game.getId());
			assertAll(
					() -> assertEquals(tsscStory_testObject_1.getDescription(), service.findById(id3).get().getDescription()),
					() -> assertEquals(tsscStory_testObject_1.getBusinessValue().longValue(), service.findById(id3).get().getBusinessValue().longValue()),
					() -> assertEquals(tsscStory_testObject_1.getInitialSprint().longValue(), service.findById(id3).get().getInitialSprint().longValue()),
					() -> assertEquals(tsscStory_testObject_2.getDescription(), service.findById(id1).get().getDescription()),
					() -> assertEquals(tsscStory_testObject_2.getBusinessValue().longValue(), service.findById(id1).get().getBusinessValue().longValue()),
					() -> assertEquals(tsscStory_testObject_2.getInitialSprint().longValue(), service.findById(id1).get().getInitialSprint().longValue()),
					() -> assertEquals(tsscStory_testObject_6.getDescription(), service.findById(id2).get().getDescription()),
					() -> assertEquals(tsscStory_testObject_6.getBusinessValue().longValue(), service.findById(id2).get().getBusinessValue().longValue()),
					() -> assertEquals(tsscStory_testObject_6.getInitialSprint().longValue(), service.findById(id2).get().getInitialSprint().longValue()));
					

		}

	}
	
	
	
}
