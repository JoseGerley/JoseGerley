package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TsscGameAdmin;
import com.example.demo.model.TsscState;

public interface TsscStateRepository extends CrudRepository<TsscState, Long> {

}
