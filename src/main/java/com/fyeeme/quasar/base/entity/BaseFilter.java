package com.fyeeme.quasar.base.entity;

import org.springframework.data.domain.Sort;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilter implements Serializable {
    private Integer page = 0;
    private Integer pageSize = 10;

    private String keyword;
    private String sortField;
    private Boolean ascending;

    public Integer getPage() {
        return page > 1 ? page - 1 : 0;
    }

    public String getSortField() {
        return Optional.ofNullable(sortField).orElse("id");
    }

    public Sort.Direction getSortDirection() {
        if (Objects.equals(ascending, Boolean.TRUE)) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
