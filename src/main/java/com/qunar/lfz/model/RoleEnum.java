package com.qunar.lfz.model;

public enum RoleEnum {
    ADMIN("admin"),
    COMMON("common"),
    SPECIAL("special"),
    ;

    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
