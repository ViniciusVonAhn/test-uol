package com.compasso.client.repositories;

import com.compasso.client.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    CityEntity findOneByNameIgnoreCase(String name);

    List<CityEntity> findByNameContainingIgnoreCaseOrderByName(String name);

    List<CityEntity> findByStateContainingIgnoreCaseOrderByName(String state);

    List<CityEntity> findAllByOrderByName();
}
