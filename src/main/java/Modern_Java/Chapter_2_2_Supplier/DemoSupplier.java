package Modern_Java.Chapter_2_2_Supplier;

import Modern_Java.Chapter_1_1_Lambda_Expressions.Flyable;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class DemoSupplier {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("...");

        Supplier<Logger> loggerSupplier = new Supplier<Logger>() {
            @Override
            public Logger get() {
                return logger;
            }
        };

        System.out.println(loggerSupplier.get());

        DoubleSupplier doubleSupplier = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return Math.random();
            }
        };
        doubleSupplier = () -> Math.random();
        doubleSupplier = Math::random;

        IntSupplier intSupplier = new IntSupplier() {
            @Override
            public int getAsInt() {
                return (int) (Math.random() * 10);
            }
        };

        intSupplier = () -> (int) (Math.random() * 10);
        System.out.println(intSupplier.getAsInt());
    }
}
