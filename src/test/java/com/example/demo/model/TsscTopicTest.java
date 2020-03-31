package com.example.demo.model;


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

import com.example.demo.service.TsscTopicService;
import com.example.demo.model.TsscTopic;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TsscTopicTest {

	@Autowired
	TsscTopicService  service;
	TsscTopic tsscTopic_testObject_1, tsscTopic_testObject_2, tsscTopic_testObject_3, tsscTopic_testObject_4, tsscTopic_testObject_5, tsscTopic_testObject_6;
	
	@BeforeEach
	void setUp()
	{
		
		tsscTopic_testObject_1 = new TsscTopic();
		tsscTopic_testObject_1.setDefaultGroups(45);
		tsscTopic_testObject_1.setDefaultSprints(44);
		tsscTopic_testObject_1.setDescription("Soy el objeto de prueba numero 1");
		
		
		tsscTopic_testObject_2 = new TsscTopic();
		tsscTopic_testObject_2.setDefaultGroups(22);
		tsscTopic_testObject_2.setDefaultSprints(11);
		tsscTopic_testObject_2.setDescription("Soy el objeto de prueba numero 2");
		
		tsscTopic_testObject_3 = new TsscTopic();
		tsscTopic_testObject_3.setDefaultGroups(5);
		tsscTopic_testObject_3.setDefaultSprints(0);
		tsscTopic_testObject_3.setDescription("Soy el objeto de prueba numero 3");
		
		tsscTopic_testObject_4 = new TsscTopic();
		tsscTopic_testObject_4.setDefaultGroups(0);
		tsscTopic_testObject_4.setDefaultSprints(25);
		tsscTopic_testObject_4.setDescription("Soy el objeto de prueba numero 4");
		
		tsscTopic_testObject_5 = new TsscTopic();
		tsscTopic_testObject_5.setDefaultGroups(0);
		tsscTopic_testObject_5.setDefaultSprints(0);
		tsscTopic_testObject_5.setDescription("Soy el objeto de prueba numero 5");
	
		tsscTopic_testObject_6 = new TsscTopic();
		tsscTopic_testObject_6.setDefaultGroups(50);
		tsscTopic_testObject_6.setDefaultSprints(550);
		tsscTopic_testObject_6.setDescription("Soy el objeto de prueba numero 6");
	}
	
	@AfterEach
	void destroy()
	{
		service.deleteAll();
	}
	
	@Nested
	@DisplayName("Topic: Save Integration Test")
	class SaveTest{
		@Test
		@DisplayName("Save a valid topic in the database")
		void saveValidTopic() {
			TsscTopic aux;
			service.save(tsscTopic_testObject_1);
			aux = service.findById(tsscTopic_testObject_1.getId()).get();
			assertNotNull(aux);
			assertEquals(tsscTopic_testObject_1.getDescription(), aux.getDescription());
		}
		@Test
		@DisplayName("Save multiple valid topics test")
		void saveMultipleValidTopics()
		{

			service.save(tsscTopic_testObject_1);
			service.save(tsscTopic_testObject_2);
			service.save(tsscTopic_testObject_6);
			TsscTopic aux1,aux2,aux3;
			aux1 = service.findById(tsscTopic_testObject_1.getId()).get();
			aux2 = service.findById(tsscTopic_testObject_2.getId()).get();
			aux3 = service.findById(tsscTopic_testObject_6.getId()).get();
			assertAll(
					() -> assertEquals(tsscTopic_testObject_1.getDescription(), aux1.getDescription()),
					() -> assertEquals(tsscTopic_testObject_2.getDescription(), aux2.getDescription()),
					() -> assertEquals(tsscTopic_testObject_6.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_2.getDescription(), aux1.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_2.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_1.getDescription(), aux3.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_1.getDescription(), aux2.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_6.getDescription(), aux2.getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_6.getDescription(), aux1.getDescription()),
					() -> assertTrue(service.existByID(tsscTopic_testObject_1.getId())),
					() -> assertTrue(service.existByID(tsscTopic_testObject_2.getId())),
					() -> assertTrue(service.existByID(tsscTopic_testObject_6.getId()))
					    );
		}
		@Test
		@DisplayName("Save multiple invalid topics test")
		void saveMultipleInvalidTopics()
		{

			service.save(tsscTopic_testObject_3);
			service.save(tsscTopic_testObject_4);
			service.save(tsscTopic_testObject_5);
			assertAll(
					() -> assertFalse(service.findById(tsscTopic_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscTopic_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscTopic_testObject_5.getId()).isPresent())

					
					    );
		}
		
		@Test
		@DisplayName("Save multiple invalid and valid topics test")
		void saveMultipleInvalidAndInvalidTopics()
		{
			service.save(tsscTopic_testObject_1);
			service.save(tsscTopic_testObject_2);
			service.save(tsscTopic_testObject_6);
			service.save(tsscTopic_testObject_3);
			service.save(tsscTopic_testObject_4);
			service.save(tsscTopic_testObject_5);
			assertAll(
					() -> assertFalse(service.findById(tsscTopic_testObject_3.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscTopic_testObject_4.getId()).isPresent()),
					() -> assertFalse(service.findById(tsscTopic_testObject_5.getId()).isPresent()),
					() -> assertEquals(tsscTopic_testObject_1.getDescription(), service.findById(tsscTopic_testObject_1.getId()).get().getDescription()),
					() -> assertEquals(tsscTopic_testObject_2.getDescription(), service.findById(tsscTopic_testObject_2.getId()).get().getDescription()),
					() -> assertEquals(tsscTopic_testObject_6.getDescription(), service.findById(tsscTopic_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_2.getDescription(), service.findById(tsscTopic_testObject_1.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_2.getDescription(), service.findById(tsscTopic_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_1.getDescription(), service.findById(tsscTopic_testObject_2.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_1.getDescription(), service.findById(tsscTopic_testObject_6.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_6.getDescription(), service.findById(tsscTopic_testObject_1.getId()).get().getDescription()),
					() -> assertNotEquals(tsscTopic_testObject_6.getDescription(), service.findById(tsscTopic_testObject_2.getId()).get().getDescription()),
					() -> assertTrue(service.existByID(tsscTopic_testObject_1.getId())),
					() -> assertTrue(service.existByID(tsscTopic_testObject_2.getId())),
					() -> assertTrue(service.existByID(tsscTopic_testObject_6.getId()))
					
					    );
		}
	}
	
	@Nested
	@DisplayName("Topic: Update Integration Test")
	class UpdateTest{
		
		@Test
		@DisplayName("edit normal test")
		void editNormalTopic() {
			service.save(tsscTopic_testObject_1);
			assertAll(
					() -> assertEquals(service.findById(tsscTopic_testObject_1.getId()).get().getDefaultGroups(), 45),
					() -> assertEquals(service.findById(tsscTopic_testObject_1.getId()).get().getDefaultSprints(), 44)
					);
			tsscTopic_testObject_1.setDefaultGroups(5L);
			tsscTopic_testObject_1.setDefaultSprints(6L);
			service.save(tsscTopic_testObject_1);
			assertAll(
					() -> assertEquals(service.findById(tsscTopic_testObject_1.getId()).get().getDefaultGroups(), 5L),
					() -> assertEquals(service.findById(tsscTopic_testObject_1.getId()).get().getDefaultSprints(), 6L)
					);
		}
		@Test
		@DisplayName("edit a valid topic test")
		void editValidTopic() {
			service.save(tsscTopic_testObject_1);
			long id = tsscTopic_testObject_2.getId(); 
			tsscTopic_testObject_2.setId(tsscTopic_testObject_1.getId());
			service.save(tsscTopic_testObject_2);
			assertAll(() -> assertEquals(tsscTopic_testObject_2.getDescription(), service.findById(tsscTopic_testObject_1.getId()).get().getDescription()),
					  () -> assertNotEquals(tsscTopic_testObject_1.getDefaultGroups(), service.findById(tsscTopic_testObject_1.getId()).get().getDefaultGroups()),
					  () -> assertNotEquals(tsscTopic_testObject_1.getDefaultSprints(), service.findById(tsscTopic_testObject_1.getId()).get().getDefaultSprints()),
					  () -> assertEquals(tsscTopic_testObject_2.getDefaultGroups(), service.findById(tsscTopic_testObject_1.getId()).get().getDefaultGroups()),
					  () -> assertEquals(tsscTopic_testObject_2.getDefaultSprints(), service.findById(tsscTopic_testObject_1.getId()).get().getDefaultSprints())
					    );
		}
		
		@Test
		@DisplayName("edit multiple topics test")
		void editMultipleTopics() {
			service.save(tsscTopic_testObject_1);
			service.save(tsscTopic_testObject_2);
			service.save(tsscTopic_testObject_6);
			long id1,id2,id3;
			id1 = tsscTopic_testObject_1.getId();
			id2 = tsscTopic_testObject_2.getId();
			id3 = tsscTopic_testObject_6.getId();
			tsscTopic_testObject_2.setId(id1);
			tsscTopic_testObject_6.setId(id2);
			tsscTopic_testObject_1.setId(id3);
			service.save(tsscTopic_testObject_1);
			service.save(tsscTopic_testObject_2);
			service.save(tsscTopic_testObject_6);
			assertAll(
					() -> assertEquals(tsscTopic_testObject_1.getDescription(), service.findById(id3).get().getDescription()),
					() -> assertEquals(tsscTopic_testObject_1.getDefaultGroups(), service.findById(id3).get().getDefaultGroups()),
					() -> assertEquals(tsscTopic_testObject_1.getDefaultSprints(), service.findById(id3).get().getDefaultSprints()),
					() -> assertEquals(tsscTopic_testObject_2.getDescription(), service.findById(id1).get().getDescription()),
					() -> assertEquals(tsscTopic_testObject_2.getDefaultGroups(), service.findById(id1).get().getDefaultGroups()),
					() -> assertEquals(tsscTopic_testObject_2.getDefaultSprints(), service.findById(id1).get().getDefaultSprints()),
					() -> assertEquals(tsscTopic_testObject_6.getDescription(), service.findById(id2).get().getDescription()),
					() -> assertEquals(tsscTopic_testObject_6.getDefaultGroups(), service.findById(id2).get().getDefaultGroups()),
					() -> assertEquals(tsscTopic_testObject_6.getDefaultSprints(), service.findById(id2).get().getDefaultSprints()));
					

		}

	}
	
	
	
}
