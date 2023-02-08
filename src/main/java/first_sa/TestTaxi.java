package first_sa;

public class TestTaxi {
    public static void main(String[] args) {
        Taxi taxi1 = new Taxi(1, 100);
        Taxi taxi2 = new Taxi(2, 100);

        taxi1.drive();
        taxi1.takePassenger(2, "서울역", 2);
        taxi1.changeGas(-80);
        taxi1.receiveFare();
        taxi1.changeVelocity(95);
        taxi1.takePassenger(5, "구디", 12);
        taxi1.takePassenger(1, "건대", 12);
        taxi1.changeGas(-20);
        taxi1.receiveFare();
    }
}
