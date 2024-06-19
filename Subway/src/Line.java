import java.util.List;

public class Line {
    private List<Section> sections;
    private int id;
    private String name;
    private String railType;

    public Line(int id, String name, String railType, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.railType = railType;
        this.sections = sections;
    }

    public int getId() {
        return id;
    }

    public String getRailType() {
        return railType;
    }

    public String getName() {
        return name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public static int lineLength(Line line) {
        int totalLength = 0;
        for (Section section : line.getSections()) {
            totalLength += section.getDistance();
        }
        return totalLength;
    }

    public static int lineCost(Line line){
        int totalCost = 0;
        for (Section section : line.getSections()) {
            totalCost += section.getCost();
        }
        return totalCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Line{id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", railType='").append(railType).append('\'')
                .append(", sections=[");

        for (Section section : sections) {
            sb.append(section.toString()).append(", ");
        }

        if (!sections.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the trailing comma and space
        }

        sb.append("]}");
        return sb.toString();
    }
}
