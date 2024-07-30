import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private String trainMaker;
    private int speed;
    private int stationStayTime;
    private List<Pcar> carList;

    //Para crear un tren sin carros para agregarlos posteriormente, carList es ingresado como null
    public Train(int id, String trainMaker, int speed, int stationStayTime, List<Pcar> carList) {
        this.id = id;
        this.trainMaker = trainMaker;
        if (speed < 1) {
            throw new IllegalArgumentException("La velocidad introducida es 0 o menor a 0");
        }
        if (stationStayTime < 0) {
            throw new IllegalArgumentException("La velocidad introducida menor a 0");
        }

        if (carList == null) {
            this.carList = new ArrayList<>();
        } else {//si for each es el mismo tipo, entonces se asigna la lista, sino error!!
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

    public Boolean isTrain() {
        int flag;
        if (carList.isEmpty()) {
            throw new IllegalArgumentException("El tren ingresado no presenta ningún carro.");
        }

        if (carList.get(0).getCarType() != Pcar.CarType.TR || carList.get(carList.size() - 1).getCarType() != Pcar.CarType.TR) {
            throw new IllegalArgumentException("El tren ingresado no inicia o termina con un carro terminal.");
        }

        for (int i = 1; i < carList.size() - 1; i++) {
            if (carList.get(i).getCarType() != Pcar.CarType.CT) {
                throw new IllegalArgumentException("El tren no es consistente: solo los carros centrales pueden estar entre los carros terminales.");
            }
        }
        return true;
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
            if (position != 0 && position != carList.size()) {
                throw new IllegalArgumentException("El carro terminal solo puede estar en los extremos.");
            }
        } else if (carro.getCarType() == Pcar.CarType.CT) {
            if (position == 0 || position == carList.size()) {
                if (!carList.isEmpty()) {
                    throw new IllegalArgumentException("El carro central no puede estar en los extremos.");
                }//NT: Esto implica que deben agregarse ambos carros terminales antes de agregar los centrales.
            }
        }

        this.carList.add(position, carro);
    }


    public void removeCar(int position) {
        if (position < 0 || position >= carList.size()) {
            throw new IllegalArgumentException("La posición introducida está fuera de los límites.");
        }

        Pcar carToRemove = carList.get(position);
        if (carToRemove.getCarType() == Pcar.CarType.TR) {
            throw new IllegalArgumentException("No se puede eliminar un carro central.");
        }

        carList.remove(position);
    }

    public int fetchCapacity(){
        int totalCapacity = 0;
        for (Pcar car : carList) {
            totalCapacity += car.getPassengerCapacity();
        }
        return totalCapacity;
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
