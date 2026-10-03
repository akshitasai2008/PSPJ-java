import java.util.Scanner;

public class WaterBill {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter water consumption in litres: ");
        double consumption = sc.nextDouble();

        if (consumption <= 500) {
            System.out.println("Water Bill: Rs.100");
        } else {
            System.out.println("Water Bill: Rs.200");
            sc.close();

        }
    }
}