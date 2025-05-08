package hexlet.code.schemas;

public class StringSchema implements Schema<String> {

    private boolean required;
    private int minLength;
    private String containsString;

    public StringSchema required() {
        return this;
    }

    public StringSchema minLength(int minLength) {
        return this;
    }

    public StringSchema contains(String containsString) {
        return this;
    }

    @Override
    public boolean isValid(String s) {
        return false;
    }
}
