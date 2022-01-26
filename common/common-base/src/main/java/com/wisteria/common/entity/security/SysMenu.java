package com.wisteria.common.entity.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMenu implements Serializable {
    private int menuId;
    private String menuName;
    private String permission;
    private String path;
    private int parentId;
    private String icon;
    private String component;
    private int order;
    private int type;
    private int dataStatus;
    private int funcStatus;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
