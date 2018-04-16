package com.sd.his.model;

import javax.persistence.*;

@Entity
@Table(name = "Properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "CLIENT_ID")
    private String clientId;
    @Column(name = "CLIENT_SECRET")
    private String clientSecret;
    @Column(name="AUTH_SERVER_SCHEM")
    private String authServerSchem;

    public Properties(String clientId, String clientSecret, String authServerSchem) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authServerSchem = authServerSchem;
    }

    public Properties(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthServerSchem() {
        return authServerSchem;
    }

    public void setAuthServerSchem(String authServerSchem) {
        this.authServerSchem = authServerSchem;
    }
}