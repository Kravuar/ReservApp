package net.kravuar.staff.model;

import lombok.Getter;

@Getter
public class StaffDetailed extends Staff {
    private final String name;
//    private final URI picture;

    public StaffDetailed(Staff staff, AccountDetails accountDetails) {
        super(staff.getId(), staff.getSub(), staff.getBusiness(), staff.isActive(), staff.getDescription());
        this.name = accountDetails.name();
//        this.picture = accountDetails.picture();
    }
}
