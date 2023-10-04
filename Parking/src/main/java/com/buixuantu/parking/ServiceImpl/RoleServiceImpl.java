package com.buixuantu.parking.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buixuantu.parking.Repository.RoleRepository;
import com.buixuantu.parking.Service.RoleService;
import com.buixuantu.parking.entity.RoleEntity;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleEntity findRoleById(String id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public RoleEntity addRole(String id, String name) {
		RoleEntity role = new RoleEntity();
		role.setId(id);
		role.setName(name);
		return roleRepository.save(role);
	}

	@Override
	public void deleteRoleById(String id) {
		roleRepository.deleteById(id);
	}

	@Override
	public void updateRole(String id, String name) {
		RoleEntity role = roleRepository.findById(id).orElse(null);
		role.setName(name);
		roleRepository.save(role);
	}

	@Override
	public List<RoleEntity> getAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public Page<RoleEntity> findAllPage(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

}
