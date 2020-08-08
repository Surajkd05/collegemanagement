package com.college.automation.system.dtos;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class PlacementDto {

    @Temporal(TemporalType.DATE)
    private Date placedDate;

    private String salary;

    private String companyName;

    private Long branchId;

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
