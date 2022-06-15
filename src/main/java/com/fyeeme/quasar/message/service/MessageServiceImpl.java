package com.fyeeme.quasar.message.service;

import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.repository.MessageRepository;
import com.fyeeme.quasar.security.exception.AssertEntity;
import com.fyeeme.quasar.security.exception.CommonError;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message create(Message message) {
        return repository.save(message);
    }

    @Override
    public Message update(Message message) {
        var existedMessage = get(message.getId());
        BeanUtils.copyProperties(message, existedMessage, "id", "createdBy", "createdDate");
        return repository.save(existedMessage);
    }

    @Override
    public Message get(Long id) {
        var message = repository.findById(id);
        AssertEntity.isTrue(message.isPresent(), CommonError.NOT_FOUND, "message not found");
        return message.get();
    }
}
