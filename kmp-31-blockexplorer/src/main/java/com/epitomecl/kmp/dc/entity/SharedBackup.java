package com.epitomecl.kmp.dc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SharedBackup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String guid;

    @NotNull
    private String payload;

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

    @Override
    public String toString() {
        return "SharedBackup{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}