package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TsscGameAdmin;
import com.example.demo.model.TsscGroup;

public interface TsscGroupRepository extends CrudRepository<TsscGroup, Long> {

}
