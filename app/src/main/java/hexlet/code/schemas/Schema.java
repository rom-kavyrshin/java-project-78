package hexlet.code.schemas;

public interface Schema<T> {
    boolean isValid(T t);
}
