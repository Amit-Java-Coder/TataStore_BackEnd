package com.watch.store.repository;

import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.repository.JpaRepository;

import com.watch.store.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
