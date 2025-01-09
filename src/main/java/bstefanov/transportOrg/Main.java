package bstefanov.transportOrg;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        // Set the environment variable DB_PASSWORD for the database
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        // Main logic
        SessionFactoryUtil.getSessionFactory().openSession();
    }
}