package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class LinkClientDto {
    private long clientId;
    private BigDecimal price;

    public LinkClientDto(long clientId, BigDecimal price) {
        this.clientId = clientId;
        this.price = price;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LinkClientDto{" +
                "clientId=" + clientId +
                ", price=" + price +
                '}';
    }
}
