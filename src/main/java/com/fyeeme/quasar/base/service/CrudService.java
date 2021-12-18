package com.fyeeme.quasar.base.service;

import com.fyeeme.quasar.base.entity.BaseEntity;
import com.fyeeme.quasar.base.entity.BaseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CrudService<T extends BaseEntity, F extends BaseFilter> {
    default T create(T t) {
        return null;
    }

    default T update(T t) {
        return null;
    }

    default T get(Long id) {
        return null;
    }

    /**
     * https://stackoverflow.com/questions/299628/is-an-entity-body-allowed-for-an-http-delete-request
     * If a DELETE request includes an entity body, the body is ignored [...]
     *
     * @param id
     * @return T t
     */
    default T delete(Long id) {
        return null;
    }

    default Page<T> findAll(F filter) {
        return null;
    }

    default List<T> listAll(F filter) {
        return null;
    }

    /**
     * 返回默认的Page对象: ID DESC
     */
    default Pageable toPageRequest(F f) {
        return PageRequest.of(f.getPage(), f.getPageSize(), f.getSortDirection(), f.getSortField());
    }
}
