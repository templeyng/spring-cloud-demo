package com.wisteria.common.entity.security;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysRole implements Serializable {
    private int roleId;
    private String roleName;
    private String roleValue;
    private int dataStatus;
    private int funcStatus;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private List<SysMenu> menus = new ArrayList<>();
}
