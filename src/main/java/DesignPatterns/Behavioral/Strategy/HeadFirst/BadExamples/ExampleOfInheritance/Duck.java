package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInheritance;

public abstract class Duck {
    public Duck(){

    }
    public abstract void fly();
    public abstract void quack();
    public void swim(){
        System.out.println("All ducks float, even decoys!");
    }
    public abstract void display();
}
