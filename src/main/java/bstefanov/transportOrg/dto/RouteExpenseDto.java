package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class RouteExpenseDto {
    private BigDecimal amount;
    private String reason;
    private boolean isEmployee;
    private long employeeId;

    public RouteExpenseDto(BigDecimal amount, String reason) {
        this.amount = amount;
        this.reason = reason;
        this.isEmployee = false;
    }

    public RouteExpenseDto(BigDecimal amount, String reason, long employeeId) {
        this.amount = amount;
        this.reason = reason;
        this.isEmployee = true;
        this.employeeId = employeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "RouteExpenseDto{" +
                "amount=" + amount +
                ", reason='" + reason + '\'' +
                ", isEmployee=" + isEmployee +
                ", employeeId=" + employeeId +
                '}';
    }
}
