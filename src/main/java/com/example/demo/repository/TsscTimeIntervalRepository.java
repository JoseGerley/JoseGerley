package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TsscTimeInterval;
import com.example.demo.model.TsscTimecontrol;

public interface TsscTimeIntervalRepository extends CrudRepository<TsscTimeInterval, Long> {

}
