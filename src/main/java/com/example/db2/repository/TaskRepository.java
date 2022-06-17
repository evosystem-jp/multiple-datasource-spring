package com.example.db2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.db2.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
