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

    SkyRoutes sky = new SkyRoutes();


    @Override
    public List<String> getStops() {
        return mypath.getVertexList();
    }

    @Override
    public List<String> getFlights() {
        List<String> flightCodes = null;
        for (CustomEdge edge:myEdges) {
            flightCodes.add(edge.getFlightNum());

        }
        return flightCodes;
    }

    @Override
    public int totalHop() {
        int count = 0;
        for(CustomEdge edge:myEdges){
            count = count + 1;
            System.out.println(count);
        }
        return count;
    }

    @Override
    public int totalCost() {
        int sum = 0 ;
        for(CustomEdge edge:myEdges){
            sum = sum + edge.getCost();
        }
        return sum;
    }

    @Override
    public int airTime() {
        return sky.getAirTime(myEdges);
    }

    @Override
    public int connectingTime() {
        sky.getConnectingTime(myEdges);
        return 0;
}

    @Override
    public int totalTime() {
//        return getAirTime(myEdges) + getConnectingTime(myEdges);
        return 0;
    }
}
