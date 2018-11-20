import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.FileNotFoundException;
import java.util.*;

public class SkyRoutes implements IRoutes {
    DefaultDirectedWeightedGraph<String, CustomEdge> graphB = new DefaultDirectedWeightedGraph<String, CustomEdge>( CustomEdge.class);

    public static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> partA() {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flights = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        String Edingburgh = "Edinburgh";
        String Heathrow = "Heathrow";
        String Dubai = "Dubai";
        String Sydney = "Sydney";
        String KualaLumpur = "Kuala Lumpur";

        flights.addVertex(Edingburgh);
        flights.addVertex(Heathrow);
        flights.addVertex(Dubai);
        flights.addVertex(Sydney);
        flights.addVertex(KualaLumpur);

        DefaultWeightedEdge edge1 = flights.addEdge(Edingburgh,Heathrow);
        flights.setEdgeWeight(edge1, 80);

        DefaultWeightedEdge edge1rev = flights.addEdge(Heathrow,Edingburgh);
        flights.setEdgeWeight(edge1rev, 80);

        DefaultWeightedEdge edge2 = flights.addEdge(Heathrow, Dubai);
        flights.setEdgeWeight(edge2, 130);

        DefaultWeightedEdge edge2rev = flights.addEdge(Dubai, Heathrow);
        flights.setEdgeWeight(edge2rev, 130);

        DefaultWeightedEdge edge3 = flights.addEdge(Heathrow, Sydney);
        flights.setEdgeWeight(edge3, 570);

        DefaultWeightedEdge edge3rev = flights.addEdge(Sydney, Heathrow);
        flights.setEdgeWeight(edge3rev, 570);

        DefaultWeightedEdge edge4 = flights.addEdge(Dubai, KualaLumpur);
        flights.setEdgeWeight(edge4, 170);

        DefaultWeightedEdge edge4rev = flights.addEdge(KualaLumpur, Dubai);
        flights.setEdgeWeight(edge4rev, 170);

        DefaultWeightedEdge edge5 = flights.addEdge(Dubai, Edingburgh);
        flights.setEdgeWeight(edge5, 190);

        DefaultWeightedEdge edge5rev = flights.addEdge(Edingburgh, Dubai);
        flights.setEdgeWeight(edge5rev, 190);

        DefaultWeightedEdge edge6 = flights.addEdge(KualaLumpur, Sydney);
        flights.setEdgeWeight(edge6, 150);

        DefaultWeightedEdge edge6rev = flights.addEdge(Sydney, KualaLumpur);
        flights.setEdgeWeight(edge6rev, 150);

        System.out.println("Please enter your starting destination");
        Scanner startscan = new Scanner(System.in);
        String start = startscan.next();
        System.out.println("Please enter your final destination");
        Scanner endscan = new Scanner(System.in);
        String end = endscan.next();

        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(flights);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(start);
        GraphPath<String, DefaultWeightedEdge> mypath = iPaths.getPath(end);
        List<String> mylist = mypath.getVertexList();

        System.out.println();
        System.out.println("The cheapest path is: ");
        for (int i = 0; i < mylist.size()-1; i++) {
            System.out.println(mylist.get(i) + " -> " + mylist.get(i+1));
        }

        System.out.println("\nThis Flight will cost a total of: £" + iPaths.getWeight(end) + "\n");

        return flights;
    }

    public static void partB() {
        SkyRoutes mySkyRoutes = new SkyRoutes();
        try {
            FlightsReader myFlight = new FlightsReader(FlightsReader.MOREAIRLINECODES);
            HashSet<String[]> airlines = myFlight.getAirlines();
            HashSet<String[]> flights = myFlight.getFlights();
            HashSet<String[]> airports = myFlight.getAirports();
            System.out.println(mySkyRoutes.populate(airlines, airports, flights));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }

        List<String> exclude = new ArrayList<>();
//        exclude.add("DOH");
//        exclude.add("DXB");
        try {
            IRoute myRoute = mySkyRoutes.leastCost("JFK", "LHE");
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    public static void partC() {
        // TO IMPLEMENT
    }

    public static void main(String[] args) {
//		partA();
        partB();

    }

    @Override
    public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {

        for(String[] airport:airports) {
            graphB.addVertex(airport[0]);
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

        System.out.printf("Leg%7s%8s%6s%12s%8s", "Leave","At", "On", "Arrive", "At");
        System.out.println("");

        int sum = 0;

        for (int i = 0; i < myVertices.size()-1; i++) {
            String vert = myVertices.get(i);
            System.out.printf(i+1 +"%7s%12s%8s%5s%13s", myVertices.get(i), myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), myVertices.get(i+1), myEdges.get(i).getEndTime());
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

            System.out.println(totHours);

            total =  total + totHours;
        }
        return total;
    }

    public int getConnectingTime(List<CustomEdge> myEdges){
        int total = 0;
        for (int i = 0; i < myEdges.size()-1; i++) {
            CustomEdge myEdge = myEdges.get(i);
            CustomEdge nextEdge = myEdges.get(i+1);
            int startHours = Integer.parseInt(myEdge.getEndTime().substring(0,2));
            int startMinutes = Integer.parseInt(myEdge.getEndTime().substring(2));
            int endHours = Integer.parseInt(nextEdge.getStartTime().substring(0,2));
            int endMinutes = Integer.parseInt(nextEdge.getStartTime().substring(2));

            int totHours = 0;

            if(startHours + endHours > 24){
                totHours = (startHours + startHours)%24;
                totHours = totHours * 60;
                totHours = totHours + (endMinutes - startMinutes);
            }
            else{
                totHours = startHours - endHours;
                totHours = (totHours * 60) + (endMinutes - startMinutes);
            }


            total =  total + totHours;
        }
        return total;
    }

    @Override
    public IRoute leastHop(String from, String to) throws SkyRoutesException {

        return null;
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
        return null;
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