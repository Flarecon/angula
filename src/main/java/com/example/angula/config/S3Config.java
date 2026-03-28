package com.example.angula.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    @Profile("dev")
    public S3Client s3Client(
            @Value("${cloud.aws.creds.access-key}") String accessKey,
            @Value("${cloud.aws.creds.secret-key}") String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials
                .create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    // its used for aws service to service communication (for example maybe if i deploy it later)
    // a Ec2 or any service in which this backend is running just needs a Role with s3 permissions
    // to access this bean not access/secret credentials are needed cause we are already in

    @Bean
    @Profile("prod")
    public S3Client s3ClientProd() {
        return S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
