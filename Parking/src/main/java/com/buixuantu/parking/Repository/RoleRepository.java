package com.buixuantu.parking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buixuantu.parking.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,String>{

}
