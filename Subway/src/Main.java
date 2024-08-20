import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        System.out.println("Debug: Current working directory: " + System.getProperty("user.dir"));
        System.out.println("##################\n\n\n");
        MainMenu menu = new MainMenu();
        menu.show();

    }
}
