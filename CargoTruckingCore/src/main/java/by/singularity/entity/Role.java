package by.singularity.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Role {
    ADMIN,DISPATCHER,MANAGER,DRIVER,COMPANY_OWNER,SYS_ADMIN;

    @Override
    public String toString() {
        return super.toString();
    }
}