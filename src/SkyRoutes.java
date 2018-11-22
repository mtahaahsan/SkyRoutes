import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import java.io.FileNotFoundException;
import java.util.*;

public class SkyRoutes implements IRoutes {
    DirectedWeightedMultigraph<String, CustomEdge> graphB = new DirectedWeightedMultigraph<String, CustomEdge>( CustomEdge.class);
    HashMap<String, String> cities = new HashMap<>();

    /**
     * This method creates a graph and populates it with strings and DefaultWeightedEdge edges
     * It first creates all the strings that need to be added, and then adds them to the graph using the addVertex() method
     * It then creates a DefaultWeightedEdge object, adds the edge, and then sets the weight correspondingly
     *
     * @return null
     */

    public static GraphPath<String, DefaultWeightedEdge> partA() {
        //Creates a simple directed weighted graph of String vertices and DefaultWeightedEdge edges
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightsGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);


        //Creates Strings with corresponding names
        String Edingburgh = "Edinburgh";
        String Heathrow = "Heathrow";
        String Dubai = "Dubai";
        String Sydney = "Sydney";
        String KualaLumpur = "Kuala Lumpur";

        //Adds the strings above as vertices in the graph
        flightsGraph.addVertex(Edingburgh);
        flightsGraph.addVertex(Heathrow);
        flightsGraph.addVertex(Dubai);
        flightsGraph.addVertex(Sydney);
        flightsGraph.addVertex(KualaLumpur);

        //Creating edge objects, adding edge, and setting edge weight
        DefaultWeightedEdge edge1 = flightsGraph.addEdge(Edingburgh,Heathrow);
        flightsGraph.setEdgeWeight(edge1, 80);

        DefaultWeightedEdge edge1rev = flightsGraph.addEdge(Heathrow,Edingburgh);
        flightsGraph.setEdgeWeight(edge1rev, 80);

        DefaultWeightedEdge edge2 = flightsGraph.addEdge(Heathrow, Dubai);
        flightsGraph.setEdgeWeight(edge2, 130);

        DefaultWeightedEdge edge2rev = flightsGraph.addEdge(Dubai, Heathrow);
        flightsGraph.setEdgeWeight(edge2rev, 130);

        DefaultWeightedEdge edge3 = flightsGraph.addEdge(Heathrow, Sydney);
        flightsGraph.setEdgeWeight(edge3, 570);

        DefaultWeightedEdge edge3rev = flightsGraph.addEdge(Sydney, Heathrow);
        flightsGraph.setEdgeWeight(edge3rev, 570);

        DefaultWeightedEdge edge4 = flightsGraph.addEdge(Dubai, KualaLumpur);
        flightsGraph.setEdgeWeight(edge4, 170);

        DefaultWeightedEdge edge4rev = flightsGraph.addEdge(KualaLumpur, Dubai);
        flightsGraph.setEdgeWeight(edge4rev, 170);

        DefaultWeightedEdge edge5 = flightsGraph.addEdge(Dubai, Edingburgh);
        flightsGraph.setEdgeWeight(edge5, 190);

        DefaultWeightedEdge edge5rev = flightsGraph.addEdge(Edingburgh, Dubai);
        flightsGraph.setEdgeWeight(edge5rev, 190);

        DefaultWeightedEdge edge6 = flightsGraph.addEdge(KualaLumpur, Sydney);
        flightsGraph.setEdgeWeight(edge6, 150);

        DefaultWeightedEdge edge6rev = flightsGraph.addEdge(Sydney, KualaLumpur);
        flightsGraph.setEdgeWeight(edge6rev, 150);

        // User enters starting and final destination
        System.out.println("Please enter your starting destination");
        Scanner startScan = new Scanner(System.in);
        String start = startScan.next();
        System.out.println("Please enter your final destination");
        Scanner endScan = new Scanner(System.in);
        String end = endScan.next();

        //Creates the shortest path, stores the edge-list and vertex-list, and prints the results
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(flightsGraph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        GraphPath<String, DefaultWeightedEdge> shortestPath = iPaths.getPath(end);
        List<String> vertexList = shortestPath.getVertexList();

        System.out.println();
        System.out.println("The cheapest path is: ");
        for (int i = 0; i < vertexList.size()-1; i++) {
            System.out.println(vertexList.get(i) + " -> " + vertexList.get(i+1));
        }
        System.out.println("\nThis Flight will cost a total of: £" + (int)iPaths.getWeight(end) + "\n");

        return shortestPath;
    }

    public static void partB() {
        SkyRoutes mySkyRoutes = new SkyRoutes();
        try {
            FlightsReader myFlight = new FlightsReader(FlightsReader.AIRLINECODES);
            HashSet<String[]> airlines = myFlight.getAirlines();
            HashSet<String[]> flights = myFlight.getFlights();
            HashSet<String[]> airports = myFlight.getAirports();
            mySkyRoutes.populate(airlines, airports, flights);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }

        List<String> exclude = new ArrayList<>();
//        exclude.add("DOH");
//        exclude.add("DXB");
        try {
            IRoute myRoute = mySkyRoutes.leastCost("EDI", "DXB");
            mySkyRoutes.leastHop("EDI", "DXB");
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    public static void partC() {
        // TO IMPLEMENT
    }

    public static void main(String[] args) {
		partA();

    }

    @Override
    public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {

        for(String[] airport:airports) {
            graphB.addVertex(airport[0]);
            cities.put(airport[0], airport[1]);
        }

        int count = 0;
        for(String[] route: routes){
            CustomEdge myEdge = new CustomEdge(route[0], route[2], route[4], route[5]);
            graphB.addEdge(route[1], route[3], myEdge);
            graphB.setEdgeWeight(myEdge, Integer.parseInt(route[5]));
            count++;
        }

        if(count==11663){
            return true;
        }
        return false;
    }

    @Override
    public IRoute leastCost(String from, String to) throws SkyRoutesException{
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath<>(graphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> mypath = iPaths.getPath(to);
        List<CustomEdge> myEdges = mypath.getEdgeList();
        List<String> myVertices = mypath.getVertexList();

        IRoute myRoute2 = new IRouteClass(myEdges, myVertices, mypath);

        System.out.printf("%s%7s%12s%9s%12s%8s", "Leg", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        int sum = 0;

        for (int i = 0; i < myVertices.size()-1; i++) {
            String vert = myVertices.get(i);
            String deptCity = cities.get(myVertices.get(i));
            String arrCity = cities.get(myVertices.get(i+1));
            System.out.printf("%-5d%-13s%-9s%-8s%-12s%-14s ",i+1, deptCity, myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), arrCity, myEdges.get(i).getEndTime());
            System.out.println("");
            sum = sum + myEdges.get(i).getCost();
        }

        System.out.println("The total cost of this trip is: £" + sum);
        System.out.println("The time spent in the air is: " + getAirTime(myEdges) + " minutes");
        System.out.println("The time spend waiting is: " + getConnectingTime(myEdges));

        return myRoute2;
    }


    public int getAirTime(List<CustomEdge> myEdges){
        int total = 0;
        for(CustomEdge myEdge:myEdges){
            int hoursStart = Integer.parseInt(myEdge.getStartTime().substring(0,2));
            int minutesStart = Integer.parseInt(myEdge.getStartTime().substring(2));
            int hoursEnd = Integer.parseInt(myEdge.getEndTime().substring(0,2));
            int minutesEnd = Integer.parseInt(myEdge.getEndTime().substring(2));

            int totHours = 0;

            if(hoursStart>hoursEnd){
                hoursStart = 24%hoursStart;
                totHours = hoursStart + hoursEnd;
                totHours = totHours*60 + (minutesEnd - minutesStart);
            }
            else{
                totHours = hoursEnd - hoursStart;
                totHours = totHours*60 + (minutesEnd-minutesStart);
            }

//            System.out.println(totHours);

            total =  total + totHours;
        }
        return total;
    }

    public int getConnectingTime(List<CustomEdge> myEdges) {
        int total = 0;
        for (int i = 0; i < myEdges.size()-1; i++) {
            CustomEdge myEdge = myEdges.get(i);
            CustomEdge myNextEdge = myEdges.get(i+1);
            int hoursStart = Integer.parseInt(myEdge.getEndTime().substring(0, 2));
            int minutesStart = Integer.parseInt(myEdge.getEndTime().substring(2));
            int hoursEnd = Integer.parseInt(myNextEdge.getStartTime().substring(0, 2));
            int minutesEnd = Integer.parseInt(myNextEdge.getStartTime().substring(2));

            int totHours = 0;

            if (hoursStart > hoursEnd) {
                hoursStart = 24 % hoursStart;
                totHours = hoursStart + hoursEnd;
                totHours = totHours * 60 + (minutesEnd - minutesStart);
            } else {
                totHours = hoursEnd - hoursStart;
                totHours = totHours * 60 + (minutesEnd - minutesStart);
            }

//            System.out.println(totHours);

            total = total + totHours;
        }
        return total;
    }

    @Override
    public IRoute leastHop(String from, String to) throws SkyRoutesException {
        AsUnweightedGraph<String, CustomEdge> unweightedGraphtB = new AsUnweightedGraph<String, CustomEdge>(graphB);
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath(unweightedGraphtB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> myGraph = iPaths.getPath(to);
        List<CustomEdge> myEdges = myGraph.getEdgeList();
        List<String> myVertices = myGraph.getVertexList();

        IRoute myRoute2 = new IRouteClass(myEdges, myVertices, myGraph);

        System.out.printf("Leg%7s%8s%6s%12s%8s", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        int sum = 0;

        for (int i = 0; i < myVertices.size()-1; i++) {
            String vert = myVertices.get(i);
            String deptCity = cities.get(myVertices.get(i));
            String arrCity = cities.get(myVertices.get(i+1));
            System.out.printf(i+1 +"%13s%9s%8s%8s%10s", deptCity, myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), arrCity, myEdges.get(i).getEndTime());
            System.out.println("");
            sum = sum + myEdges.get(i).getCost();
        }

        System.out.println("The total cost of this trip is: £" + sum);
        System.out.println("The time spent in the air is: " + getAirTime(myEdges) + " minutes");
//        System.out.println("The time spend waiting is: " + getConnectingTime(myEdges));

        return myRoute2;
    }

    @Override
    public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
        for(String exclude:excluding){
            graphB.removeVertex(exclude);
        }

        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath<>(graphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);

        GraphPath<String, CustomEdge> mypath = iPaths.getPath(to);

        List<String> myVertices = mypath.getVertexList();
        List<CustomEdge> myEdges = mypath.getEdgeList();
        int sum = 0;

        System.out.printf("Leg%7s%8s%6s%12s%8s", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        for (int i = 0; i < myVertices.size()-1; i++) {
            String vert = myVertices.get(i);
            System.out.printf(i+1 +"%7s%12s%8s%5s%13s", myVertices.get(i), myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), myVertices.get(i+1), myEdges.get(i).getEndTime());
            System.out.println("");
            sum = sum + myEdges.get(i).getCost();
        }

        System.out.println("The total cost of this trip is: £" + mypath.getWeight());
        System.out.println("The time spent in the air is: " + getAirTime(myEdges) + " minutes");
        return null;
    }

    @Override
    public IRoute leastHop(String from, String to, List<String> excluding) throws SkyRoutesException {
        AsUnweightedGraph<String, CustomEdge> unweightedGraphtB = new AsUnweightedGraph<String, CustomEdge>(graphB);
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath(unweightedGraphtB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> myGraph = iPaths.getPath(to);
        List<CustomEdge> myEdges = myGraph.getEdgeList();
        List<String> myVertices = myGraph.getVertexList();

        for(String exclude:excluding){
            unweightedGraphtB.removeVertex(exclude);
        }

        IRoute myRoute2 = new IRouteClass(myEdges, myVertices, myGraph);

        System.out.printf("Leg%7s%8s%6s%12s%8s", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        int sum = 0;

        for (int i = 0; i < myVertices.size()-1; i++) {
            String vert = myVertices.get(i);
            String deptCity = cities.get(myVertices.get(i));
            String arrCity = cities.get(myVertices.get(i+1));
            System.out.printf(i+1 +"%13s%9s%8s%8s%10s", deptCity, myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), arrCity, myEdges.get(i).getEndTime());
            System.out.println("");
            sum = sum + myEdges.get(i).getCost();
        }

        System.out.println("The total cost of this trip is: £" + sum);
        System.out.println("The time spent in the air is: " + getAirTime(myEdges) + " minutes");
//        System.out.println("The time spend waiting is: " + getConnectingTime(myEdges));

        return myRoute2;
    }

    @Override
    public String leastCostMeetUp(String at1, String at2) throws SkyRoutesException {
        return null;
    }

    @Override
    public String leastHopMeetUp(String at1, String at2) throws SkyRoutesException {
        return null;
    }

    @Override
    public String leastTimeMeetUp(String at1, String at2, String startTime) throws SkyRoutesException {
        return null;
    }

    @Override
    public List<IRoute> allRoutesCost(String from, String to, List<String> excluding, int maxCost) throws SkyRoutesException {
        return null;
    }

    @Override
    public List<IRoute> allRoutesHop(String from, String to, List<String> excluding, int maxHop) throws SkyRoutesException {
        return null;
    }
}