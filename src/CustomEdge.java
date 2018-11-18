import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomEdge extends DefaultWeightedEdge {
    String flightNum;
    String startTime;
    String endTime;
    int cost;

    public CustomEdge(String fNumb, String startT, String endT, String fcost) {
        flightNum = fNumb;
        startTime = startT;
        endTime = endT;
        cost = Integer.parseInt(fcost);
    }

    public int getCost() {
        return cost;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getFlightNum() {
        return flightNum;
    }



}
