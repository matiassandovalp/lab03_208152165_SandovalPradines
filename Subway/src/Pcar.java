import java.util.List;

public class Pcar {
    private int id;
    private int passengerCapacity;
    private String model;
    private String trainMaker;//Restriccion
    private CarType carType;

    public enum CarType {
        TR('t'), CT('c');

        private final char letter;

        CarType(char letter) {
            this.letter = letter;
        }

        public char getLetter() {
            return letter;
        }

        // Método estático para obtener el CarType de un char.
        public static Pcar.CarType fromChar(char letter) {
            for (Pcar.CarType type : Pcar.CarType.values()) {
                if (type.getLetter() == letter) {
                    return type;
                }
            }
            throw new IllegalArgumentException("El tipo introducido no es válido: " + letter);
        }
    }

    public Pcar(int id, int passengerCapacity, String model, String trainMaker, CarType carType) {
        this.id = id;
        this.passengerCapacity = passengerCapacity;
        this.model = model;
        this.trainMaker = trainMaker;
        this.carType = carType;
    }

    public int getId() {
        return id;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public String getModel() {
        return model;
    }

    public String getTrainMakerPcar() {
        return trainMaker;
    }

    public CarType getCarType() {
        return carType;
    }

    @Override
    public String toString() {
        return "Pcar{" +
                "id=" + id +
                ", passengerCapacity=" + passengerCapacity +
                ", model='" + model + '\'' +
                ", trainMaker='" + trainMaker + '\'' +
                ", carType=" + carType +
                '}';
    }
}
