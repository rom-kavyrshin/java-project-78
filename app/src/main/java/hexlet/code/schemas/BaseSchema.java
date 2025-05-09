package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    public abstract boolean isValid(T t);
}
