package com.fyeeme.quasar.base.repository.suppport;

import com.fyeeme.quasar.base.enums.QueryOperationEnum;
import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.core.exception.BizException;
import com.fyeeme.quasar.core.exception.CommonError;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

/**
 * https://self-learning-java-tutorial.blogspot.com/2020/08/spring-jpa-specification-to-join-tables.html
 */
public class ResourceSpecificationBuilder {

    public static <T> Specification<T> buildSpecs(QueryCondition filterQuery, Class<T> clazz) {
        Specification<T> andSpecs = (root, query, builder) -> {
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
        };

        Specification<T> joinSpecs = (root, query, builder) -> {
            var joinConditions = filterQuery.getJoinConditions();
            var joinPredicates = new ArrayList<Predicate>();

            if (!CollectionUtils.isEmpty(joinConditions)) {
                joinConditions.forEach(condition -> {
                    addJoinPredicates(joinPredicates, condition, builder, root);
                });
            }
            if (CollectionUtils.isEmpty(joinPredicates)) {
                return null;//builder.conjunction();
            }

            return builder.and(joinPredicates.toArray(new Predicate[0]));
        };

        Specification<T> orSpecs = (root, query, builder) -> {
            var orConditions = filterQuery.getOrConditions();
            var orPredicates = new ArrayList<Predicate>();

            if (!CollectionUtils.isEmpty(orConditions)) {
                orConditions.forEach(condition -> {
                    addFieldPredicates(orPredicates, condition, builder, root);
                });
            }
            if (CollectionUtils.isEmpty(orPredicates)) {
                return null;
            }

            return builder.and(orPredicates.toArray(new Predicate[0]));
        };
        return andSpecs.and(joinSpecs).or(orSpecs);
    }

    private static <T> void addJoinPredicates(ArrayList<Predicate> predicates, QueryCondition.JoinFieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var fieldCondition = condition.getCondition();
        var joinEntity = root.join(condition.getJoinField());
        var expression = joinEntity.get(fieldCondition.getField());
        addFieldPredicate(predicates, fieldCondition, builder, expression);
    }

    private static <T> void addFieldPredicates(ArrayList<Predicate> predicates, QueryCondition.FieldCondition condition, CriteriaBuilder builder, Root<T> root) {
        var expression = root.get(condition.getField());
        addFieldPredicate(predicates, condition, builder, expression);
    }

    private static void addFieldPredicate(ArrayList<Predicate> predicates, QueryCondition.FieldCondition condition, CriteriaBuilder builder, Path expression) {
        var operator = QueryOperationEnum.fromValue(condition.getOperator());

        switch (operator) {
            case EQUAL -> predicates.add(builder.equal(expression, condition.getValue()));
            case LIKE -> predicates.add(builder.like(expression, "%" + condition.getValue() + "%"));
            case IN -> predicates.add(builder.in(expression).value(condition.getValue()));
            case LESS_THAN -> predicates.add(builder.lessThan(expression, (Comparable) condition.getValue()));
            case LESS_THAN_OR_EQUAL_TO -> predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) condition.getValue()));
            case GREATER_THAN -> predicates.add(builder.greaterThan(expression, (Comparable) condition.getValue()));
            case GREATER_THAN_OR_EQUAL_TO -> predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) condition.getValue()));
            case NOT_EQUAL -> predicates.add(builder.notEqual(expression, condition.getValue()));
            case IS_NULL -> predicates.add(builder.isNull(expression));
            case IS_NOT_NULL -> predicates.add(builder.isNotNull(expression));
            default -> throw new BizException(CommonError.BAD_REQUEST, condition.getOperator() + "is not a valid operate");
        }
    }

}
