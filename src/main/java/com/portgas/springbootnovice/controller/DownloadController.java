package com.portgas.springbootnovice.controller;

import com.portgas.springbootnovice.service.core.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/download")
public class DownloadController
{
    private final RoleService roleService;

    public DownloadController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/test-master-replicate-database")
    public void getUserFile() throws IOException
    {

        roleService.getListRole();
        roleService.getListRoleMaster();
    }
}
