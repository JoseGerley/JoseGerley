package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TsscDeliverable;
import com.example.demo.model.TsscGameAdmin;

public interface TsscGameAdminRepository extends CrudRepository<TsscGameAdmin, Long> {

}
