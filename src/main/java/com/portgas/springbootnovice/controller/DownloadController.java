package com.portgas.springbootnovice.controller;

import com.portgas.springbootnovice.model.role.Role;
import com.portgas.springbootnovice.service.core.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/test")
public class DownloadController
{
    private final RoleService roleService;

    public DownloadController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/test")
    public void getUserFile() throws IOException
    {

        Role testRole = new Role();
        testRole.setCode("Test code");
        testRole.setName("Test role name");
        testRole.setDisplaySequence(-1);
        roleService.createEntity( testRole );
        roleService.deleteEntity( testRole );
    }
}
