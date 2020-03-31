package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.TsscStory;


public interface TsscStoryServiceInt {
	public boolean save(TsscStory story, long id);
	public Optional<TsscStory> findById(long id);
	public void deleteAll();
	public boolean existById(long id);
}
