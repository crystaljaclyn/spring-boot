package com.portgas.springbootnovice.service.core;

import com.portgas.springbootnovice.config.database.DBType;
import com.portgas.springbootnovice.config.database.DatasourceType;
import com.portgas.springbootnovice.model.role.Role;
import com.portgas.springbootnovice.model.role.query.QRole;
import io.ebean.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER= LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    @DatasourceType(dbType = DBType.REPLICATE)
    @Transactional(readOnly = true)
    public List<Role> getListRole() {

        List<Role> roleList = new QRole()
                .findList();
        for (Role r:roleList){
            LOGGER.info("Name : " + r.getName());
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    @DatasourceType(dbType = DBType.MASTER)
    public List<Role> getListRoleMaster() {

        List<Role> roleList = new QRole()
                .findList();
        for (Role r:roleList){
            LOGGER.info("Name : " + r.getName());
        }
        return null;
    }

    /**
     *
     * @param paramInput
     * @return
     */
    @Override
    @Transactional
    @DatasourceType(dbType = DBType.MASTER)
    public Role createEntity(Role paramInput) {
        paramInput.save();

        Role role = new Role();
        role.setName("TESt Role                                                                                                                                                                         sdfsdfdsf         sdfdsfsdfsdfsdfdsf");
        role.save();

        role.delete();
        return paramInput;
    }

    /**
     *
     * @param paramInput
     * @return
     */
    @Override
    @Transactional
    @DatasourceType(dbType = DBType.MASTER)
    public Role deleteEntity(Role paramInput) {
        paramInput.deletePermanent();
        return paramInput;
    }
}
