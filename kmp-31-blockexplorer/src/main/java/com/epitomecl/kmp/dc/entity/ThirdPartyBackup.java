package com.epitomecl.kmp.dc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ThirdPartyBackup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String guid;

    @NotNull
    private String payload;

    @NotNull
    private String priv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    @Override
    public String toString() {
        return "SharedBackup{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", payload='" + payload + '\'' +
                ", priv='" + priv + '\'' +
                '}';
    }
}