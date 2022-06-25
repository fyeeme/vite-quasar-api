package com.fyeeme.quasar.message.controller;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @RolesAllowed({"message_write"})
    public Message createMessage(@RequestBody Message message) {
        var savedMessage = messageService.create(message);
        return savedMessage;
    }

    @PutMapping
    @RolesAllowed({"message_write"})
    public Message updateMessage(@RequestBody Message message) {
        var savedMessage = messageService.update(message);
        return savedMessage;
    }

    @GetMapping("/{id}")
    @RolesAllowed({"message_read"})
    public Message getMessage(@PathVariable Long id) {
        return messageService.get(id);
    }

    @PostMapping("/search")
    public Page<Message> searchMessages(@RequestBody QueryCondition query) {
        return messageService.findAll(query);
    }
}
