package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TsscTopic;
import com.example.demo.repository.TsscTopicRepository;


@Service
public class TsscTopicService implements TsscTopicServiceInt{

	private TsscTopicRepository topicRepository;
	
	@Autowired
	public TsscTopicService(TsscTopicRepository repository) {
		this.topicRepository = repository;
	}

	@Override
	public boolean save(TsscTopic topic) {
		if(topic == null)
			return false;
		if(topic.getDefaultGroups() > 0 && topic.getDefaultSprints() > 0) {
			topicRepository.save(topic);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Optional<TsscTopic> findById(long id) {

		return topicRepository.findById(id);
	}

	@Override
	public boolean existByID(long id) {
		return topicRepository.existsById(id);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		topicRepository.deleteAll();;
	}
}
