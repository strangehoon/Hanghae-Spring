package first_sa;
public class TestBus {
    public static void main(String[] args) {
        Bus bus1 = new Bus(30, 1500, 100, 70, 1);
        Bus bus2 = new Bus(30, 1500, 100, 70, 2);

        bus1.takePassenger(2);
        bus1.changeVelocity(100);
        bus1.changeGas(-50);
        bus1.changeGas(10);
        bus1.takePassenger(45);
        bus1.takePassenger(5);
        bus1.changeGas(-55);
        bus1.end();
    }
}
