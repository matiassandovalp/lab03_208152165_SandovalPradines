public class Section {
    private Station point1;
    private Station point2;
    private int distance;
    private int cost;

    public Section(Station point1, Station point2, int distance, int cost) {
        if (point1 == null || point2 == null) {
            throw new IllegalArgumentException("Las estaciones no pueden ser nulas.");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("La distancia introducida es negativa o 0.");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("El costo introducido es negativo.");
        }
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
        this.cost = cost;
    }

    public Station getPoint1() {
        return point1;
    }

    public Station getPoint2() {
        return point2;
    }

    public int getDistance() {
        return distance;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Section{" +
                "point1=" + point1 +
                ", point2=" + point2 +
                ", distance=" + distance +
                ", cost=" + cost +
                '}';
    }
}
