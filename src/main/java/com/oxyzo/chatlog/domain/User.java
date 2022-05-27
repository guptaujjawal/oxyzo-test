package com.oxyzo.chatlog.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain model for user
 */
@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    private String id;

    @OneToMany(orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private List<Message> messages;

}
