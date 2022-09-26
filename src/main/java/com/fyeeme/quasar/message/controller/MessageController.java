package com.fyeeme.quasar.message.controller;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.message.entity.Message;
import com.fyeeme.quasar.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@Tag(name = "message", description = "Message Management API")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Create new message")
    @Parameter(name = "message", description = "The message data")
    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageService.create(message);
    }

    @Operation(summary = "Update message by ID")
    @Parameter(name = "id", description = "The message id")
    @Parameter(name = "message", description = "The message dto")
    @PutMapping("{id}")
    public Message update(@PathVariable Long id, @RequestBody Message message) {
        return messageService.update(message);
    }

    @Operation(summary = "Get existed message by ID")
    @Parameter(name = "id", description = "The message id")
    @GetMapping("/{id}")
    public Message get(@PathVariable Long id) {
        return messageService.get(id);
    }

    @Operation(summary = "Delete existed message by ID")
    @Parameter(name = "id", description = "The message id")
    @DeleteMapping("/{id}")
    public Message delete(@PathVariable Long id) {
        return messageService.delete(id);
    }

    @Operation(description = "This api is only prepared for admin", summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @GetMapping
    public Page<Message> findAll(QueryCondition filter) {
        return messageService.findAll(filter);
    }

    @Operation(summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @GetMapping("/search")
    public Page<Message> search(@RequestBody QueryCondition query) {
        return messageService.findAll(query);
    }

    @Operation(description = "This api is only prepared for admin", summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @PutMapping("{id}/publish")
    public Message publish(@PathVariable Long id) {
        return  messageService.publish(id);
    }

    @Operation(summary = "Search products by query filter")
    @Parameter(name = "filter", description = "The query filter")
    @DeleteMapping("{id}/publish")
    public Message undoPublish(@PathVariable Long id) {
        return messageService.undoPublish(id);
    }
}
