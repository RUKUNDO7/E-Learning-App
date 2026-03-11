package com.elearning.platform.service;

import com.elearning.platform.domain.CommunicationThread;
import com.elearning.platform.domain.Message;
import com.elearning.platform.domain.UserAccount;
import com.elearning.platform.domain.UserRole;
import com.elearning.platform.dto.MessageRequest;
import com.elearning.platform.dto.ThreadRequest;
import com.elearning.platform.repository.CommunicationThreadRepository;
import com.elearning.platform.repository.MessageRepository;
import com.elearning.platform.repository.UserAccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunicationService {

    private final CommunicationThreadRepository threadRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public CommunicationService(CommunicationThreadRepository threadRepository, MessageRepository messageRepository, UserService userService) {
        this.threadRepository = threadRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public CommunicationThread createThread(ThreadRequest request, Long actorId) {
        userService.assertRole(actorId, UserRole.ADMIN, UserRole.INSTRUCTOR, UserRole.STUDENT);
        UserAccount actor = userService.findOne(actorId);
        CommunicationThread thread = new CommunicationThread(request.getTopic(), actor);
        return threadRepository.save(thread);
    }

    @Transactional
    public Message post(Long threadId, MessageRequest request, Long actorId) {
        UserAccount actor = userService.findOne(actorId);
        CommunicationThread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));
        Message message = new Message(request.getContent(), actor);
        message.setThread(thread);
        return messageRepository.save(message);
    }

    public List<Message> fetchMessages(Long threadId) {
        return messageRepository.findByThreadId(threadId);
    }

    public List<CommunicationThread> listThreads() {
        return threadRepository.findAll();
    }
}
