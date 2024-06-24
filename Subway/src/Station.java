import java.util.List;


public class Station {
    private final int id;
    private final String name;
    private final StationType type;
    private final int stopTime;

    // Enum para stationTypes
    public enum StationType {
        R('r'), M('m'), C('c'), T('t');

        private final char letter;

        StationType(char letter) {
            this.letter = letter;
        }

        public char getLetter() {
            return letter;
        }

        // Método estático para obtener el StationType de un char.
        public static StationType fromChar(char letter) {
            for (StationType type : StationType.values()) {
                if (type.getLetter() == letter) {
                    return type;
                }
            }
            throw new IllegalArgumentException("La letra introducida no es válida: " + letter);
        }
    }

    // Constructor de station que acepta tipos de estación solo directamente(Station.StationType.M).
    public Station(int id, StationType type, String name, int stop) {
        if (stop <= 0) {
            throw new IllegalArgumentException("El tiempo de parada introducido es menor o igual a 0.");
        }
        this.id = id;
        this.type = type;
        this.name = name;
        this.stopTime = stop;
    }

    // Constructor de station que acepta tipos de estación como char ('r') y lo convierte a stationType
    public Station(int id, char typeLetter, String name, int stop) {
        if (stop <= 0) {
            throw new IllegalArgumentException("El tiempo de parada introducido es menor a 0.");
        }
        this.id = id;
        this.type = StationType.fromChar(typeLetter);
        this.name = name;
        this.stopTime = stop;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StationType getType() {
        return type;
    }

    public int getStopTime() {
        return stopTime;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", stopTime=" + stopTime +
                '}';
    }
}
