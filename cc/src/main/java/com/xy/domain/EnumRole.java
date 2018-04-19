package com.xy.domain;

public enum EnumRole {

    SYSTEM(1, "ROLE_SYSTEM"),

    FLOW(2, "ROLE_FLOW"),

    STORE(3, "ROLE_STORE");

    private int id;

    private String role;

    private EnumRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
