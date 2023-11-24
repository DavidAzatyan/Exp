package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInterface;

public class RubberDuck extends Duck implements Quackable{
    @Override
    void display() {
        System.out.println("I'm a rubber Duck");
    }

    @Override
    public void qauck() {
        System.out.println();
    }
}
