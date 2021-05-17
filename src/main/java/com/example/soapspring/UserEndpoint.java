package com.example.soapspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    @Autowired
    private UserRepository userRepository;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        return userRepository.findUser(request);
    }

    @PayloadRoot(namespace = "http://spring.io/guides/gs-producing-web-service-register", localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse registerUser(@RequestPayload GetUserRequest request) {
        return userRepository.registerUser(request);
    }
}