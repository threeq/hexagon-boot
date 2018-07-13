package com.hexagon.boot.adapter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;


public abstract class Condition<T> {

    protected final String expression;
    protected final T value;

    Condition(String expression, T value) {
        this.expression = expression;
        this.value = value;
    }
    Condition(T value) {
        this.expression = null;
        this.value = value;
    }

    public static Predicate predicate(Root root, CriteriaBuilder criteriaBuilder, Condition condition) {
        return condition.predicate(root, criteriaBuilder);
    }

    abstract Predicate predicate(Root root, CriteriaBuilder criteriaBuilder);
}
class Eq extends Condition<Object> {

    Eq(String expression, Object value) {
        super(expression, value);
    }

    @Override
    Predicate predicate(Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.<String>get(this.expression), this.value);
    }
}
class And extends Condition<Condition[]> {
    public And(Condition[] value) {
        super(value);
    }

    @Override
    Predicate predicate(Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                Arrays.stream(value).
                        map(it->it.predicate(root, criteriaBuilder))
                        .toArray((Predicate[]::new)));
    }
}

class Or extends Condition<Condition[]> {
    public Or(Condition[] value) {
        super(value);
    }

    @Override
    Predicate predicate(Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                Arrays.stream(value).
                        map(it->it.predicate(root, criteriaBuilder))
                        .toArray((Predicate[]::new)));
    }
}
