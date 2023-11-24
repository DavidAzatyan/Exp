package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInterface;

public abstract class Duck {

    public Duck() {
    }
    public void swim(){
        System.out.println("All ducks float, even decoys!");
    }
    abstract void display();
}
