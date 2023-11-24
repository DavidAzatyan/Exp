package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInheritance;

public class MallardDuck extends Duck {
    @Override
    public void fly() {
        System.out.println("I'm flying");
    }

    @Override
    public void quack() {
        System.out.println("quack");
    }

    @Override
    public void display() {
        System.out.println("I'm a mallard duck");
    }
}
