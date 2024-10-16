package com.application.mongodb_api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Client {
    @Id
    String id;
    String name;
    String email;

    public Client() {
    }

    public Client(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCounterPartOfHexID(){

        // integer counter portion extraction(last 3 bytes) from  mongoDB ID HexCode
        // String clientHexID = latestClient.getFirst().getId();
        String lastThreeBytesHex = this.id.substring(this.id.length() - 6);
        int ClientIdCounterPart = Integer.parseInt(lastThreeBytesHex, 16);
        return ClientIdCounterPart;

    }


}
