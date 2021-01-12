package student_benefit;

public class FinanceStudentOrder {
    public static void main(String[] args) {
        getStudentOrder();
        handleStudentOrder();
        sendStudentOrderToFinDept();
    }

    private static void handleStudentOrder() {
        System.out.println("Handle order...");
    }

    public static void getStudentOrder() {
        System.out.println("Getting of order");
    }

    public static void sendStudentOrderToFinDept() {
        System.out.println("order sending");
    }
}
