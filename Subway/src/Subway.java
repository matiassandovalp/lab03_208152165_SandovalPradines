import java.util.ArrayList;
import java.util.List;

public class Subway {
    private int id;
    private String name;
    private List<Station> stationList;//interno
    private List<Pcar> pcarList; //interno
    private List<Section> sectionList; //interno
    private List<Train> trainList;
    private List<Line> lineList;
    private List<Driver> driverList;


    public Subway() {
        this.trainList = new ArrayList<>();
        this.lineList = new ArrayList<>();
        this.driverList = new ArrayList<>();
        this.stationList = new ArrayList<>();//interno
        this.pcarList = new ArrayList<>();//interno
        this.sectionList = new ArrayList<>();//interno
    }

    public Subway(int id, String name) {
        this.id = id;
        this.name = name;
        this.trainList = new ArrayList<>();
        this.lineList = new ArrayList<>();
        this.driverList = new ArrayList<>();
        this.pcarList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public List<Station> getStationList() {
        return stationList;
    }

    public List<Pcar> getPcarList(){
        return pcarList;
    }

    public List<Section> getSectionList(){
        return sectionList;
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


    public void addStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("El objeto estación es nulo.");
        }
        this.stationList.add(station);
    }

    public void addPcar(Pcar car) {
        if (car == null) {
            throw new IllegalArgumentException("El objeto carro es nulo.");
        }
        this.pcarList.add(car);
    }

    public void addSection(Section section) {
        if (section == null) {
            throw new IllegalArgumentException("El objeto seccion es nulo.");
        }
        this.sectionList.add(section);
    }


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
