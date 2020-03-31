package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TsscGameAdmin;
import com.example.demo.model.TsscStory;
@Repository
public interface TsscStoryRepository extends CrudRepository<TsscStory, Long> {

}
