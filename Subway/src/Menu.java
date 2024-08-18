import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Scanner input = new Scanner(System.in);
    private Subway subwayAdministrator = new Subway();

    public void show() {
        System.out.println("Bienvenido al lab3");
        System.out.println("Administración de metro");
        System.out.println("###################################");

        while (true) {
            boolean valid;
            int option;
            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Manipular estaciones");
                System.out.println("2) Manipular trenes");
                System.out.println("3) Manipular líneas");
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
                case 1 -> m = new StationMenu(subwayAdministrator);
                case 2 -> m = new TrainMenu(subwayAdministrator);
                case 3 -> m = new LineMenu(subwayAdministrator);
                case 4 -> {
                    System.out.println("Saliendo...");
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }

    public abstract static class Submenu {
        protected Subway subwayAdministrator;
        protected Scanner input = new Scanner(System.in);

        public Submenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        public abstract void show();
    }

    public class LineMenu extends Submenu {

        public LineMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de líneas");
            boolean valid;
            int option;
            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear línea");
                System.out.println("2) Agregar sección");
                System.out.println("3) Mostrar líneas");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 3) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            ActionMenu m = null;
            switch (option) {
                case 1 -> m = new CreateLineMenu(subwayAdministrator);
                case 2 -> m = new AddSectionMenu(subwayAdministrator);
                case 3 -> {
                    System.out.println("Líneas actualmente creadas:");
                    for (Line l : subwayAdministrator.getLineList()) {
                        System.out.println(l.toString());
                    }
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }

    public interface ActionMenu {
        void show();
    }

    public class CreateLineMenu implements ActionMenu {

        private Scanner input = new Scanner(System.in);
        private Subway subwayAdministrator;

        public CreateLineMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        public void show() {
            System.out.println("#############################");
            System.out.println("#############################");
            System.out.println("Menu de creación de líneas");

            System.out.print("Ingrese el id de la línea: ");
            String line = input.nextLine();
            int id;
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("El id ingresado no es válido");
                return;
            }

            System.out.print("Ingrese el nombre de la línea: ");
            line = input.nextLine();
            if (line.isBlank()) {
                System.out.println("El nombre ingresado no es válido");
                return;
            }
            String name = line;

            System.out.print("Ingrese el tipo de riel: ");
            line = input.nextLine();
            if (line.isBlank()) {
                System.out.println("El tipo de riel ingresado no es válido");
                return;
            }
            String railType = line;

            List<Section> sections = new ArrayList<>();

            Line newLine = new Line(id, name, railType, sections);
            
            subwayAdministrator.addLine(newLine);
            System.out.println("Se creó la línea");
        }
    }

    public class AddSectionMenu implements ActionMenu {

        private Subway subwayAdministrator;

        public AddSectionMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de secciones de líneas");
            // Aquí agregar la lógica para pedir datos de la sección y el id de la línea
        }
    }

    public class StationMenu extends Submenu {

        public StationMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("Menu de estaciones");
            // Lógica para manipular estaciones
        }
    }

    public class TrainMenu extends Submenu {

        public TrainMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("Menu de trenes");
            // Lógica para manipular trenes
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.show();
    }
}
