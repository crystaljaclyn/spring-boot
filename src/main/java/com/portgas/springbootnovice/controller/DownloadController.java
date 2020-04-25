package com.portgas.springbootnovice.controller;

import com.portgas.springbootnovice.config.datasource.DBType;
import com.portgas.springbootnovice.service.core.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/download")
public class DownloadController
{
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/test")
    public void getUserFile() throws IOException
    {
        roleService.getListRole(DBType.REPLICATE);
        roleService.getListRole(DBType.MASTER);
    }
}
