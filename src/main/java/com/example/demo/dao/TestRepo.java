package com.example.demo.dao;

import com.example.demo.model.qc.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findDistinctByInuseAndName(String inuse, String name);
}
