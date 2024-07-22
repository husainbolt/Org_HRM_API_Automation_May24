package utility;

import net.datafaker.Faker;

import java.util.UUID;

public class DataGenerator {

    private static Faker faker = new Faker();

    public static String generateFullName(){

        return faker.name().firstName() +" "+ faker.name().lastName();
    }

    public static String generateFirstName(){

        return faker.name().firstName();
    }

    public static String generateLastName(){

        return faker.name().lastName();
    }

    public static String generateMiddleName(){

        return faker.name().firstName();
    }

    public static String generateJoinedDate(){

        return faker.date().birthday("yyyy-MM-dd");
    }

    public static String generatePhoneNUmber(){
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateEmail(){
        return faker.name().firstName()+"."+faker.name().lastName()+"@test.com";
    }

    public static String generatePassword(){
        return faker.name().firstName();
    }

    public static int generateAge(){
        return Integer.parseInt(faker.number().digits(2));
    }

    public static boolean generateBooleanValue(){
        return faker.bool().bool();
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getSkillName(){
        return faker.programmingLanguage().name();
    }

    public static String getSkillDescription(){
        return faker.text().text(10);
    }

}
