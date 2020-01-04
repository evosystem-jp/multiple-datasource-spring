package com.evosys.db2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {
    
    @Id
    private int id;
    
    public int getId() {
        return id;
    }
    
}