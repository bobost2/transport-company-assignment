package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.ClientDto;
import bstefanov.transportOrg.dto.ClientInvoiceDto;
import bstefanov.transportOrg.dto.ClientInvoiceShortDto;
import bstefanov.transportOrg.entity.Client;
import bstefanov.transportOrg.entity.Transit;
import bstefanov.transportOrg.entity.TransitClient;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    public static void createClient(ClientDto clientDto) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Client client;
            if (clientDto.isLegalEntity()) {
                client = new Client(clientDto.getFirstName(), clientDto.getLastName(), clientDto.getCompanyName());
            } else {
                client = new Client(clientDto.getFirstName(), clientDto.getLastName());
            }
            session.persist(client);

            transaction.commit();
        }
    }

    public static List<ClientDto> getClients() {
        List<ClientDto> clients = new ArrayList<>();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("from Client", Client.class).list().forEach(client -> {
                if (client.isLegalEntity()) {
                    clients.add(new ClientDto(client.getId(), client.getFirstName(), client.getLastName(), client.getCompanyName()));
                } else {
                    clients.add(new ClientDto(client.getId(), client.getFirstName(), client.getLastName()));
                }
            });
        }
        return clients;
    }

    public static void editClient(ClientDto client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Client clientEntity = session.get(Client.class, client.getId());
            clientEntity.setFirstName(client.getFirstName());
            clientEntity.setLastName(client.getLastName());
            clientEntity.setLegalEntity(client.isLegalEntity());
            if (client.isLegalEntity()) {
                clientEntity.setCompanyName(client.getCompanyName());
            } else {
                clientEntity.setCompanyName(null);
            }
            session.merge(clientEntity);

            transaction.commit();
        }
    }

    public static void deleteClient(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            // TODO: Handle constraint violation
            session.remove(client);
            transaction.commit();
        }
    }

    public static List<ClientInvoiceDto> getClientUnpaidInvoices(long companyId) {
        List<ClientInvoiceDto> invoices;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientInvoiceDto> query = cb.createQuery(ClientInvoiceDto.class);
            Root<TransitClient> transitClientRoot = query.from(TransitClient.class);
            Join<TransitClient, Client> clientJoin = transitClientRoot.join("client");
            Join<TransitClient, Transit> transitJoin = transitClientRoot.join("transit");

            query.select(cb.construct(ClientInvoiceDto.class, clientJoin, transitClientRoot.get("amountToPay")))
                    .where(cb.and( cb.equal(transitJoin.get("company").get("id"), companyId),
                            cb.isFalse(transitClientRoot.get("hasPaid"))));

            invoices = session.createQuery(query).getResultList();
        }
        return invoices;
    }

    public static List<ClientInvoiceShortDto> getClientUnpaidInvoicesShort(long clientId) {
        List<ClientInvoiceShortDto> invoices;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String jpql = "SELECT new bstefanov.transportOrg.dto.ClientInvoiceShortDto(tc.id, t.company.name, tc.amountToPay) " +
                    "FROM TransitClient tc " +
                    "JOIN tc.client c " +
                    "JOIN tc.transit t " +
                    "JOIN t.company comp " +
                    "WHERE tc.client.id = :clientID AND tc.hasPaid = false";
            TypedQuery<ClientInvoiceShortDto> query = session.createQuery(jpql, ClientInvoiceShortDto.class);
            query.setParameter("clientID", clientId);
            invoices = query.getResultList();

            transaction.commit();
        }
        return invoices;
    }

    public static void payInvoice(long invoiceId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            TransitClient transitClient = session.get(TransitClient.class, invoiceId);
            transitClient.setHasPaid(true);

            session.merge(transitClient);
            transaction.commit();
        }
    }
}
