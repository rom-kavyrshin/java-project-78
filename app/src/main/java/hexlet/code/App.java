package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public class App {

    public static void main(String[] args) {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        NumberSchema numberSchema = validator.number();

        stringSchema.required().minLength(5).contains("some");
        numberSchema.required().positive().range(5, 10);

        System.out.println("stringSchema.isValid(\"something\"): " + stringSchema.isValid("something"));
        System.out.println("numberSchema.isValid(7): " + numberSchema.isValid(7));
    }
}
