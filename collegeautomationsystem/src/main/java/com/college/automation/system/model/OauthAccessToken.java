package com.college.automation.system.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
public class OauthAccessToken {

    @Id
    @NotNull
    private String authenticationId;

    @NotNull
    private String tokenId;

    @NotNull
    @Lob
    private byte[] token;

    @NotNull
    private String userName;

    @NotNull
    private String clientId;

    @NotNull
    @Lob
    private byte[] authentication;

    @NotNull
    private String refreshToken;
}
