package com.pas.app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class StoreApp extends Application {
/*    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CustomExceptionMapper.class);
        *//* add your additional JAX-RS classes here *//*
        return classes;
    }*/
}