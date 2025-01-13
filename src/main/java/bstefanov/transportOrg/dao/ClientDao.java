package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.ClientDto;
import bstefanov.transportOrg.entity.Client;
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
}
