package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.config.database.DBContextHolder;
import com.portgas.springbootnovice.config.database.DBType;
import com.portgas.springbootnovice.model.role.Role;
import com.portgas.springbootnovice.model.role.query.QRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER= LoggerFactory.getLogger(RoleServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public List<Role> getListRole(DBType dbType) {

        DBContextHolder.setDbType(dbType);

        List<Role> roleList = new QRole()
                .findList();
        LOGGER.info("---------------- "+ dbType.name() +" -------------");
        for (Role r:roleList){
            LOGGER.info("Name : " + r.getName());
        }

        DBContextHolder.clearDbType();
        return null;
    }
}
