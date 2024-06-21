package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@Data
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
})
public  class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @NaturalId(mutable = true)
    private Status status;
    private Boolean status2;
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account() {

    }

    public void setStatus2(Boolean status2) {
        this.status2 = !status.equals(Status.NON_ACTIVE);

    }
}
