package hexlet.code;

import hexlet.code.schemas.StringSchema;

public class App {

    public static void main(String[] args) {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        schema.required().minLength(5).contains("some");

        System.out.println("schema.isValid(\"something\"): " + schema.isValid("something"));
        System.out.println("schema.isValid(\"thing\"): " + schema.isValid("thing"));
    }
}
