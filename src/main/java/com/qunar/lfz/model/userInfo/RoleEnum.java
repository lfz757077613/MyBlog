package com.qunar.lfz.model.userInfo;

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
