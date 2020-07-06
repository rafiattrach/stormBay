package penguin.streams;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DataScience {

    public static Stream<Penguin> getDataByTrackId(Stream<PenguinData> stream) {

        Map<String, Penguin> unique = new HashMap<String, Penguin>();

        stream.forEach(d -> {
            // if the id exists, add the locations to the existing penguin
            if (unique.containsKey(d.getTrackID())) {
                unique.get(d.getTrackID()).getLocations().add(d.getGeom());
            } else {
                // otherwise instantiate a new penguin with the locations
                List<Geo> visited = new LinkedList<Geo>();
                visited.add(d.getGeom());
                Penguin member = new Penguin(visited, d.getTrackID());

                unique.put(d.getTrackID(), member);
            }
        });

        return unique.values().stream();

    }

    public static void outputPenguinStream() {
        // supplier to use the stream multiple times if needed
        Supplier<Stream<Penguin>> penguinStream = () -> getDataByTrackId(CSVReading.processInputFile());

        System.out.println(penguinStream.get().count());

        // sorting strings using the compareTo java method
        penguinStream.get().sorted((id1, id2) -> (id1.getTrackID()).compareTo(id2.getTrackID()))
                .forEach(p -> System.out.println(p.toStringUsingStreams()));

    }

    public static void outputLocationRangePerTrackid(Stream<PenguinData> stream) {

        Supplier<Stream<Penguin>> penguinStream = () -> getDataByTrackId(stream);

        penguinStream.get().sorted((id1, id2) -> (id1.getTrackID()).compareTo(id2.getTrackID())).forEach(p -> {

            // stats for both longitude and latitude for each penguin
            DoubleSummaryStatistics longStats = p.getLocations().stream().mapToDouble(g -> g.getLongitude())
                    .summaryStatistics();
            DoubleSummaryStatistics latStats = p.getLocations().stream().mapToDouble(g -> g.getLatitude())
                    .summaryStatistics();

            System.out.println(p.getTrackID() + "\n" + "Min Longitude: " + longStats.getMin() + " Max Longitude: "
                    + longStats.getMax() + " Avg Longitude: " + longStats.getAverage() + " Min Latitude: "
                    + latStats.getMin() + " Max Latitude: " + latStats.getMax() + " Avg Latitude: "
                    + latStats.getAverage());

        });

    }

}