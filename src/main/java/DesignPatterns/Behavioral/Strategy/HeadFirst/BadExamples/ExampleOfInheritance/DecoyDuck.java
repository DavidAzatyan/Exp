package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInheritance;

public class DecoyDuck extends Duck {

    @Override
    public void fly() {
        System.out.println("I can't fly");
    }

    @Override
    public void quack() {
        System.out.println("Silence");
    }

    @Override
    public void display() {
        System.out.println("I am a decoy duck");
    }
}
