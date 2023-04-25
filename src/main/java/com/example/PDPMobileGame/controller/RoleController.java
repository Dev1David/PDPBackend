package com.example.PDPMobileGame.controller;

import com.example.PDPMobileGame.entity.RoleEntity;
import com.example.PDPMobileGame.repository.RoleRepository;
import com.example.PDPMobileGame.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    RoleService roleService = new RoleService();
    @GetMapping
    public List<RoleEntity> getAllRoles() {
        return roleService.getAllRoles();
    }
}
