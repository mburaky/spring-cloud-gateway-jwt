package com.justmop.jwtdemo.repository;

import com.justmop.jwtdemo.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    Optional<Client> findOneByPhone(String phoneNumber);
}
