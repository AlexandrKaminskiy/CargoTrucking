package by.singularity.repository.queryUtils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public <T> QPredicate add(T object, Function<T, Predicate> predicate) {
        if (object != null) {
            predicates.add(predicate.apply(object));
        }
        return this;
    }

    public static QPredicate builder() {
        return new QPredicate();
    }

    public Predicate buildAnd() {
        Predicate predicate = ExpressionUtils.allOf(predicates);
        if (predicate == null) {
            predicate = Expressions.TRUE.isTrue();
        }
        return predicate;
    }

    public Predicate buildOr() {
        Predicate predicate = ExpressionUtils.anyOf(predicates);
        if (predicate == null) {
            predicate = Expressions.TRUE.isTrue();
        }
        return predicate;
    }

}
