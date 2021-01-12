package student_benefit;

public class StudentOrderValidator {
    public static void main(String[] args) {
        checkAll();
    }

    public static void checkAll() {
        StudentOrder order = readStudentOrder();
        AnswerCityRegister answerCityRegister = checkCityRegister(order);
        AnswerMarriage answerMarriage = checkMarriage(order);
        AnswerChildren answerChildren = checkChildren(order);
        AnswerStudent answerStudent = checkStudent(order);

        sendMail(order);
    }

    private static void sendMail(StudentOrder order) {

    }

    private static StudentOrder readStudentOrder() {
        return new StudentOrder();
    }

    public static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        System.out.println("CityRegister is running");
        return new AnswerCityRegister();
    }

    public static AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        System.out.println("Marriage is checking");
        return new AnswerMarriage();
    }

    public static AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("Children is checking");
        return new AnswerChildren();
    }

    public static AnswerStudent checkStudent(StudentOrder studentOrder) {
        System.out.println("Students is checking");
        return new AnswerStudent();
    }
}
