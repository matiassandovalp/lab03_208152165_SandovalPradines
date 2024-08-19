import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private Scanner input = new Scanner(System.in);

    public void show() {
        System.out.println("Sistema Metro:");
        System.out.println("#############################");

        while (true) {
            boolean valid;
            int choice = 0; // Initialize choice variable
            do {
                System.out.println("1. Crear metro desde 0");
                System.out.println("2. Cargar datos de subway desde archivos");
                System.out.println("3. Salir");

                String line = input.nextLine();

                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    choice = 0;
                }

                if (choice < 1 || choice > 3) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            Subway subway = new Subway();

            switch (choice) {
                case 1:
                    Menu menu = new Menu();
                    menu.show();
                    break;
                case 2:
                    loadSubwayData(subway);
                    break;
                case 3:
                    System.out.println("Saliendo....");
                    return;
                default:
                    System.out.println("La elección introducida no es válida, intente nuevamente.");
            }
        }
    }

    private void loadSubwayData(Subway subway) {
        try (BufferedReader reader = new BufferedReader(new FileReader("lineas.txt"))){
            loadLinesFromFile(subway, "lineas.txt");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de líneas: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'lineas.txt'");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("secciones.txt"))){
            loadSectionsFromFile(subway, "secciones.txt");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de secciones: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'secciones.txt'");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("trenes.txt"))){
            loadTrainsFromFile(subway, "trenes.txt");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de trenes: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'trenes.txt'");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("conductores.txt"))){
            loadDriversFromFile(subway, "conductores.txt");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de conductores: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'conductores.txt'");
        }

        // After loading, transition to the regular Menu
        System.out.println("Archivos cargados con éxito, accediendo al menú de manipulación.........");

        Menu menu = new Menu();
        menu.show();
    }

    private void loadLinesFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String railType = parts[2];
                List<Section> sections = new ArrayList<>();

                subway.addLine(new Line(id, name, railType, sections));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de líneas: " + e.getMessage());
        }
    }

    private void loadPcarsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                int capacity = Integer.parseInt(parts[1]);
                String model = parts[2];
                String maker = parts[3];
                Pcar.CarType type = Pcar.CarType.valueOf(parts[4].toUpperCase());

                Pcar pcar = new Pcar(id, capacity, model, maker, type);
                subway.addPcar(pcar);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de carros de pasajeros: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'pcars.txt'");
        }
    }

    private void loadTrainsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String maker = parts[1];
                int speed = Integer.parseInt(parts[2]);
                int stayTime = Integer.parseInt(parts[3]);

                Train train = new Train(id, maker, speed, stayTime, new ArrayList<>());
                subway.addTrain(train);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de trenes: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'trenes.txt'");
        }
    }

    private void loadDriversFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String trainedMaker = parts[2];

                Driver driver = new Driver(id, name, trainedMaker);
                subway.addDriver(driver);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de conductores: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'conductores.txt'");
        }
    }

    private void loadSectionsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String startStation = parts[0];
                String endStation = parts[1];
                int time = Integer.parseInt(parts[2]);
                int cost = Integer.parseInt(parts[3]);

                Station start = findStationByName(subway, startStation);
                Station end = findStationByName(subway, endStation);

                if (start == null || end == null) {
                    System.out.println("Error: Una de las estaciones no existe.");
                    continue;
                }

                Section section = new Section(start, end, time, cost);
                subway.addSection(section);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de secciones: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'secciones.txt'");
        }
    }

    private Station findStationByName(Subway subway, String name) {
        for (Station station : subway.getStationList()) {
            if (station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }

    // Implement other load methods similarly
}
