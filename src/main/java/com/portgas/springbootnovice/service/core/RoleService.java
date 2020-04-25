package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.config.datasource.DBType;
import com.portgas.springbootnovice.model.role.Role;

import java.util.List;

public interface RoleService {

    List<Role> getListRole(DBType dbType);
}
