package com.fyeeme.quasar.base.service;

import com.fyeeme.quasar.base.entity.AuditableEntity;
import com.fyeeme.quasar.base.entity.BaseEntity;
import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.repository.ResourceRepository;
import com.fyeeme.quasar.core.exception.BizException;
import com.fyeeme.quasar.core.exception.CommonError;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ResourceService<T extends BaseEntity, F extends QueryCondition> {

    String[] ignoreProperties = {"id", "createdBy", "createdDate"};

    default ResourceRepository<T> getRepository() {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default Logger getLogger() {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    default T create(T t) {
//        throw new BizException(CommonError.BAD_REQUEST);
        var savedEntity = getRepository().save(t);
        getLogger().info("New {} saved: {}", t.getClass().getSimpleName(), t);
        return savedEntity;
    }

    default T update(T t) {
        //        throw new BizException(CommonError.BAD_REQUEST);
        var existedEntity = get(t.getId());
        BeanUtils.copyProperties(t, existedEntity, extendedIgnoreProperties());

        //modifiedBy should be null, if we want to use JPA Auditable
        if (existedEntity instanceof AuditableEntity) {
            ((AuditableEntity<Long>) existedEntity).setModifiedBy(null);
        }
        var savedEntity = getRepository().save(existedEntity);
        getLogger().info("Existed {} saved: {}", t.getClass().getSimpleName(), t);
        return savedEntity;
    }

    default T get(Long id) {
        Optional<T> optional = getRepository().findById(id);
        return optional.orElseThrow(() -> new BizException(CommonError.BAD_REQUEST));
    }

    /**
     * https://stackoverflow.com/questions/299628/is-an-entity-body-allowed-for-an-http-delete-request
     * If a DELETE request includes an entity body, the body is ignored [...]
     *
     * @param id
     * @return T
     */
    default T delete(Long id) {
//        throw new BizException(CommonError.BAD_REQUEST);
        var t = get(id);
        getRepository().delete(t);
        getLogger().info("Existed {} saved: {}", t.getClass().getSimpleName(), t);
        return t;
    }

    //TODO  convert Page
    default Page<T> findAll(F query) {
        return getRepository().findAll(buildSpecs(query), toPageRequest(query));
    }

    default List<T> listAll(F query) {
        return getRepository().findAll(buildSpecs(query));
    }

    // another way of naming create method
//    T join(T t);
//
//    T quit(Long id);
//
//    T publish(Long id);
//
//    T undoPublish(Long id);
//
//    T hits(Long id);


    default String[] extendedIgnoreProperties() {
        throw new BizException(CommonError.BAD_REQUEST);
    }

    /**
     * used to extend default ignoreProperties
     * extendedProperties("sn", "commentCount")
     *
     * @param properties extended ignoreProperties
     * @return String[] ignoreProperties
     */
    default String[] extendedIgnoreProperties(String... properties) {
        if (Objects.isNull(properties)) {
            return ignoreProperties;
        }
        return Stream.of(ignoreProperties, properties).flatMap(Stream::of).toArray(String[]::new);
    }

    /**
     * 返回默认的Page对象: ID DESC
     */
    default Pageable toPageRequest(F f) {
        Sort sort = Sort.unsorted();
        if (!CollectionUtils.isEmpty(f.getSortOrders())) {
            sort = Sort.by(f.getSortOrders().stream()
                    .map(order -> Sort.Order.by(order.getField()).with(order.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC))
                    .collect(Collectors.toList())
            );
        }
        return PageRequest.of(f.getPage(), f.getPageSize(), sort);
    }

    default Specification<T> buildSpecs(F query) {
        throw new BizException(CommonError.BAD_REQUEST);
    }
}
