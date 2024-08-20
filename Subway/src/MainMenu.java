import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private Scanner input = new Scanner(System.in);
    private Subway subway;

    public MainMenu() {
        this.subway = new Subway();
    }

    public void show() {
        System.out.println("#############################");
        System.out.println("Sistema Metro:");
        System.out.println("#############################");

        while (true) {
            boolean valid;
            int choice = 0;
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
                    Menu menu = new Menu(subway);
                    menu.show();
                    break;
                case 2:
                    loadSubwayData();
                    break;
                case 3:
                    System.out.println("Saliendo....");
                    return;
                default:
                    System.out.println("La elección introducida no es válida, intente nuevamente.");
            }
        }
    }

    private void loadSubwayData() {
        loadStationsFromFile(subway, "estaciones.txt");
        loadSectionsFromFile(subway, "secciones.txt");
        loadLinesFromFile(subway, "lineas.txt");
        loadPcarsFromFile(subway, "carros.txt");
        loadTrainsFromFile(subway, "trenes.txt");
        loadDriversFromFile(subway, "conductores.txt");

        System.out.println("Archivos cargados o ignorados si presentan errores. Accediendo al menú de manipulación.........");

        Menu menu = new Menu(subway);
        menu.show();
    }

    private void loadStationsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;


                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato incorrecto: " + line);
                    continue;
                }

                int id;
                int stop;
                try {
                    id = Integer.parseInt(parts[0].trim());
                    stop = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato numérico inválido");
                    continue;
                }

                String type = parts[1].trim().toUpperCase();
                String name = parts[2].trim();

                Station station = null;
                if (type.equals("C")) {
                    station = new Station(id, 'c', name, stop);
                } else if (type.equals("R")) {
                    station = new Station(id, 'r', name, stop);
                } else if (type.equals("T")) {
                    station = new Station(id, 't', name, stop);
                } else if (type.equals("M")) {
                    station = new Station(id, 'm', name, stop);
                } else {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Tipo no válido: " + type);
                    continue;
                }


                subway.addStation(station);
                System.out.println("Estación añadida al metro: " + station);
                continue;
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de estaciones: " + e.getMessage());
        }
    }



    private void loadSectionsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Número incorrecto de campos: " + line);
                    continue;
                }

                String startStation = parts[0].trim();
                String endStation = parts[1].trim();
                int time;
                int cost;
                try {
                    time = Integer.parseInt(parts[2].trim());
                    cost = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato numérico inválido: " + line);
                    continue;
                }

                Station start = findStationByName(subway, startStation);
                Station end = findStationByName(subway, endStation);

                if (start == null || end == null) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Una de las estaciones no existe.");
                    continue;
                }

                Section section = new Section(start, end, time, cost);
                subway.addSection(section);
                System.out.println("Seccion añadida: " + section);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de secciones: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'secciones.txt'");
        }
    }

    private void loadLinesFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Número incorrecto de campos: " + line);
                    continue;
                }

                int id;
                String name;
                String railType;
                List<Section> sections = new ArrayList<>();
                try {
                    id = Integer.parseInt(parts[0].trim());
                    name = parts[1].trim();
                    railType = parts[2].trim();
                } catch (NumberFormatException e) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato numérico inválido: " + line);
                    continue;
                }

                Line lineObject = new Line(id, name, railType, sections);
                subway.addLine(lineObject);
                System.out.println("Linea añadida: " + lineObject);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de líneas: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'lineas.txt'");
        }
    }

    private void loadPcarsFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                int capacity = Integer.parseInt(parts[1].trim());
                String model = parts[2].trim();
                String maker = parts[3].trim();
                Pcar.CarType type = null;

                String typeStr = parts[4].trim().toUpperCase();
                if (typeStr.equals("TR")) {
                    type = Pcar.CarType.TR;
                } else if (typeStr.equals("CT")) {
                    type = Pcar.CarType.CT;
                } else {
                    System.out.println("Error en la línea del archivo: Tipo de carro inválido: " + typeStr);
                    continue;
                }

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
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Número incorrecto de campos: " + line);
                    continue;
                }

                int id;
                int speed;
                int stayTime;
                String maker;
                try {
                    id = Integer.parseInt(parts[0].trim());
                    speed = Integer.parseInt(parts[2].trim());
                    stayTime = Integer.parseInt(parts[3].trim());
                    maker = parts[1].trim();
                } catch (NumberFormatException e) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato numérico inválido: " + line);
                    continue;
                }

                if (maker.isEmpty()) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Campo de fabricante vacío: " + line);
                    continue;
                }

                Train train = new Train(id, maker, speed, stayTime, new ArrayList<>());
                subway.addTrain(train);
                System.out.println("Tren añadido: " + train);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de trenes: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'trenes.txt'");
        }
    }

    private void loadDriversFromFile(Subway subway, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Número incorrecto de campos: " + line);
                    continue;
                }

                int id;
                String name;
                String trainMaker;
                try {
                    id = Integer.parseInt(parts[0].trim());
                    name = parts[1].trim();
                    trainMaker = parts[2].trim();
                } catch (NumberFormatException e) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Formato numérico inválido: " + line);
                    continue;
                }

                if (name.isEmpty() || trainMaker.isEmpty()) {
                    System.out.println("Error en la línea del archivo (" + lineNumber + "): Campo vacío detectado: " + line);
                    continue;
                }

                Driver driver = new Driver(id, name, trainMaker);
                subway.addDriver(driver);
                System.out.println("Conductor añadido: " + driver);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de conductores: " + e.getMessage());
            System.out.println("El archivo debe ser nombrado 'conductores.txt'");
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

}
