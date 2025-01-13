package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class CompanyListEntityDto {
    private long id;
    private String name;
    private String address;
    private BigDecimal profit;

    public CompanyListEntityDto(long id, String name, String address, BigDecimal profit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.profit = profit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "CompanyListDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", profit=" + profit +
                '}';
    }
}
