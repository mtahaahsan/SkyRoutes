	import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
	import org.jgrapht.GraphPath;
	import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
		import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
		import org.jgrapht.graph.DefaultWeightedEdge;
		import org.jgrapht.graph.SimpleDirectedWeightedGraph;
		import sun.security.provider.certpath.Vertex;

	import java.util.Formatter;
	import java.io.FileNotFoundException;
	import java.util.HashSet;
		import java.util.List;
		import java.util.Scanner;
		import java.util.Set;

public class SkyRoutes implements IRoutes {
	SimpleDirectedWeightedGraph<String, CustomEdge> graphB = new SimpleDirectedWeightedGraph<String, CustomEdge>(CustomEdge.class);

	// TO IMPLEMENT


	public static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> partA(String start, String end) {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flights = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		String Edingburgh = "Edinburgh";
		String Heathrow = "Heathrow";
		String Dubai = "Dubai";
		String Sydney = "Sydney";
		String KualaLumpur = "Kuala Lumpur";
		String Pakistan = "Pakistan";

		flights.addVertex(Edingburgh);
		flights.addVertex(Heathrow);
		flights.addVertex(Dubai);
		flights.addVertex(Sydney);
		flights.addVertex(KualaLumpur);
		flights.addVertex(Pakistan);

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

		DefaultWeightedEdge edgePak = flights.addEdge(Edingburgh, Pakistan);
		flights.setEdgeWeight(edgePak, 250);

//		for(DefaultWeightedEdge e : flights.edgeSet()){
//			System.out.println(flights.getEdgeSource(e) + " --> " + flights.getEdgeTarget(e));
//		}
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

		// TO IMPLEMENT
	}

//	@Override
//	public String toString() {
//		return
//	}

	public static void partB() {
		// TO IMPLEMENT

	}

	public static void partC() {
		// TO IMPLEMENT
	}

	public static void main(String[] args) {

		SkyRoutes mySkyRoutes = new SkyRoutes();
//		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flights = partA();
//		System.out.println("Please enter your starting destination");
//		Scanner startscan = new Scanner(System.in);
//		String start = startscan.next();
//		System.out.println("Please enter your final destination");
//		Scanner endscan = new Scanner(System.in);
//		String end = endscan.next();
//		partA(start, end);
		String[] myAirlines = {"BA"};

		try {
			FlightsReader myFlight = new FlightsReader(FlightsReader.MOREAIRLINECODES);
			HashSet<String[]> airlines = myFlight.getAirlines();
			HashSet<String[]> flights = myFlight.getFlights();
			HashSet<String[]> airports = myFlight.getAirports();
			mySkyRoutes.populate(airlines, airports, flights);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}

		try {
			mySkyRoutes.leastCost("LHR", "ISB");
		} catch (SkyRoutesException e) {
			e.printStackTrace();
		}


//		partA("Kuala Lumpur", "Pakistan");

		/**For loop Taken from Stack Overflow**/


		// TODO Auto-generated method stub
	}

	@Override
	public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {
//		System.out.println(airports.size());

		for(String[] airport:airports) {
			graphB.addVertex(airport[0]);
		}

		for(String[] route: routes){
			CustomEdge<String> myEdge = new CustomEdge<>(route[0], route[2], route[4], route[5]);
			graphB.addEdge(route[1], route[3], new CustomEdge(route[0], route[2], route[4], route[5]));
//			System.out.println(routes.size());
		}

//				for(CustomEdge e : graphB.edgeSet()){
//			System.out.println(graphB.getEdgeSource(e) + " --> " + graphB.getEdgeTarget(e) + " The weight is " + e.getCost() + " Flight no. is " + e.getFlightNum());
//		}

		return false;
	}

	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException{
		DijkstraShortestPath<String, CustomEdge> dijkstraAlg = new DijkstraShortestPath<>(graphB);
		ShortestPathAlgorithm.SingleSourcePaths<String, CustomEdge> iPaths = dijkstraAlg.getPaths(from);

		GraphPath<String, CustomEdge> mypath = iPaths.getPath(to);

		List<String> mylist = mypath.getVertexList();
		List<CustomEdge> myEdges = mypath.getEdgeList();
		int sum = 0;

		System.out.printf("Leg%7s%8s%6s%12s%8s", "Leave","At", "On", "Arrive", "At");
		System.out.println("");

//		System.out.printf("Leg");
//		System.out.printf("%7s", "Leave");
//		System.out.printf("%8s", "At");
//		System.out.printf("%5s", "On");
//		System.out.printf("%8s", "Arrive");
//		System.out.printf("%8s", "At");

		for (int i = 0; i < mylist.size()-1; i++) {
//			System.out.println(mylist.get(i) + " -> " + mylist.get(i+1));
			System.out.printf(i+1 +"%7s%12s%8s%5s%13s", mylist.get(i), myEdges.get(i).getStartTime(), myEdges.get(i).getFlightNum(), mylist.get(i+1), myEdges.get(i).getEndTime());
			System.out.println("");
			sum = sum + myEdges.get(i).getCost();
		}

//		System.out.println("");
		getAirTime(myEdges);
		System.out.println("The total cost of this trip is: £" + sum);
		return null;
	}

	public void getAirTime(List<CustomEdge> myEdges){
		int total = 0;
		for(CustomEdge myEdge:myEdges){
			int timeStart1 = Integer.parseInt(myEdge.getStartTime().substring(0,2));
			int timeStart2 = Integer.parseInt(myEdge.getStartTime().substring(2));
			int timeEnd1 = Integer.parseInt(myEdge.getEndTime().substring(0,2));
			int timeEnd2 = Integer.parseInt(myEdge.getEndTime().substring(2));

			System.out.println(timeStart1 +" "+timeStart2 + " ,"+ timeEnd1 + " " + timeEnd2);



//			int start = Integer.parseInt(myEdge.getStartTime());
//			int end =  Integer.parseInt(myEdge.getEndTime());
//			System.out.println(start + " " + end);

		}
	}

	@Override
	public IRoute leastHop(String from, String to) throws SkyRoutesException {
		return null;
	}

	@Override
	public IRoute leastCost(String from, String to, List<String> excluding) throws SkyRoutesException {
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
