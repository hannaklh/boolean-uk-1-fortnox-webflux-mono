package org.booleanuk.demo.model.repo;

import org.booleanuk.demo.model.jpa.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
