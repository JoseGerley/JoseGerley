package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TsscTimecontrol;
import com.example.demo.model.TsscTopic;
@Repository
public interface TsscTopicRepository extends CrudRepository<TsscTopic, Long> {

}
