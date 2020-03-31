package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TsscGame;
import com.example.demo.model.TsscStory;
import com.example.demo.model.TsscTimecontrol;
import com.example.demo.repository.TsscGameRepository;
import com.example.demo.repository.TsscTopicRepository;



@Service
public class TsscGameService implements TsscGameServiceInt {
	


	private TsscGameRepository repository;
	private TsscTopicRepository topicRepository;
	
	@Autowired
	TsscGameService(TsscGameRepository repository, TsscTopicRepository topicRepository){
	this.repository = repository;
	this.topicRepository = topicRepository;
	}



	@Override
	public boolean existById(long id) {
		return repository.existsById(id);
	}

	@Override
	public Optional<TsscGame> findById(long id) {
		return repository.findById(id);
	}


	public TsscTopicRepository getTopicRepository() {
		return topicRepository;
	}



	public void setTopicRepository(TsscTopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}



	@Override
	public boolean save(TsscGame game) {
		if(game == null) {
			
		}
		else {
			if(game.getNGroups() > 0 && game.getNSprints() > 0) {
				if(game.getTsscTopic() == null) {
					repository.save(game);
					return true;
				}
			}
		}
		return false;
	}
	


	@Override
	public boolean save2(TsscGame game, long id) {

			if(game == null) {
				
			}
			else {
				if(game.getNGroups() > 0 && game.getNSprints() > 0) {
					if(game.getTsscTopic() == null) {
						repository.save(game);
						return true;
					}
					else {
						if(topicRepository.existsById(id)) {
							List<TsscTimecontrol> listTimecontrol = game.getTsscTopic().getTsscTimecontrols();
							game.getTsscTimecontrols().addAll(listTimecontrol);
							List<TsscStory> lisStories = game.getTsscTopic().getTsscStories();
							game.getTsscStories().addAll(lisStories);
							repository.save(game);
							return true;
						}
					}
				}
			}
			return false;
	}



	@Override
	public boolean save(TsscGame game, long id) {
		if(game == null) {
					
		}
		else {
			if(game.getNGroups() > 0 && game.getNSprints() > 0) {
				if(game.getTsscTopic() == null) {
					repository.save(game);
					return true;
				}
				else {
					if(topicRepository.existsById(id)) {
						repository.save(game);
						return true;
					}
				}
			}
		}
		return false;
	}



	@Override
	public void deleteAll() {
		repository.deleteAll();
		
	}



}
