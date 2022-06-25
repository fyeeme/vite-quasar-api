package com.fyeeme.quasar.base.repository.suppport;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.enums.QueryOperationEnum;
import com.fyeeme.quasar.core.exception.BizException;
import com.fyeeme.quasar.core.exception.CommonError;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * this class is used to build all the queries and passed as parameters.
 * andConditions(filter list for the AND operator)
 * orConditions(filter list for the OR operator)
 * joinConditions(filter list for the JOIN AND operator)
 */
public class GenericSpecificationBuilder {

    public static <T> Specification<T> buildSpecs(QueryCondition filterQuery, Class<T> clazz) {
        Specification<T> andSpecs = (root, query, builder) -> {
            var andConditions = Optional.ofNullable(filterQuery.getAndConditions()).orElse(Collections.emptyList());
            var andPredicates = andConditions.stream().map(condition -> addFieldPredicates(condition, builder, root)).toList();

            return buildPredicates(builder, andPredicates);
        };

        Specification<T> joinSpecs = (root, query, builder) -> {
            var joinConditions = Optional.ofNullable(filterQuery.getJoinConditions()).orElse(Collections.emptyList());
            var joinPredicates = joinConditions.stream().map(condition -> addJoinPredicates(condition, builder, root)).toList();

            return buildPredicates(builder, joinPredicates);
        };

        Specification<T> orSpecs = (root, query, builder) -> {
            var orConditions = Optional.ofNullable(filterQuery.getOrConditions()).orElse(Collections.emptyList());
            var orPredicates = orConditions.stream().map(condition -> addFieldPredicates(condition, builder, root)).toList();

            return buildPredicates(builder, orPredicates);
        };

        return andSpecs.and(joinSpecs).or(orSpecs);
    }

    private static Predicate buildPredicates(CriteriaBuilder builder, List<Predicate> predicates) {
        //fixme return null will omit 1=1 on where, which is necessary when use or-condition.
        return CollectionUtils.isEmpty(predicates) ? null : builder.and(predicates.toArray(new Predicate[0]));
    }

    private static <T> Predicate addJoinPredicates(QueryCondition.JoinFieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var fieldCondition = condition.getCondition();
        var joinEntity = root.join(condition.getJoinField());
        var expression = joinEntity.get(fieldCondition.getField());
        return addFieldPredicate(fieldCondition, builder, expression);
    }

    private static <T> Predicate addFieldPredicates(QueryCondition.FieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var expression = root.get(condition.getField());
        return addFieldPredicate(condition, builder, expression);
    }

    private static Predicate addFieldPredicate(QueryCondition.FieldCondition condition, CriteriaBuilder builder, Path expression) {
        var operator = QueryOperationEnum.fromValue(condition.getOperator());

        return switch (operator) {
            case EQUAL -> builder.equal(expression, condition.getValue());
            case LIKE -> builder.like(expression, "%" + condition.getValue() + "%");
            //Cannot cast java.lang.Integer to java.lang.Long"
            case IN -> builder.in(expression).value(((List) condition.getValue()).stream().map(val ->
                    val instanceof Number ? ((Number)val).longValue() : val).toList());
//            case IN -> builder.in(expression).value(condition.getValue());
            case LESS_THAN -> builder.lessThan(expression, (Comparable) condition.getValue());
            case LESS_THAN_OR_EQUAL_TO -> builder.lessThanOrEqualTo(expression, (Comparable) condition.getValue());
            case GREATER_THAN -> builder.greaterThan(expression, (Comparable) condition.getValue());
            case GREATER_THAN_OR_EQUAL_TO -> builder.greaterThanOrEqualTo(expression, (Comparable) condition.getValue());
            case NOT_EQUAL -> builder.notEqual(expression, condition.getValue());
            case IS_NULL -> builder.isNull(expression);
            case IS_NOT_NULL -> builder.isNotNull(expression);
            default -> throw new BizException(CommonError.BAD_REQUEST, condition.getOperator() + "is not a valid operate");
        };
    }

}
