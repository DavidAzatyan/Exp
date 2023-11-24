package DesignPatterns.Behavioral.Strategy.HeadFirst.Example;

public class RubberDuck extends Duck {

    public RubberDuck(){
        setFlyBehavior(new FlyNoWay());
        setQuackBehavior(new Squeak());
    }
    @Override
    public void display() {
        System.out.println("I am a rubber duck");
    }
}
