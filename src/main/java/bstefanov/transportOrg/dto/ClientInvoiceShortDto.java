package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class ClientInvoiceShortDto {
    private long id;
    private String companyName;
    private BigDecimal amount;

    public ClientInvoiceShortDto(long id, String companyName, BigDecimal amount) {
        this.id = id;
        this.companyName = companyName;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClientInvoiceShortDto{" +
                "companyName='" + companyName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
