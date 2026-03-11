package com.elearning.platform.repository;

import com.elearning.platform.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByThreadId(Long threadId);
}
