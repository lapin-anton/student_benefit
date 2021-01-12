package student_benefit;

public class StudentOrderValidator {
    public static void main(String[] args) {
        checkAll();
    }

    public static void checkAll() {
        while(true) {
            StudentOrder order = readStudentOrder();
            if (order == null) break;
            AnswerCityRegister answerCityRegister = checkCityRegister(order);
            if(!answerCityRegister.success) continue;
            AnswerMarriage answerMarriage = checkMarriage(order);
            AnswerChildren answerChildren = checkChildren(order);
            AnswerStudent answerStudent = checkStudent(order);
            sendMail(order);
        }
    }

    private static void sendMail(StudentOrder order) {
        System.out.println("Почта отправлена");
    }

    private static StudentOrder readStudentOrder() {
        return new StudentOrder();
    }

    public static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        CityRegisterValidator crv = new CityRegisterValidator();
        crv.hostName = "hostName";
        crv.login = "Login";
        crv.password = "password";
        return crv.checkCityRegister(studentOrder);
    }

    public static AnswerStudent checkStudent(StudentOrder studentOrder) {
        StudentValidator sv = new StudentValidator();
        return sv.checkStudent(studentOrder);
    }

    public static AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        MarriageValidator mv = new MarriageValidator();
        return mv.checkMarriage(studentOrder);
    }

    public static AnswerChildren checkChildren(StudentOrder studentOrder) {
        ChildrenValidator cv = new ChildrenValidator();
        return cv.checkChildren(studentOrder);
    }
}
