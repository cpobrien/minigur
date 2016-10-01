package org.minigur.site;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Environment {
    private AmazonS3Client client;
    private JdbcManager jdbcManager;

    public AmazonS3Client getClient() {
        return client;
    }

    public JdbcManager getJdbcManager() {
        return jdbcManager;
    }

    @Autowired
    public Environment(@Value("${accessKey}") String accessKey,
                       @Value("${secretKey}") String secretKey,
                       @Value("${username}") String username,
                       @Value("${password}") String password,
                       @Value("${connectionString}") String connectionString) {
        this.client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        this.jdbcManager = new JdbcManager(username, password, connectionString);
    }

    public class JdbcManager {
        private String username;
        private String password;
        private String connectionString;

        public JdbcManager(String username, String password, String connectionString) {
            this.username = username;
            this.password = password;
            this.connectionString = connectionString;
        }

        public Connection connect() throws SQLException {
            return DriverManager.getConnection(connectionString, username, password);
        }

    }
}
