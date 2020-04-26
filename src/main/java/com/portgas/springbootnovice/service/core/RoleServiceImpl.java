package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.config.database.DBType;
import com.portgas.springbootnovice.config.database.TransactionalCustom;
import com.portgas.springbootnovice.model.role.Role;
import com.portgas.springbootnovice.model.role.query.QRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER= LoggerFactory.getLogger(RoleServiceImpl.class);

    @TransactionalCustom(readOnly = true,dbType = DBType.REPLICATE)
    @Override
    public List<Role> getListRole() {

        List<Role> roleList = new QRole()
                .findList();
        for (Role r:roleList){
            LOGGER.info("Name : " + r.getName());
        }
        return null;
    }


    @TransactionalCustom(readOnly = true,dbType = DBType.MASTER)
    @Override
    public List<Role> getListRoleMaster() {

        List<Role> roleList = new QRole()
                .findList();
        for (Role r:roleList){
            LOGGER.info("Name : " + r.getName());
        }
        return null;
    }
}
