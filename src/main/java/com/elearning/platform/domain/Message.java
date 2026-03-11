package com.elearning.platform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserAccount sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private CommunicationThread thread;

    private LocalDateTime sentAt = LocalDateTime.now();

    public Message() {
    }

    public Message(String content, UserAccount sender) {
        this.content = content;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserAccount getSender() {
        return sender;
    }

    public CommunicationThread getThread() {
        return thread;
    }

    public void setThread(CommunicationThread thread) {
        this.thread = thread;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
