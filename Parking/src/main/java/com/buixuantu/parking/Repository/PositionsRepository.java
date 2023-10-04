package com.buixuantu.parking.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buixuantu.parking.entity.PositionsEntity;

import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface PositionsRepository extends JpaRepository<PositionsEntity,String>{
}
