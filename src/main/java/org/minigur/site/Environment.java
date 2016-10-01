package org.minigur.site;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
public class Environment {
    private AmazonS3Client client;

    public AmazonS3Client getClient() {
        return client;
    }

    @Autowired
    public Environment(@Value("${accessKey}") String accessKey,
                       @Value("${secretKey}") String secretKey) {
        client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
    }

}
