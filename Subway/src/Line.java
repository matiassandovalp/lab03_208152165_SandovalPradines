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

    //Remover parametros de entrada (solo se puede llamar con objetos de la clase)
    public int lineLength() {
        int totalLength = 0;
        for (Section section : sections) {
            totalLength += section.getDistance();
        }
        return totalLength;
    }

    public int lineCost(){
        int totalCost = 0;
        for (Section section : sections) {
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
        for (int i = 0; i < sections.size(); i++) {
            sb.append(sections.get(i));
            if (i < sections.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");
        return sb.toString();
    }
}
