package com.fyeeme.quasar.base.service;

import com.fyeeme.quasar.base.entity.BaseEntity;
import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceService<T extends BaseEntity, F extends QueryCondition> {
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

    // another way of naming create method
    // default T join(T t) {
    //     return null;
    // }

    // another way of naming delete method
    // default T quit(Long id) {
    //     return null;
    // }

    // some other operate of naming method
    // default T publish(Long id) {
    //     return null;
    // }

    // default T undoPublish(Long id) {
    //     return null;
    // }

    // default T hits(Long id) {
    //     return null;
    // }


    /**
     * 返回默认的Page对象: ID DESC
     */
    default Pageable toPageRequest(F f) {
        return PageRequest.of(f.getPage(), f.getPageSize());
    }
}
