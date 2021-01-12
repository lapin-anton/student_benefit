package student_benefit;

public class StudentOrder {

    String hFirstName;
    String hLastName;
    String wFirstName;
    String wLastName;

    @Override
    public String toString() {
        return "StudentOrder{" +
                "hFirstName='" + hFirstName + '\'' +
                ", hLastName='" + hLastName + '\'' +
                ", wFirstName='" + wFirstName + '\'' +
                ", wLastName='" + wLastName + '\'' +
                '}';
    }
}
