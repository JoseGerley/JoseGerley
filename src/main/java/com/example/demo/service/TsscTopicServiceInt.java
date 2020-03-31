package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.TsscTopic;

public interface TsscTopicServiceInt {
	public boolean save(TsscTopic topic);
	public Optional<TsscTopic> findById(long id);
	public boolean existByID(long id);
	public void deleteAll();
}
