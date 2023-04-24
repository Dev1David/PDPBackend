package com.example.PDPMobileGame.entity;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String role_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return role_name;
    }

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
