package com.saadmeddiche.creditmanagement.bean;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Mapper {

    @Bean @Scope("prototype")
    public ModelMapper modelMapper() {
        return new ModelMapper().registerModule(new RecordModule());
    }

}
