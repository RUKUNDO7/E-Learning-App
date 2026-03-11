package com.elearning.platform.controller;

import com.elearning.platform.domain.CommunicationThread;
import com.elearning.platform.domain.Message;
import com.elearning.platform.dto.MessageRequest;
import com.elearning.platform.dto.ThreadRequest;
import com.elearning.platform.service.CommunicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/communications")
public class CommunicationController {

    private final CommunicationService communicationService;

    public CommunicationController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @PostMapping("/threads")
    public CommunicationThread createThread(@RequestHeader("X-Actor-Id") Long actorId,
                                            @Valid @RequestBody ThreadRequest request) {
        return communicationService.createThread(request, actorId);
    }

    @GetMapping("/threads")
    public List<CommunicationThread> listThreads() {
        return communicationService.listThreads();
    }

    @PostMapping("/threads/{threadId}/messages")
    public Message postMessage(@PathVariable Long threadId,
                               @RequestHeader("X-Actor-Id") Long actorId,
                               @Valid @RequestBody MessageRequest request) {
        return communicationService.post(threadId, request, actorId);
    }

    @GetMapping("/threads/{threadId}/messages")
    public List<Message> getMessages(@PathVariable Long threadId) {
        return communicationService.fetchMessages(threadId);
    }
}
