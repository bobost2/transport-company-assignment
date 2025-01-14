package bstefanov.transportOrg.dto;

import bstefanov.transportOrg.entity.Client;

import java.math.BigDecimal;

public class ClientInvoiceDto {
    ClientDto client;
    BigDecimal amount;

    public ClientInvoiceDto(Client client, BigDecimal amount) {
        this.client = new ClientDto(client.getId(), client.getFirstName(), client.getLastName());
        this.amount = amount;
    }

    public ClientInvoiceDto(ClientDto client, BigDecimal amount) {
        this.client = client;
        this.amount = amount;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClientInvoiceDto{" +
                "client=" + client +
                ", amount=" + amount +
                '}';
    }
}
