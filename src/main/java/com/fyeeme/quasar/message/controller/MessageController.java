package com.fyeeme.quasar.message.controller;

import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        var savedMessage = messageService.create(message);
        return savedMessage;
    }

    @PutMapping
    public Message updateMessage(@RequestBody Message message) {
        var savedMessage = messageService.update(message);
        return savedMessage;
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageService.get(id);
    }
}
