package penguin.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Penguin {
    private List<Geo> locations;
    private String trackID;

    public Penguin(List<Geo> locations, String trackID) {
        this.locations = locations;
        this.trackID = trackID;
    }

    @Override
    public String toString() {
        return "Penguin{" + "locations=" + locations + ", trackID='" + trackID + '\'' + '}';
    }

    public List<Geo> getLocations() {
        return locations;
    }

    public String getTrackID() {
        return trackID;
    }


    public String toStringUsingStreams() {

        return "Penguin{" + "locations=" + locations.stream()
                .sorted((Comparator.comparing(Geo::getLatitude).thenComparing(Geo::getLongitude)).reversed()).map(Geo::toString)
                .collect(Collectors.joining(", ", "[", "]")) + ", trackID='" + trackID + '\'' + '}';

    }
}
