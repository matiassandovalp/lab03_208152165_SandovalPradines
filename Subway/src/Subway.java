import java.util.ArrayList;
import java.util.List;

public class Subway {
    private int id;
    private String name;
    private List<Train> trainList;
    private List<Line> lineList;
    private List<Driver> driverList;

    public Subway(int id, String name) {
        this.id = id;
        this.name = name;
        this.trainList = new ArrayList<>();
        this.lineList = new ArrayList<>();
        this.driverList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Train> getTrainList() {
        return trainList;
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    //Temporal!!
    public void addTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("El objeto tren es nulo.");
        }
        this.trainList.add(train);
    }

    public void addLine(Line line) {
        if (line == null) {
            throw new IllegalArgumentException("El objeto línea es nulo.");
        }
        this.lineList.add(line);
    }

    public void addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("El conductor no puede ser nulo.");
        }
        this.driverList.add(driver);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Subway{id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", trains=[");
        for (int i = 0; i < trainList.size(); i++) {
            sb.append(trainList.get(i));
            if (i < trainList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], lines=[");
        for (int i = 0; i < lineList.size(); i++) {
            sb.append(lineList.get(i));
            if (i < lineList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], drivers=[");
        for (int i = 0; i < driverList.size(); i++) {
            sb.append(driverList.get(i));
            if (i < driverList.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");
        return sb.toString();
    }
}
