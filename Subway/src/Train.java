import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private String trainMaker;
    private int speed;
    private int stationStayTime;
    private List<Pcar> carList;

    public Train(int id, String trainMaker, int speed, int stationStayTime, List<Pcar> carList) {
        this.id = id;
        this.trainMaker = trainMaker;
        if (speed < 1) {
            throw new IllegalArgumentException("La velocidad introducida es 0 o menor a 0");
        }
        if (stationStayTime < 0) {
            throw new IllegalArgumentException("La velocidad introducida menor a 0");
        }

        // Initialize carList as an empty ArrayList if carList is null
        if (carList == null) {
            this.carList = new ArrayList<>();
        } else {
            this.carList = carList;
        }

        this.speed = speed;
        this.stationStayTime = stationStayTime;
    }

    public int getId() {
        return id;
    }

    public String getTrainMaker() {
        return trainMaker;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStationStayTime() {
        return stationStayTime;
    }

    public List<Pcar> getCarList() {
        return carList;
    }

    public void addCar(Pcar carro, int position) {
        if (carro == null) {
            throw new IllegalArgumentException("El objeto carro es nulo.");
        }

        if (position < 0 || position > carList.size()) {
            throw new IllegalArgumentException("La posición introducida está fuera de los límites.");
        }

        String carMaker = carro.getTrainMakerPcar();
        String trainMaker1 = getTrainMaker();
        if (!carMaker.equals(trainMaker1)) {
            throw new IllegalArgumentException("El fabricante del carro es diferente al del tren.");
        }

        if (carro.getCarType() == Pcar.CarType.TR) {
            throw new IllegalArgumentException("El carro introducido solo puede ser central.");
        }

        this.carList.add(position, carro);
    }

    @Override
    public String toString() {
        StringBuilder tr = new StringBuilder();
        tr.append("Train{id=").append(id)
                .append(", trainMaker='").append(trainMaker).append('\'')
                .append(", speed=").append(speed)
                .append(", stationStayTime=").append(stationStayTime)
                .append(", passengerCars=[");

        for (int i = 0; i < carList.size(); i++) {
            tr.append(carList.get(i));
            if (i < carList.size() - 1) {
                tr.append(", ");
            }
        }

        tr.append("]}");
        return tr.toString();
    }
}
