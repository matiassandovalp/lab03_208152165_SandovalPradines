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
                System.out.println("2) Manipular secciones");
                System.out.println("3) Manipular líneas");
                System.out.println("4) Manipular Carros");
                System.out.println("5) Manipular Trenes");
                System.out.println("6) Manipular Conductores");
                System.out.println("7) Salir");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 7) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            Submenu m = null;
            switch (option) {
                case 1 -> m = new StationMenu(subwayAdministrator);
                case 2 -> m = new SectionMenu(subwayAdministrator);
                case 3 -> m = new LineMenu(subwayAdministrator);
                case 4 -> m = new PcarMenu(subwayAdministrator);
                case 5 -> m = new TrainMenu(subwayAdministrator);
                case 6 -> m = new DriverMenu(subwayAdministrator);
                case 7 -> {
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
        private Scanner input = new Scanner(System.in);

        public AddSectionMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de agragar secciones en líneas");

            // Pide id de linea
            System.out.print("Ingrese el id de la línea: ");
            String lineInput = input.nextLine();
            int idLine;
            try {
                idLine = Integer.parseInt(lineInput);
            } catch (NumberFormatException e) {
                System.out.println("El id introducido no es válido");
                return;
            }

            //Pide estacion por la que empieza la seccion
            System.out.print("Ingrese la primera estación de la sección: ");
            String startStation = input.nextLine();
            if (startStation.isBlank()) {
                System.out.println("El nombre de la estación no puede estar vacío");
                return;
            }

            // Encuentra la linea por el id en el subway
            Line line = findLineById(idLine);
            if (line == null) {
                System.out.println("Línea no encontrada");
                return;
            }

            // Encuentra la seccion por la estacion inicial
            Section newSection;
            try {
                newSection = findSectionByStartStation(line, startStation);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }

            // Añade la seccion a la linea
            line.lineAddSection(newSection);
            System.out.println("Se añadió la sección a la línea especificada");
        }

        // Metodo que permite encontrar una linea en un subway mediante el id de esta
        private Line findLineById(int idLine) {
            for (Line line : subwayAdministrator.getLineList()) {
                if (line.getId() == idLine) {
                    return line;
                }
            }
            return null;
        }

        // Método modificado para buscar entre las secciones de la linea
        private Section findSectionByStartStation(Line line, String stationName) {
            for (Section section : line.getSections()) {
                if (section.getPoint1().getName().equalsIgnoreCase(stationName)) {
                    return section;
                }
            }
            throw new IllegalArgumentException("Ninguna sección de la línea empieza por la estación especificada.");
        }
    }

    public class StationMenu extends Submenu {

        public StationMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de estaciones");
            boolean valid;
            int option;

            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear estación");
                System.out.println("2) Mostrar estaciones");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 2) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            ActionMenu m = null;
            switch (option) {
                case 1 -> m = new CreateStationMenu(subwayAdministrator);
                case 2 -> {
                    System.out.println("Estaciones actualmente creadas:");
                    for (Station s : subwayAdministrator.getStationList()) {
                        System.out.println(s.toString());
                    }
                    return;
                }

            }

            if (m != null) {
                m.show();
            }
        }
    }


    public class CreateStationMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public CreateStationMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de creación de estaciones");

            // Ingrese y valida el id de la estación
            System.out.print("Ingrese el id de la estación: ");
            String stationInput = input.nextLine().trim();
            int stationId;
            try {
                stationId = Integer.parseInt(stationInput);
            } catch (NumberFormatException e) {
                System.out.println("El id ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Chequea si ya existe una estación con este id
            if (subwayAdministrator.getStationList().stream().anyMatch(station -> station.getId() == stationId)) {
                System.out.println("Ya existe una estación con el id ingresado.");
                return;
            }


            // Ingresa y valida el tipo de estación
            System.out.print("Ingrese el tipo de estación: ");
            System.out.print("Tipos existentes: 'r' 't' 'c' 'm' ");
            String stationType = input.nextLine().trim().toUpperCase();
            if (stationType.isBlank()) {
                System.out.println("El tipo de estación no puede estar vacío.");
                return;
            }
            if (!stationType.equals("T") && !stationType.equals("R") && !stationType.equals("M") && !stationType.equals("C")) {
                System.out.println("El tipo de estación no está entre los valores especificados.");
                return;
            }

            // Ingresa y valida el nombre de la estación
            System.out.print("Ingrese el nombre de la estación: ");
            String stationName = input.nextLine().trim().toUpperCase();
            if (stationName.isBlank()) {
                System.out.println("El nombre de la estación no puede estar vacío.");
                return;
            }

            // Chequea si ya existe una estación con este nombre
            if (subwayAdministrator.getStationList().stream().anyMatch(station -> station.getName() == stationName)) {
                System.out.println("Ya existe una estación con el nombre ingresado.");
                return;
            }

            // Ingresa y valida el tiempo de estadía en la estación
            System.out.print("Ingrese el tiempo de estadía del tren en la estación: ");
            String stationInputTime = input.nextLine().trim();
            int stationTimeStop;
            try {
                stationTimeStop = Integer.parseInt(stationInputTime);
            } catch (NumberFormatException e) {
                System.out.println("El tiempo de estadía ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Crea y añade el nuevo objeto de la clase Estación
            Station newStation;
            switch (stationType) {
                case "C":
                    newStation = new Station(stationId, 'c', stationName, stationTimeStop);
                    break;
                case "R":
                    newStation = new Station(stationId, 'r', stationName, stationTimeStop);
                    break;
                case "T":
                    newStation = new Station(stationId, 't', stationName, stationTimeStop);
                    break;
                case "M":
                    newStation = new Station(stationId, 'm', stationName, stationTimeStop);
                    break;
                default:
                    System.out.println("Error en la creación de la estación. Tipo no válido.");
                    return;
            }

            subwayAdministrator.addStation(newStation);
            System.out.println("Se creó la estación exitosamente.");
        }


    }

    public class PcarMenu extends Submenu {

        public PcarMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de carros");
            boolean valid;
            int option;

            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear carro");
                System.out.println("2) Mostrar carros");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 2) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            ActionMenu m = null;
            switch (option) {
                case 1 -> m = new CreatePcarMenu(subwayAdministrator);
                case 2 -> {
                    System.out.println("Carros de pasajero actualmente creados:");
                    for (Pcar pcar : subwayAdministrator.getPcarList()) {
                        System.out.println(pcar.toString());
                    }
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }

    public class CreatePcarMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public CreatePcarMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de creación de carros");

            // Ingresa y valida el id del carro
            System.out.print("Ingrese el id del carro: ");
            String pcarInput = input.nextLine().trim();
            int pcarId;
            try {
                pcarId = Integer.parseInt(pcarInput);
            } catch (NumberFormatException e) {
                System.out.println("El id ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Ingresa y valida la capacidad de pasajeros
            System.out.print("Ingrese la capacidad de pasajeros del carro: ");
            String capacityInput = input.nextLine().trim();
            int pcarCapacity;
            try {
                pcarCapacity = Integer.parseInt(capacityInput);
            } catch (NumberFormatException e) {
                System.out.println("La capacidad ingresada no es válida. Debe ser un número entero.");
                return;
            }

            // Ingresa y valida el tipo de carro
            System.out.print("Ingrese el tipo de carro: ");
            System.out.print("Tipos existentes: 'TR' 'CT' ");
            String carType = input.nextLine().trim().toUpperCase();
            if (carType.isBlank()) {
                System.out.println("El tipo de carro no puede estar vacío.");
                return;
            }
            if (!carType.equals("TR") && !carType.equals("CT")) {
                System.out.println("El tipo de carro no está entre los valores especificados.");
                return;
            }

            // Ingresa el modelo del carro
            System.out.print("Ingrese el modelo del carro: ");
            String pcarModel = input.nextLine().trim().toUpperCase();
            if (pcarModel.isBlank()) {
                System.out.println("El modelo del carro no puede estar vacío.");
                return;
            }

            // Ingresa el manufactor del carro
            System.out.print("Ingrese el manufactor del carro: ");
            String pcarMaker = input.nextLine().trim().toUpperCase();
            if (pcarMaker.isBlank()) {
                System.out.println("El manufactor del carro no puede estar vacío.");
                return;
            }

            // Crea y añade el nuevo objeto de la clase Pcar
            Pcar newPcar;
            switch (carType) {
                case "CT":
                    newPcar = new Pcar(pcarId, pcarCapacity, pcarModel, pcarMaker, Pcar.CarType.CT);
                    break;
                case "TR":
                    newPcar = new Pcar(pcarId, pcarCapacity, pcarModel, pcarMaker, Pcar.CarType.TR);
                    break;
                default:
                    System.out.println("Error en la creación del carro. Tipo no válido.");
                    return;
            }

            subwayAdministrator.addPcar(newPcar);
            System.out.println("Se creó el carro exitosamente.");
        }
    }

    public class TrainMenu extends Submenu {

        public TrainMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de trenes");
            boolean valid;
            int option;

            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear tren");
                System.out.println("2) Añadir carros a tren");
                System.out.println("3) Mostrar trenes");
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
                case 1 -> m = new CreateTrainMenu(subwayAdministrator);
                case 2 -> m = new AddPcarMenu(subwayAdministrator);
                case 3 -> {
                    System.out.println("Trenes:");
                    for (Train train : subwayAdministrator.getTrainList()) {
                        System.out.println(train.toString());
                    }
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }


    public class CreateTrainMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public CreateTrainMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de creación de trenes");

            // Ingresa y valida el id del tren
            System.out.print("Ingrese el id del tren: ");
            String trainInput = input.nextLine().trim();
            int trainId;
            try {
                trainId = Integer.parseInt(trainInput);
            } catch (NumberFormatException e) {
                System.out.println("El id ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Ingresa el manufactor del tren
            System.out.print("Ingrese el manufactor del tren: ");
            String trainMaker = input.nextLine().trim().toUpperCase();
            if (trainMaker.isBlank()) {
                System.out.println("El manufactor del tren no puede estar vacío.");
                return;
            }

            // Ingresa la velocidad del tren
            System.out.print("Ingrese la velocidad del tren: ");
            String speedInput = input.nextLine().trim();
            int trainSpeed;
            try {
                trainSpeed = Integer.parseInt(speedInput);
            } catch (NumberFormatException e) {
                System.out.println("La velocidad ingresada no es válida. Debe ser un número entero.");
                return;
            }

            // Ingresa el tiempo de estadía del tren
            System.out.print("Ingrese el tiempo de estadía en estación del tren: ");
            String stayInput = input.nextLine().trim();
            int trainStay;
            try {
                trainStay = Integer.parseInt(stayInput);
            } catch (NumberFormatException e) {
                System.out.println("El tiempo ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Crea y añade el tren al sistema
            Train newTrain = new Train(trainId, trainMaker, trainSpeed, trainStay, new ArrayList<>());
            subwayAdministrator.addTrain(newTrain);
            System.out.println("Se creó el tren exitosamente.");
        }
    }


    public class AddPcarMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public AddPcarMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        private Train findTrainById(int idTrain) {
            for (Train train : subwayAdministrator.getTrainList()) {
                if (train.getId() == idTrain) {
                    return train;
                }
            }
            return null;
        }

        private Pcar findPcarById(int idPcar) {
            for (Pcar car : subwayAdministrator.getPcarList()) {
                if (car.getId() == idPcar) {
                    return car;
                }
            }
            return null;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Agregación de carros a un tren: ");

            // Pide id de tren
            System.out.print("Ingrese el id del tren: ");
            String trainInput = input.nextLine();
            int idTrain;
            try {
                idTrain = Integer.parseInt(trainInput);
            } catch (NumberFormatException e) {
                System.out.println("El id del tren introducido no es válido");
                return;
            }

            // Pide id de carro
            System.out.print("Ingrese el id del carro: ");
            String carInput = input.nextLine();
            int idPcar;
            try {
                idPcar = Integer.parseInt(carInput);
            } catch (NumberFormatException e) {
                System.out.println("El id del carro introducido no es válido");
                return;
            }

            // Encuentra el tren por el id en el subway
            Train train = findTrainById(idTrain);
            if (train == null) {
                System.out.println("Tren no encontrado");
                return;
            }

            // Encuentra el carro por el id en el subway
            Pcar car = findPcarById(idPcar);
            if (car == null) {
                System.out.println("Carro no encontrado");
                return;
            }

            // Pide la posición en la que se agregará el carro
            System.out.print("Ingrese la posición en la que se agregará el carro: ");
            String posInput = input.nextLine();
            int posPcar;
            try {
                posPcar = Integer.parseInt(posInput);
            } catch (NumberFormatException e) {
                System.out.println("La posición introducida no es válida");
                return;
            }

            // Añade el carro al tren
            train.addCar(car, posPcar);
            System.out.println("Se añadió el carro al tren exitosamente");
        }
    }

    public class DriverMenu extends Submenu {

        public DriverMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de conductores");
            boolean valid;
            int option;

            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear conductor");
                System.out.println("2) Mostrar conductores");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 2) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            ActionMenu m = null;
            switch (option) {
                case 1 -> m = new CreateDriverMenu(subwayAdministrator);
                case 2 -> {
                    System.out.println("Conductores:");
                    for (Driver driver : subwayAdministrator.getDriverList()) {
                        System.out.println(driver.toString());
                    }
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }


    public class CreateDriverMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public CreateDriverMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de creación de conductores");

            // Ingresa y valida el id del conductor
            System.out.print("Ingrese el id del conductor: ");
            String driverInput = input.nextLine().trim();
            int driverId;
            try {
                driverId = Integer.parseInt(driverInput);
            } catch (NumberFormatException e) {
                System.out.println("El id ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Ingresa el nombre del conductor
            System.out.print("Ingrese el nombre del conductor: ");
            String driverName = input.nextLine().trim().toUpperCase();
            if (driverName.isBlank()) {
                System.out.println("El nombre del conductor no puede estar vacío.");
                return;
            }

            // Ingresa el manufactor del tren
            System.out.print("Ingrese el manufactor del tren que el conductor esta capacitado para conducir: ");
            String trainMaker = input.nextLine().trim().toUpperCase();
            if (trainMaker.isBlank()) {
                System.out.println("El manufactor del tren no puede estar vacío.");
                return;
            }


            // Crea y añade el conductor al sistema
            Driver newDriver = new Driver(driverId, driverName, trainMaker);
            subwayAdministrator.addDriver(newDriver);
            System.out.println("Se creó el conductor exitosamente.");
        }
    }


    public class SectionMenu extends Submenu {

        public SectionMenu(Subway subwayAdministrator) {
            super(subwayAdministrator);
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de secciones");
            boolean valid;
            int option;

            do {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1) Crear seccion");
                System.out.println("2) Mostrar secciones");
                String line = input.nextLine();

                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                if (option < 1 || option > 2) {
                    valid = false;
                    System.out.println("Error en la opción introducida");
                } else {
                    valid = true;
                }
            } while (!valid);

            ActionMenu m = null;
            switch (option) {
                case 1 -> m = new CreateSectionMenu(subwayAdministrator);
                case 2 -> {
                    System.out.println("Conductores:");
                    for (Section section : subwayAdministrator.getSectionList()) {
                        System.out.println(section.toString());
                    }
                    return;
                }
            }

            if (m != null) {
                m.show();
            }
        }
    }


    public class CreateSectionMenu implements ActionMenu {

        private Subway subwayAdministrator;
        private Scanner input = new Scanner(System.in);

        public CreateSectionMenu(Subway subwayAdministrator) {
            this.subwayAdministrator = subwayAdministrator;
        }

        @Override
        public void show() {
            System.out.println("#############################");
            System.out.println("Menu de creación de secciones");

            // Ingresa el nombre de la estación de partida
            System.out.print("Ingrese la estación inicial del tramo: ");
            String startStation = input.nextLine().trim().toUpperCase();
            if (startStation.isBlank()) {
                System.out.println("El nombre de la estación inicial no puede estar vacío.");
                return;
            }

            // Buscar la estación de partida
            Station start = findStationByName(startStation);
            if (start == null) {
                System.out.println("La estación inicial no existe.");
                return;
            }

            // Ingresa el nombre de la estación de término
            System.out.print("Ingrese la estación final del tramo: ");
            String endStation = input.nextLine().trim().toUpperCase();
            if (endStation.isBlank()) {
                System.out.println("El nombre de la estación final no puede estar vacío.");
                return;
            }

            // Buscar la estación de término
            Station end = findStationByName(endStation);
            if (end == null) {
                System.out.println("La estación final no existe.");
                return;
            }

            // Ingresa la distancia de la sección
            System.out.print("Ingrese la distancia entre estaciones: ");
            String distanceInput = input.nextLine().trim();
            int sectionTime;
            try {
                sectionTime = Integer.parseInt(distanceInput);
            } catch (NumberFormatException e) {
                System.out.println("El tiempo ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Ingresa el costo de la sección
            System.out.print("Ingrese el costo entre estaciones: ");
            String costInput = input.nextLine().trim();
            int sectionCost;
            try {
                sectionCost = Integer.parseInt(costInput);
            } catch (NumberFormatException e) {
                System.out.println("El costo ingresado no es válido. Debe ser un número entero.");
                return;
            }

            // Crear y añadir la nueva sección al sistema
            Section newSection = new Section(start, end, sectionTime, sectionCost);
            subwayAdministrator.addSection(newSection);
            System.out.println("Se creó la sección exitosamente.");
        }

        private Station findStationByName(String name) {
            for (Station station : subwayAdministrator.getStationList()) {
                if (station.getName().equalsIgnoreCase(name)) {
                    return station;
                }
            }
            return null;  // Retorna null si no se encuentra la estación
        }
    }

}

