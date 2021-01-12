package lk.prison_management.asset.employee.entity.enums;

public enum EmployeeStatus {
    WORKING("Working"),
    LEAVE("EmployeeLeave"),
    SUSPENDED("Suspended"),
    NOPAY("No pay"),
    MEDICAL("Medical EmployeeLeave"),
    BLOCK("Block"),
    RESIGNED("Resigned"),
    RETIRED("Retired");

    private final String employeeStatus;

    EmployeeStatus(String employeeStatus) {

        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }
}
