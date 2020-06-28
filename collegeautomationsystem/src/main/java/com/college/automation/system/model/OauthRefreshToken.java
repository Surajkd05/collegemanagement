package com.college.automation.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OauthRefreshToken {

    @Id
    @NotNull
    private String tokenId;

    @NotNull
    @Lob
    private byte[] token;

    @NotNull
    @Lob
    private byte[] authentication;
}
