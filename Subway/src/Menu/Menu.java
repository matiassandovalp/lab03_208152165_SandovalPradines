package Menu;

import java.util.Scanner;

public class Menu{
    private Scanner input = new Scanner(System.in);
    /*
    private Subway subwayAdministrator = new Subway();

    public void show() {
        System.out.println("Bienvenido al lab3");
        System.out.println("Administración de metro");
        System.out.println("###################################");

        while(true) {
            boolean valid;
            int option;
            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Manipular estaciones");
                System.out.println("2) Manipular trenes");
                System.out.println("3) Manipular lineas");
                System.out.println("4) Salir");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 4) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            Submenu m = null;
            switch (option) {
                case 1 -> {
                    m = new StationMenu(subwayAdministrator);
                }
                case 2 -> {
                    m = new TrainMenu(subwayAdministrator);
                }
                case 3 -> {
                    m = new LineMenu(subwayAdministrator);
                }
                case 4 -> {
                    System.out.println("Saliendo");
                    return;
                }
            }
            m.show();
        }

    }*/
}
