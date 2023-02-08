package first_sa;

// 운송 수단 인터페이스
interface Transportation {
    void changeGas(Integer gas);

    void changeVelocity(Integer velocity);

    void drive();
    void end();

}
