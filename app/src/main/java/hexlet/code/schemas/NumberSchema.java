package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        return this;
    }

    public NumberSchema positive() {
        return this;
    }

    public NumberSchema range(int start, int end) {
        return this;
    }

    @Override
    public boolean isValid(Integer number) {
        return false;
    }
}
