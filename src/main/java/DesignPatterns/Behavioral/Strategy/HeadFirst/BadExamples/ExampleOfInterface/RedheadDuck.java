package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInterface;

public class RedheadDuck extends Duck implements Flyable, Quackable {
    @Override
    void display() {
        System.out.println("I'm a red head duck");
    }

    @Override
    public void fly() {
        System.out.println("I can fly");
    }

    @Override
    public void qauck() {
        System.out.println("quack");
    }
}
