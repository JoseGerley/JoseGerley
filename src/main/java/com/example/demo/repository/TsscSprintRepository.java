package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TsscGameAdmin;
import com.example.demo.model.TsscSprint;

public interface TsscSprintRepository extends CrudRepository<TsscSprint, Long> {

}
