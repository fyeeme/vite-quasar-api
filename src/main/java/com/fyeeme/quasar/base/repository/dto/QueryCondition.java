package com.fyeeme.quasar.base.repository.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class QueryCondition implements Serializable {
    private Integer page = 0;
    private Integer pageSize = 10;
    private List<SortOrder> sortOrders;

    private List<FieldCondition> andConditions;
    private List<FieldCondition> orConditions;
    private List<JoinFieldCondition> joinConditions;

    public Integer getPage() {
        return page > 1 ? page - 1 : 0;
    }

    /**
     * 分页排序
     */
    @Data
    @RequiredArgsConstructor
    private static class SortOrder {
        private String field;
        private Boolean ascending;
    }
}
