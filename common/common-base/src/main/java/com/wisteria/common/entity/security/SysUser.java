package com.wisteria.common.entity.security;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysUser implements Serializable {
    private int userId;
    private String username;
    private String alias;
    private String password;
    private int sex;
    private String email;
    private String mobile;
    private int dataStatus;
    private int funcStatus;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private List<SysRole> roles = new ArrayList<>();
}
