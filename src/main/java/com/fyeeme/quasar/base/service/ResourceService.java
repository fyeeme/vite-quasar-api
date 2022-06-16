package com.fyeeme.quasar.base.service;

import com.fyeeme.quasar.base.entity.BaseEntity;
import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.core.exception.BizException;
import com.fyeeme.quasar.core.exception.CommonError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;

public interface ResourceService<T extends BaseEntity, F extends QueryCondition> {
    default T create(T t) {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default T update(T t) {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default T get(Long id) {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    /**
     * https://stackoverflow.com/questions/299628/is-an-entity-body-allowed-for-an-http-delete-request
     * If a DELETE request includes an entity body, the body is ignored [...]
     *
     * @param id
     * @return T
     */
    default T delete(Long id) {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default Page<T> findAll(F query) {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default List<T> listAll(F query) {
        throw new BizException(CommonError.BAD_REQUEST);
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
        Sort sort = Sort.unsorted();
        if (!CollectionUtils.isEmpty(f.getOrConditions())) {
            sort = Sort.by(f.getSortOrders().stream()
                    .map(order -> Sort.Order.by(order.getField()).with(order.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC))
                    .toList()
            );
        }
        return PageRequest.of(f.getPage(), f.getPageSize(), sort);
    }
}
