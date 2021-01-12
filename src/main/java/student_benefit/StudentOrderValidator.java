package student_benefit;

public class StudentOrderValidator {
    public static void main(String[] args) {
        checkAll();
    }

    public static void checkAll() {
        checkCityRegister();
        checkMarriage();
        checkChildren();
        checkStudent();
    }

    public static void checkCityRegister() {
        System.out.println("CityRegister is running");
    }

    public static void checkMarriage() {
        System.out.println("Marriage is checking");
    }

    public static void checkChildren() {
        System.out.println("Children is checking");
    }

    public static void checkStudent() {
        System.out.println("Students is checking");
    }
}
