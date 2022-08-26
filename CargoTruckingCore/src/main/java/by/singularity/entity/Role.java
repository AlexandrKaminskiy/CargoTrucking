package by.singularity.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Role {
    ADMIN,DISPATCHER,MANAGER,DRIVER,COMPANY_OWNER;

    @Override
    public String toString() {
        return super.toString();
    }
}