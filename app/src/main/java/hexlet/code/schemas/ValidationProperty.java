package hexlet.code.schemas;

import java.util.function.BiPredicate;

public final class ValidationProperty<T, U> {

    private final T validationCriteria;
    private final BiPredicate<T, U> predicate;

    public ValidationProperty(T criteria, BiPredicate<T, U> validator) {
        this.validationCriteria = criteria;
        this.predicate = validator;
    }

    public boolean test(U u) {
        return predicate.test(validationCriteria, u);
    }
}
