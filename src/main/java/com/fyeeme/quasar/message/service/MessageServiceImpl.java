package com.fyeeme.quasar.message.service;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.repository.ResourceRepository;
import com.fyeeme.quasar.base.repository.support.GenericSpecificationBuilder;
import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public Message publish(Long id) {
        return null;
    }

    @Override
    public Message undoPublish(Long id) {
        return null;
    }

    @Override
    public ResourceRepository<Message> getRepository() {
        return repository;
    }

    @Override
    public Specification<Message> buildSpecs(QueryCondition query) {
        return GenericSpecificationBuilder.buildSpecs(query, Message.class);
    }

    @Override
    public String[] extendedIgnoreProperties() {
        return extendedIgnoreProperties("pid");
    }
}
