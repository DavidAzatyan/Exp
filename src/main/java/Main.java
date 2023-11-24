import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private static String configFile;
    private static String token;
    public static void main(String[] args) throws FileNotFoundException {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("/Users/dazatyan/IdeaProjects/Training/src/main/java/buffercopy.props"));
            token = prop.getProperty("token", token);
            System.out.println(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
