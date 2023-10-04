package com.buixuantu.parking.Service;

import com.buixuantu.parking.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
	RoleEntity findRoleById(String id);
	RoleEntity addRole(String id,String name);
	void deleteRoleById(String id);
	void updateRole(String id,String name);
	List<RoleEntity> getAllRole();
	Page<RoleEntity> findAllPage(Pageable pageable);
}
