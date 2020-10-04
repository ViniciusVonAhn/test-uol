package com.compasso.client.repositories;

import com.compasso.client.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findAllByFullNameContainsIgnoreCaseOrderByFullName(String fullName);

    List<ClientEntity> findAllByOrderByFullName();
}
