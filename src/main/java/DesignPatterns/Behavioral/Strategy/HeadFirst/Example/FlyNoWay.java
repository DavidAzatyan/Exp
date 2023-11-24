package DesignPatterns.Behavioral.Strategy.HeadFirst.Example;

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I can't fly");
    }
}
