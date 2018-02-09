package com.example.template.repository;


import com.example.template.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Repository extends JpaRepository<Car, Long> {
}