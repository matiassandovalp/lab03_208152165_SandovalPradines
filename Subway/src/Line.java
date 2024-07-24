import java.util.ArrayList;
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
        if (sections == null) {
            this.sections = new ArrayList<>();
        } else {
            this.sections= sections;
        }
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

    public void lineAddSection(Section newSection){
        if (newSection == null) {
            throw new IllegalArgumentException("El objeto carro es nulo.");
        }

        this.sections.add(newSection);
        //Considerar el siguiente comentario (podria evitar la creacion de una linea desde 0.)
        //luego, pasar por isLine (verifica continuidad), si este falla, la linea se revierte y se notifica al usuario que la nueva seccion no es compatible.
    }

    public boolean isLine(){
        int tipoFlag = 1; //1 = linea circular, 0 = linea con est. terminal

        for (Section section : sections) {
            Station p1 = section.getPoint1();
            Station p2 = section.getPoint2();
            if(p1.getType() == Station.StationType.T || p2.getType() == Station.StationType.T ){
                tipoFlag = 0;
            }
        }
        if(tipoFlag == 0){
           Section start = findStart();
           Section end = findEnd();
           return path(start, sections, end);
        }
        else{
            Section loop = sections.get(0);
            return pathCircle(loop, sections, loop, 0);
        }
    }

    //Encuentra la seccion que contiene la estación por la que empieza una linea
    public Section findStart(){
        for(Section section : sections){
            if(section.getPoint1().getType() == Station.StationType.T){
                return section;
            }
        }
        throw new IllegalArgumentException("La linea introducida no comienza en una estación terminal.");
    }

    //Encuentra la seccion que contiena la estacion por la que termina una linea
    public Section findEnd(){
        for(Section section : sections){
            if(section.getPoint2().getType() == Station.StationType.T){
                return section;
            }
        }
        throw new IllegalArgumentException("La linea introducida no termina en una estación terminal.");
    }

    //Recorre la lista de secciones removiendo la actual hasta llegar a la meta (estacion terminal final)
    public boolean path(Section actualSection, List<Section> sectionList, Section goalSection) {
        if (actualSection.equals(goalSection)) {
            return true;
        }
        if (sectionList == null || sectionList.isEmpty()) {
            return false;
        }

        List<Section> remainingSections = new ArrayList<>(sectionList);
        remainingSections.remove(actualSection);

        for (Section section : remainingSections) {
            if (actualSection.areContinuous(actualSection, section)) {
                if (path(section, remainingSections, goalSection)) {
                    return true;
                }
            }
        }
        return false;
    }

    //como path, pero considera iteraciones realizadas para terminar la funcion
    public boolean pathCircle(Section actualSection, List<Section> sectionList, Section goalSection, int ite) {
        if (actualSection.equals(goalSection) && ite > 0) {
            return true;
        }
        if (sectionList == null || sectionList.isEmpty() || ite > sectionList.size()) {
            return false;
        }

        List<Section> remainingSections = new ArrayList<>(sectionList);
        remainingSections.remove(actualSection);

        for (Section section : remainingSections) {
            if (actualSection.areContinuous(actualSection, section)) {
                if (pathCircle(section, remainingSections, goalSection, ite + 1)) {
                    return true;
                }
            }
        }
        return false;
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
