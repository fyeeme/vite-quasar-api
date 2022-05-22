package com.fyeeme.quasar.base.repository.suppport;

import com.fyeeme.quasar.base.repository.dto.FieldCondition;
import com.fyeeme.quasar.base.repository.dto.JoinFieldCondition;
import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;

/**
 * https://self-learning-java-tutorial.blogspot.com/2020/08/spring-jpa-specification-to-join-tables.html
 */
public class GenericSpecificationBuilder {

    public static <T> Specification<T> buildSpecs(QueryCondition filterQuery, Class<T> clazz) {
        var andSpecs = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                var andConditions = filterQuery.getAndConditions();

                var andPredicates = new ArrayList<Predicate>();
                if (!CollectionUtils.isEmpty(andConditions)) {
                    andConditions.forEach(condition -> {
                        addFieldPredicates(andPredicates, condition, builder, root);
                    });
                }
                if (CollectionUtils.isEmpty(andPredicates)) {
                    return builder.conjunction();
                }
                return builder.and(andPredicates.toArray(new Predicate[0]));
            }
        };

        var joinSpecs = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                var joinConditions = filterQuery.getJoinConditions();
                var joinPredicates = new ArrayList<Predicate>();

                if (!CollectionUtils.isEmpty(joinConditions)) {
                    joinConditions.forEach(condition -> {
                        addJoinPredicates(joinPredicates, condition, builder, root);
                    });
                }
                if (CollectionUtils.isEmpty(joinPredicates)) {
                    return builder.conjunction();
                }

                return builder.and(joinPredicates.toArray(new Predicate[0]));
            }
        };

        var orSpecs = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                var orConditions = filterQuery.getOrConditions();
                var orPredicates = new ArrayList<Predicate>();

                if (!CollectionUtils.isEmpty(orConditions)) {
                    orConditions.forEach(condition -> {
                        addFieldPredicates(orPredicates, condition, builder, root);
                    });
                }
                if (CollectionUtils.isEmpty(orPredicates)) {
                    return builder.conjunction();
                }

                return builder.and(orPredicates.toArray(new Predicate[0]));
            }
        };
        return joinSpecs.and(andSpecs).or(orSpecs);
    }

    private static <T> void addJoinPredicates(ArrayList<Predicate> predicates, JoinFieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var fieldCondition = condition.getCondition();
        var joinEntity = root.join(condition.getJoinField());
        var expression = joinEntity.get(fieldCondition.getField());
        addFieldPredicate(predicates, fieldCondition, builder, expression);
    }

    private static <T> void addFieldPredicates(ArrayList<Predicate> predicates, FieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var expression = root.get(condition.getField());
        addFieldPredicate(predicates, condition, builder, expression);
    }

    private static void addFieldPredicate(ArrayList<Predicate> predicates, FieldCondition condition, CriteriaBuilder builder, Path expression) {
        switch (condition.getOperator()) {
            case EQUAL -> predicates.add(builder.equal(expression, condition.getValue()));
            case LIKE -> predicates.add(builder.like(expression, "%" + condition.getValue() + "%"));
            case IN -> predicates.add(builder.in(expression).value(condition.getValue()));
            case LESS_THAN -> predicates.add(builder.lessThan(expression, (Comparable) condition.getValue()));
            case LESSTHAN_OR_EQUAL_TO -> predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) condition.getValue()));
            case GREATER_THAN -> predicates.add(builder.greaterThan(expression, (Comparable) condition.getValue()));
            case GREATER_THAN_OR_EQUAL_TO -> predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) condition.getValue()));
            case NOT_EQUAL -> predicates.add(builder.notEqual(expression, condition.getValue()));
            case IS_NULL -> predicates.add(builder.isNull(expression));
            case IS_NOT_NULL -> predicates.add(builder.isNotNull(expression));
            default -> new IllegalArgumentException(condition.getOperator() + "is not a valid operate");
        }
    }

}
