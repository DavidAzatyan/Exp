package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniDuckSimulator {
    public static void main(String[] args) {
        List<Duck> ducks = new ArrayList<>(Arrays.asList(new DecoyDuck(),new MallardDuck(), new RubberDuck(), new RedheadDuck()));
        for(Duck duck : ducks){
            duck.display();
            duck.swim();
            System.out.println("___________________");
        }
        List<Flyable> flayableDucks = new ArrayList<>(Arrays.asList(new MallardDuck(),new RedheadDuck()));
        for (Flyable duck : flayableDucks){
            duck.fly();
        }
        List<Quackable> quackableDucks = new ArrayList<>(Arrays.asList(new MallardDuck(),new RedheadDuck()));
        for (Quackable duck : quackableDucks){
            duck.qauck();
        }
    }
}
