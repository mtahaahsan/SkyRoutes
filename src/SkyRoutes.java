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
     * @param start (Start Airport)
     * @param end (End Airport)
     * @return void
     */
    public static GraphPath<String, DefaultWeightedEdge> partA(String start, String end) {
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

        //Creates the shortest path, stores the edge-list and vertex-list, and prints the results
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(flightsGraph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        GraphPath<String, DefaultWeightedEdge> shortestPath = iPaths.getPath(end);
        List<String> vertexList = shortestPath.getVertexList();

        System.out.println("\nThe cheapest path is: ");
        for (int i = 0; i < vertexList.size()-1; i++) {
            System.out.println(vertexList.get(i) + " -> " + vertexList.get(i+1));
        }
        System.out.println("\nThis flight will cost a total of: £" + (int)iPaths.getWeight(end) + "\n");

        return shortestPath;
    }

    /**
     * This method allows you to find the simple least cost by first populating the graph and then calling the
     * leastCost() method and passing the right parameters
     *
     * @param start (Start Airport)
     * @param end (End Airport)
     */
    public static void partB(String start, String end) {
        // Creates a new instance of SkyRoutes
        SkyRoutes mySkyRoutes = new SkyRoutes();

        // Gets the HashSets from the FlightsReader class
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

        List<String> exclude = new ArrayList<String>();
        exclude.add("MEL");
//        exclude.add("DXB");

        try {
            mySkyRoutes.leastCost(start, end);
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method first populates the graph lets you choose with part within PartC you want to choose, and
     * correspondingly asks you to enter values, and then calls the method you chose
     */
    public static void partC() {
        SkyRoutes mySkyRoutes = new SkyRoutes();

        // Gets the HashSets from the FlightsReader class
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

        List<String> exclude = new ArrayList<String>();
        List<String> excludeLHE = new ArrayList<String>();

        System.out.println("What would you like to do?\nLeast Cost Excluding(LCE)\nLeast Hops(LH)\nLeast Hops Excluding(LHE)");
        System.out.println("Please enter your starting destination code");
        Scanner choiceScan = new Scanner(System.in);
        String choice = choiceScan.next();

        if(choice.equals("LCE") ||choice.equals("lce")||choice.equals("Lce")){
            String start = startVal();
            String end = endVal();
            System.out.println("Please enter the city codes you'd like to avoid, type 'end' when you're done");
            String excluded = "";
            while(!excluded.equals("end")){
                Scanner excludeScan = new Scanner(System.in);
                excluded = excludeScan.next();
                exclude.add(excluded);
            }
            try {
                mySkyRoutes.leastCost(start, end, exclude);
            } catch (SkyRoutesException e) {
                e.printStackTrace();
            }

            if(choice.equals("LH") ||choice.equals("lh")||choice.equals("lH")){
                String lhStart = startVal();
                String lhEnd = endVal();
                try {
                    mySkyRoutes.leastHop(lhStart, lhEnd);
                } catch (SkyRoutesException e) {
                    e.printStackTrace();
                }
            }

            if(choice.equals("LHE") ||choice.equals("lhe")||choice.equals("Lhe")){
                String startLHE = startVal();
                String endLHE = endVal();
                System.out.println("Please enter the city codes you'd like to avoid, type 'end' when you're done");
                String excludedLHE = "";
                while(!excludedLHE.equals("end")){
                    Scanner excludeScan = new Scanner(System.in);
                    excluded = excludeScan.next();
                    exclude.add(excluded);
                }
                try {
                    mySkyRoutes.leastCost(startLHE, endLHE, excludeLHE);
                } catch (SkyRoutesException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Not a valid option");
        }
    }

    /**
     * This method lets you choose which part you want to run
     *
     * @return void
     */
    public static void main(String[] args) {
        System.out.println("Please select with part you would like try: ");
        System.out.println("Choose from the following parts: \nPartA \nPartB \nPartC");
        Scanner partScan = new Scanner(System.in);
        String part = partScan.next();

        if(part.equals("PartA") || part.equals("parta") || part.equals("partA")){
            String start = startVal();
            String end = endVal();
            partA(start, end);
        }
        if(part.equals("PartB") || part.equals("partb") || part.equals("partB") || part.equals("PARTB")){
            String start = startVal();
            String end = endVal();
            partB(start, end);
        }
        if(part.equals("PartC") || part.equals("partc") || part.equals("partC") || part.equals("PARTC")){
            partC();
        }
    }

    /**
     * This method takes in three values and uses them to populate the graph by adding each value in the airports as the
     * vertex in the graph, and then uses the routes HashSet to add the edges with the values in the HashSet.
     * @param airlines
     * @param airports
     * @param routes
     * @return
     */
    @Override
    public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {
        // Adds every value inside airports HashSet to the graph, and creates a HashMap to be able to convert city codes
        // to city names
        for(String[] airport:airports) {
            graphB.addVertex(airport[0]);
            cities.put(airport[0], airport[1]);
        }

        // Creates a instance of CustomEdge and passes the values from routes HashSet as parameters for the CustomEdge.
        // It then adds the edge with the corresponding vertices, and then sets the weight for each edge. Counter is
        // there to keep track of how many edges are added
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

    /**
     * This method creates the shortest path algorithm and uses it on the graph with the entered from and to, it then
     * creates a list of vertices and a list of edges. It passes the edges, vertices, and the shortest path as
     * parameters for IRouteClass and then displays the information
     *
     * @param from (Start Airport)
     * @param to (End Airport)
     * @return iRoute
     */
    @Override
    public IRoute leastCost(String from, String to) throws SkyRoutesException{
        // Creates the shortest path and a list of edges and a list of vertices
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath<>(graphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> shortestPath = iPaths.getPath(to);
        List<CustomEdge> myEdges = shortestPath.getEdgeList();
        List<String> myVertices = shortestPath.getVertexList();

        IRoute iRoute = new IRouteClass(myEdges, myVertices, shortestPath);

        display(myVertices, myEdges, iRoute);
        return iRoute;
    }

    /**
     * This method converts graphB into a AsUnweightedGraph to find least hops and then creates the shortest path
     * algorithm and uses it on the graph with the entered from and to, it then creates a list of vertices and a list
     * of edges. It passes the edges, vertices, and the shortest path as parameters for IRouteClass and then displays
     * the information
     *
     * @param from (Start Airport)
     * @param to (End Airport)
     * @return iRoute
     */
    @Override
    public IRoute leastHop(String from, String to) throws SkyRoutesException {
        // Converts graphB into a AsUnweightedGraph, and then creates the shortest path and a list of edges and a
        // list of vertices
        AsUnweightedGraph<String, CustomEdge> unweightedGraphB = new AsUnweightedGraph(graphB);
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath(unweightedGraphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> shortestPath = iPaths.getPath(to);
        List<CustomEdge> myEdges = shortestPath.getEdgeList();
        List<String> myVertices = shortestPath.getVertexList();

        IRoute iRoute = new IRouteClass(myEdges, myVertices, shortestPath);

        display(myVertices, myEdges, iRoute);

        return iRoute;
    }

    /**
     * This method first uses the list of indices that need to be removed to remove the vertices from the graph, and then
     * creates the shortest path algorithm and uses it on the graph with the entered from and to, it then
     * creates a list of vertices and a list of edges. It passes the edges, vertices, and the shortest path as
     * parameters for IRouteClass and then displays the information
     *
     * @param from (Start Airport)
     * @param to (End Airport)
     * @return iRoute
     */
    @Override
    public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
        // For each String in the excluding list, remove that string from the graph
        for(String exclude:excluding){
                graphB.removeVertex(exclude);
            }

        // Creates the shortest path and a list of edges and a list of vertices
        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath<>(graphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> shortestPath = iPaths.getPath(to);
        List<String> myVertices = shortestPath.getVertexList();
        List<CustomEdge> myEdges = shortestPath.getEdgeList();

        IRoute iRoute = new IRouteClass(myEdges, myVertices, shortestPath);

        display(myVertices, myEdges, iRoute);

        return iRoute;
    }

    /**
     * This method first uses the list of indices that need to be removed to remove the vertices from the graph, and then
     * converts graphB into a AsUnweightedGraph to find least hops and then creates the shortest path
     * algorithm and uses it on the graph with the entered from and to, it then creates a list of vertices and a list
     * of edges. It passes the edges, vertices, and the shortest path as parameters for IRouteClass and then displays
     * the information
     * @param from
     * @param to
     * @param excluding
     * @return
     * @throws SkyRoutesException
     */
    @Override
    public IRoute leastHop(String from, String to, List<String> excluding) throws SkyRoutesException {
        // Converts graphB into a AsUnweightedGraph, and then creates the shortest path and a list of edges and a
        // list of vertices
        AsUnweightedGraph<String, CustomEdge> unweightedGraphB = new AsUnweightedGraph<String, CustomEdge>(graphB);

        // For each String in the excluding list, remove that string from the graph
        for(String exclude:excluding){
            unweightedGraphB.removeVertex(exclude);
        }

        DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath(unweightedGraphB);
        ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);
        GraphPath<String, CustomEdge> shortestPath = iPaths.getPath(to);
        List<CustomEdge> myEdges = shortestPath.getEdgeList();
        List<String> myVertices = shortestPath.getVertexList();

        IRoute iRoute = new IRouteClass(myEdges, myVertices, shortestPath);

        display(myVertices, myEdges, iRoute);

        return iRoute;
    }

    /**
     * This method is purely there to avoid repeated code, it formats the code and prints it out. This method is called
     * at the end of the above four methods
     *
     * @param myVertices
     * @param myEdges
     * @param iRoute
     */
    private void display(List<String> myVertices, List<CustomEdge> myEdges, IRoute iRoute){
        System.out.printf("\n%s%7s%12s%7s%12s%12s", "Leg", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        // Printing the leg, departure airport, departure time, flight no., arrival airport, arrival time
        for (int i = 0; i < myVertices.size()-1; i++) {
            String deptCity = cities.get(myVertices.get(i));
            String arrCity = cities.get(myVertices.get(i+1));
            System.out.printf("%-5d%-15s%-7s%-8s%-16s%-10s ",i+1, deptCity, myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), arrCity, myEdges.get(i).getEndTime());
            System.out.println("");
        }

        // Printing the times and cost
        System.out.println("\nThe total cost of this trip is: £" + iRoute.totalCost());
        System.out.println("The time spent in the air is: " + iRoute.airTime() + " minutes");
        System.out.println("The time spend waiting is: " + iRoute.connectingTime() + " minutes");
        System.out.println("Total time travelling is: " + iRoute.totalTime() + " minutes");
    }

    /**
     * This method is to avoid repeated code for when entering starting destinations
     * @return
     */
    private static String startVal(){
        System.out.println("Please enter your starting destination code");
        Scanner startScan = new Scanner(System.in);
        return startScan.next();

    }

    /**
     * This method is to avoid repeated code with entering the end destination
     * @return
     */
    private static String endVal(){
        System.out.println("Please enter your final destination code");
        Scanner endScan = new Scanner(System.in);
        return endScan.next();
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