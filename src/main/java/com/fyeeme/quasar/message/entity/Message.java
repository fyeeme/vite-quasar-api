package com.fyeeme.quasar.message.entity;


import com.fyeeme.quasar.base.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serial;

@Entity
@Getter
@Setter
public class Message extends Auditable<Long> {
    @Serial
    private static final long serialVersionUID = 4185249080943442252L;

    private String title;
    private String content;
}
