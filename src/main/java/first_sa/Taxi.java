package first_sa;

public class Taxi implements Transportation{

    private Integer number;

    private Integer gasAmount;
    private Integer velocity = 0;
    private String destination;

    private Integer distance;
    private Integer primitiveFare = 3000;

    private Integer distancePerFare = 1000;

    private Integer passenger = 0;

    private Integer maxPassenger = 4;

    private State state = State.운행불가;

    /////////////////////////////// 생성자 ///////////////////////////
    public Taxi(Integer number, Integer gasAmount) {
        this.number = number;
        this.gasAmount = gasAmount;
        System.out.println(number + "번 택시 생성\n");
    }
    ///////////////////////////비즈니스 로직////////////////////////////////
    public void takePassenger(Integer passenger, String destination, Integer distance) {
        if(this.state != State.일반){
            System.out.println("탑승 불가\n");
            return;
        }
        if(this.passenger + passenger > 4){
            System.out.println("탑승 시도 승객 수 = " + passenger);
            System.out.println("최대 승객 수 초과\n");
            return;
        }
        this.passenger = passenger;
        this.destination = destination;
        this.distance = distance;
        this.state = State.운행중;

        System.out.println("탑승 승객 수 = " + passenger);
        System.out.println("잔여 승객 수 = " + (maxPassenger-passenger));
        System.out.println("기본 요금 확인 = " + primitiveFare);
        System.out.println("목적지 = " + destination);
        System.out.println("목적지까지 거리 = " + distance);
        System.out.println("지불할 요금 = " + (primitiveFare + distancePerFare*distance));
        System.out.println("상태 = " + state + "\n");
    }

    @Override
    public void changeGas(Integer gas) {
        this.gasAmount += gas;
        if(gasAmount < 10)
            System.out.println("주유가 필요합니다.\n");
        if(gas < 0)
            this.state = State.차고지행;
    }

    @Override
    public void changeVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public void receiveFare(){
        System.out.println("주유량 : " + gasAmount);
        if(gasAmount <= 0){
            state = State.운행불가;
            System.out.println("상태 = " + state);
        }
        System.out.println("누적 요금 = "  + (primitiveFare + distancePerFare * distance) + "\n");
        this.state = State.일반;
    }

    @Override
    public void drive() {
        if(gasAmount >= 10){
            this.state = State.일반;
            System.out.println("주유량 : " + gasAmount);
            System.out.println("상태 : " + state + "\n");
        }
        else{
            System.out.println("기름이 부족합니다.\n");
        }
    }

    @Override
    public void end(){
        state = State.차고지행;
    }

    //////////////////////////////// getter만 열어둠 /////////////////////////////////

    public Integer getNumber() {
        return number;
    }

    public Integer getGasAmount() {
        return gasAmount;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getPrimitiveFare() {
        return primitiveFare;
    }

    public Integer getDistancePerFare() {
        return distancePerFare;
    }

    public Integer getPassenger() {
        return passenger;
    }

    public Integer getMaxPassenger() {
        return maxPassenger;
    }

    public State getState() {
        return state;
    }
}
