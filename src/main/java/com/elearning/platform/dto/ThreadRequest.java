package com.elearning.platform.dto;

import jakarta.validation.constraints.NotBlank;

public class ThreadRequest {

    @NotBlank
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
