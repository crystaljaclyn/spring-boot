package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.config.datasource.DBContextHolder;
import com.portgas.springbootnovice.config.datasource.DBType;
import com.portgas.springbootnovice.model.role.Role;
import com.portgas.springbootnovice.model.role.query.QRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    @Transactional(readOnly = true)
    @Override
    public List<Role> getListRole(DBType dbType) {

        DBContextHolder.setDbType(dbType);

        List<Role> roleList = new QRole()
                .findList();
        System.out.println("---------------- "+ dbType.name() +" -------------");
        for (Role r:roleList){
            System.out.println("Name : " + r.getName());
        }

        DBContextHolder.clearDbType();
        return null;
    }
}
