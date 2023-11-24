package DesignPatterns.Behavioral.Strategy.HeadFirst.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniDuckSimulator {
    public static void main(String[] args) {
        List<Duck> ducks = new ArrayList<>(Arrays.asList(new DecoyDuck(),new MallardDuck(),new RedheadDuck(), new RubberDuck()));
        for (Duck duck : ducks){
            duck.display();
            duck.performFly();
            duck.performQuack();
            duck.swim();
            System.out.println("_______________________________");
        }
    }
}
