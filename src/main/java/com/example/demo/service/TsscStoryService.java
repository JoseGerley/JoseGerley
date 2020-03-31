package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TsscStory;
import com.example.demo.repository.TsscGameRepository;
import com.example.demo.repository.TsscStoryRepository;

@Service
public class TsscStoryService implements TsscStoryServiceInt {

	private TsscStoryRepository storyRepository;
	private TsscGameRepository serviceInt;
	
	@Autowired
	public TsscStoryService(TsscStoryRepository storyRepository,TsscGameRepository gameService ) {
		this.storyRepository = storyRepository;
		this.serviceInt = gameService;
	}

	@Override
	public Optional<TsscStory> findById(long id) {
		return storyRepository.findById(id);
	}
	@Override
	public boolean save(TsscStory story, long id) {
		if(story == null) {
			
		}
		else {
			if(story.getBusinessValue().compareTo(BigDecimal.ZERO)==1 
					&& story.getInitialSprint().compareTo(BigDecimal.ZERO)==1 
					&& story.getPriority().compareTo(BigDecimal.ZERO)==1
					&& story.getTsscGame()!= null) {
				if(serviceInt.existsById(id)) {
					storyRepository.save(story);
					return true;
				}
			}
		}
		return false;
		
	}

	@Override
	public void deleteAll() {
		storyRepository.deleteAll();
		
	}

	@Override
	public boolean existById(long id) {
		return storyRepository.existsById(id);
	}

}
