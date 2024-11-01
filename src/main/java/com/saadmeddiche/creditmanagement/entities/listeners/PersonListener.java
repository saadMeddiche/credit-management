package com.saadmeddiche.creditmanagement.entities.listeners;

import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.global_constants.CacheNames;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

public class PersonListener {

    @PostPersist @PostUpdate @PostRemove
    @CacheEvict(value = CacheNames.PERSONS , key = CacheNames.PERSONS)
    public void afterAnyInteraction(){}

    @PostUpdate
    @CachePut(value = CacheNames.PERSONS , key = "#person.id")
    public void afterUpdate(Person person){}

    @PostRemove
    @CachePut(value = CacheNames.PERSONS , key = "#person.id")
    public void afterDelete(Person person){}


}
