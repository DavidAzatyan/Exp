package DesignPatterns.Behavioral.Strategy.HeadFirst.Example;

public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Squeak");
    }
}
