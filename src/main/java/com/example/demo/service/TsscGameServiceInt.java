package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.TsscGame;

public interface TsscGameServiceInt {
	public boolean save(TsscGame game);
	public boolean save(TsscGame game, long id);
	public boolean save2(TsscGame game, long id);
	public Optional<TsscGame> findById(long id);
	public boolean existById(long Id);
	public void deleteAll();
	
}
