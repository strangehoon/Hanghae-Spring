package first_sa;

public class Bus implements Transportation{
    private Integer maxPassenger;
    private Integer passenger;
    private Integer fare;
    private Integer number = 0;
    private Integer gasAmount;
    private Integer velocity;
    private State state;

    /////////////////////////////// 생성자 ///////////////////////////
    public Bus(Integer maxPassenger, Integer fare, Integer gasAmount, Integer velocity, Integer number) {
        this.maxPassenger = maxPassenger;
        this.passenger = 0;
        this.fare = fare;
        this.gasAmount = gasAmount;
        this.velocity = velocity;
        this.state = State.운행중;
        this.number = number;
        System.out.println(number + "번 버스객체 만들어짐!\n");
    }

    ///////////////////////////비즈니스 로직////////////////////////////////

    public void takePassenger(Integer passenger) {
        if(this.state == State.차고지행){
            System.out.println("운행 중이 아닙니다.");
            return;
        }
        if(this.passenger + passenger > maxPassenger){
            System.out.println("최대 승객 수 초과\n");
            return;
        }
        this.passenger += passenger;
        System.out.println("탐승 승객 수 = " + passenger);
        System.out.println("잔여 승객 수 = " + (maxPassenger - this.passenger));
        System.out.println("요금 확인 = " + fare + "\n");
    }

    @Override
    public void changeGas(Integer gas) {
        this.gasAmount += gas;
        System.out.println("주유량 : " + gasAmount);
        if(gasAmount < 10)
            System.out.println("주유가 필요합니다.");
        System.out.print("\n");
    }

    @Override
    public void changeVelocity(Integer velocity) {
        this.velocity += velocity;
    }

    @Override
    public void drive() {
        this.state = State.운행중;
        System.out.println("상태 : " + state + "\n");
    }

    @Override
    public void end() {
        this.state = State.차고지행;
        System.out.println("운행 종료 \n");
    }

    //////////////////////////////// getter만 열어둠 /////////////////////////////////
    public Integer getMaxPassenger() {
        return maxPassenger;
    }
    public Integer getPassenger() {
        return passenger;
    }
    public Integer getFare() {
        return fare;
    }
    public Integer getNumber() {
        return number;
    }
    public Integer getGasAmount() {
        return gasAmount;
    }
    public Integer getVelocity() {
        return velocity;
    }
    public State getState() {
        return state;
    }
}
