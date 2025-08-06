package com.mahmoud.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(indexes = {
        @Index(columnList = "chatNumber")
})
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private int chatNumber;

    private int messageCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private Application application;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;
}
