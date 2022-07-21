package com.fyeeme.quasar.message.service;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.repository.suppport.GenericSpecificationBuilder;
import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.repository.MessageRepository;
import com.fyeeme.quasar.core.exception.AssertEntity;
import com.fyeeme.quasar.core.exception.CommonError;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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

        //TODO modifiedBy should be null, if we want to use JPA Auditable
        existedMessage.setModifiedBy(null);
        return repository.save(existedMessage);
    }

    @Override
    public Message get(Long id) {
        var message = repository.findById(id);
        AssertEntity.isTrue(message.isPresent(), CommonError.NOT_FOUND, "message not found");
        return message.get();
    }

    @Override
    public Page<Message> findAll(QueryCondition filter) {
        var specs =
                GenericSpecificationBuilder.buildSpecs(filter, Message.class);
        return repository.findAll(specs, toPageRequest(filter));
    }
}
