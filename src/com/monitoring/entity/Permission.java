package com.monitoring.entity;

public enum Permission {
    PERMISSION1("一级权限"),PERMISSION2("二级权限"),PERMISSION3("三级权限"),
    PERMISSION4("四级权限"),PERMISSION5("五级权限");

    private String permission;



    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return permission;
    }

}
