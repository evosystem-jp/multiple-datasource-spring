package com.evosys.db1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    
    @Id
    private Integer id;
    
    public Integer getId() {
        return id;
    }
    
}
