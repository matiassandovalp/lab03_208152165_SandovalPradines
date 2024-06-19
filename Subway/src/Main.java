import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create Station instances
        Station station1 = new Station(1, 'r', "Station1", 5);
        Station station2 = new Station(2, Station.StationType.M, "Station2", 10);
        Station station4 = new Station(4, 'r', "Station4", 5);
        Station station5 = new Station(5, 'm', "Station5", 10);
        Station station6 = new Station(6, 'c', "Station6", 15);

        // Create some sample sections
        Section section1 = new Section(station1, station2, 100, 50);
        Section section2 = new Section(station2, station4, 150, 70);
        Section section3 = new Section(station4, station5, 200, 80);
        Section section4 = new Section(station5, station6, 250, 90);

        // Create Line objects
        Line line1 = new Line(1, "Line1", "Type1", List.of(section1, section2));
        Line line2 = new Line(2, "Line2", "Type2", List.of(section3, section4));

        // Calculate and print the total length of the lines using the static method
        System.out.println("Total length of line1 using static method: " + Line.lineLength(line1));
        System.out.println("Total length of line2 using static method: " + Line.lineLength(line2));

        // Print the whole line details
        System.out.println("Details of line1: " + line1);
        System.out.println("Details of line2: " + line2);
    }
}
