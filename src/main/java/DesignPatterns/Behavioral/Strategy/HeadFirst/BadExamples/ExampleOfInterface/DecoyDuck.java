package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInterface;

public class DecoyDuck extends Duck {
    @Override
    void display() {
        System.out.println("I'm a decoy duck");
    }
}
