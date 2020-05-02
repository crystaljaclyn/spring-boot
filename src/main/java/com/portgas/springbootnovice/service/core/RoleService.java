package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.model.role.Role;

import java.util.List;

public interface RoleService {

    List<Role> getListRole();

    List<Role> getListRoleMaster();

    Role createEntity(Role paramInput);

    Role deleteEntity(Role paramInput);
}
