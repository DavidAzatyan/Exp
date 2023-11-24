package DesignPatterns.Behavioral.Strategy.HeadFirst.Example;

public class Quack implements QuackBehavior{
    @Override
    public void quack(){
        System.out.println("quack");
    }
}
