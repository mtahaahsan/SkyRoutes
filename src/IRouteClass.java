import org.jgrapht.GraphPath;

import java.util.List;

public class IRouteClass implements IRoute {

    List<CustomEdge> myEdges;
    List<String> myVerts;
    GraphPath<String, CustomEdge> mypath;

    public IRouteClass(List<CustomEdge> Edges, List<String> verts, GraphPath<String, CustomEdge> path) {
        myEdges = Edges;
        myVerts = verts;
        mypath = path;
    }


    /**
     * This method returns the number of airports in the route
     * @return list of Strings
     */
    @Override
    public List<String> getStops() {
        return mypath.getVertexList();
    }

    /**
     * This method returns the list of flight numbers by using the getFlightNum() method on every edge in the EdgeList
     * @return
     */
    @Override
    public List<String> getFlights() {
        List<String> flightCodes = null;
        for (CustomEdge edge:myEdges) {
            flightCodes.add(edge.getFlightNum());
        }
        return flightCodes;
    }


    /**
     * This method counts the number of flights in the path by counting the number of edges in the graph
     * @return
     */
    @Override
    public int totalHop() {
        int count = 0;
        for(CustomEdge edge:myEdges){
            count = count + 1;
        }
        return count;
    }

    /**
     * This method return the total cost of a trip by using the getCost() method on every edge in the EdgeList
     * @return
     */
    @Override
    public int totalCost() {
        int sum = 0 ;
        for(CustomEdge edge:myEdges){
            sum = sum + edge.getCost();
        }
        return sum;
    }

    /**
     * This method returns the total amount of time a flight takes
     * @return
     */
    @Override
    public int airTime() {
        int total = 0;

        // For each CustomEdge in myEdges, separate the hours and the minutes and parse into integers
        for(CustomEdge myEdge:myEdges){
            int hoursStart = Integer.parseInt(myEdge.getStartTime().substring(0,2));
            int minutesStart = Integer.parseInt(myEdge.getStartTime().substring(2));
            int hoursEnd = Integer.parseInt(myEdge.getEndTime().substring(0,2));
            int minutesEnd = Integer.parseInt(myEdge.getEndTime().substring(2));

            int totHours = 0;

            // If starting time is greater than ending time, then startTime is modded from 24 to compensate for the next
            // day, else, just subtract end and start times. Minutes is being calculated by just subtracting in both.
            if(hoursStart>hoursEnd){
                hoursStart = 24%hoursStart;
                totHours = hoursStart + hoursEnd;
                totHours = totHours*60 + (minutesEnd - minutesStart);
            }
            else{
                totHours = hoursEnd - hoursStart;
                totHours = totHours*60 + (minutesEnd-minutesStart);
            }
            total =  total + totHours;
        }
        return total;
    }


    /**
     * This method returns the total time taken in waiting between two flights
     * @return
     */
    @Override
    public int connectingTime() {
        int total = 0;

        // For each CustomEdge in myEdges, take the current and next edge, separate the hours and the minutes and parse
        // into integer
        for (int i = 0; i < myEdges.size()-1; i++) {
            CustomEdge myEdge = myEdges.get(i);
            CustomEdge myNextEdge = myEdges.get(i+1);
            int hoursStart = Integer.parseInt(myEdge.getEndTime().substring(0, 2));
            int minutesStart = Integer.parseInt(myEdge.getEndTime().substring(2));
            int hoursEnd = Integer.parseInt(myNextEdge.getStartTime().substring(0, 2));
            int minutesEnd = Integer.parseInt(myNextEdge.getStartTime().substring(2));

            int totHours = 0;

            // If starting time is greater than ending time, then startTime is modded from 24 to compensate for the next
            // day, else, just subtract end and start times. Minutes is being calculated by just subtracting in both.
            if (hoursStart > hoursEnd) {
                hoursStart = 24 % hoursStart;
                totHours = hoursStart + hoursEnd;
                totHours = totHours * 60 + (minutesEnd - minutesStart);
            } else {
                totHours = hoursEnd - hoursStart;
                totHours = totHours * 60 + (minutesEnd - minutesStart);
            }
            total = total + totHours;
        }
        return total;
}

    /**
     * This method returns the total amount of time spent flying and waiting between your start and end
     * @return
     */
    @Override
    public int totalTime() {
        // returns the sum of airTime and connectingTime
        return airTime() + connectingTime();
    }
}
