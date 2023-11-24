package DesignPatterns.Behavioral.Strategy.HeadFirst.BadExamples.ExampleOfInheritance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniDuckSimulator {
    public static void main(String[] args) {
        List<Duck> ducks = new ArrayList<>(Arrays.asList(new DecoyDuck(),new RubberDuck(),new MallardDuck(),new RedheadDuck()));
        for (Duck duck : ducks){
            duck.display();
            duck.fly();
            duck.quack();
            duck.swim();
        }
    }
}
