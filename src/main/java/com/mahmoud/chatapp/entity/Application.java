package com.mahmoud.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "application")
@Table(indexes = {
        @Index(columnList = "token", unique = true)
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String name;

    @Column(unique = true)
    private String token;

    private int chatCount;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Chat> chats;

}
