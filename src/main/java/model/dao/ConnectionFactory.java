package model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String PROPERTIES_FILE = "/db.properties";
    private static Properties properties;

    private ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            if (properties == null) {
                properties = loadProperties();
            }
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão: " + e.getMessage(), e);
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = ConnectionFactory.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (is == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado");
            }
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler db.properties: " + e.getMessage(), e);
        }
    }
}
