import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import sun.security.provider.certpath.Vertex;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SkyRoutes implements IRoutes {

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

		System.out.println("Your flight path will be: \n" );
		String temp = iPaths.getPath(end).toString();
		myToString(temp);
		System.out.println("\nThis Flight will cost a total of: £" + iPaths.getWeight(end) + "\n");

		return flights;

		// TO IMPLEMENT
	}

	private static void myToString(String temp) {
		int start = 0;
		StringBuffer myTemp = new StringBuffer(temp);
		myTemp.delete(0,1);
		myTemp.delete(myTemp.length()-1, myTemp.length());
		myTemp.replace(0,1,"");

		for (int i = 0; i < myTemp.length(); i++) {
			if(myTemp.charAt(i) == '(' || myTemp.charAt(i) == ')'){
				myTemp.replace(i,i+1, " ");
			}

			if(myTemp.charAt(i) != ' ' && myTemp.charAt(i) == ':'){
				myTemp.replace(i,i+1, "->");
			}

			if(myTemp.charAt(i) == ','){
				myTemp.replace(i,i+3, "\n");
			}




		}
		System.out.println(myTemp);
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
//		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flights = partA();
//		Scanner startscan = new Scanner(System.in);
//		String start = startscan.next();
//		Scanner endscan = new Scanner(System.in);
//		String end = endscan.next();

		partA("Kuala Lumpur", "Pakistan");

		/**For loop Taken from Stack Overflow**/
		

		// TODO Auto-generated method stub
	}

	@Override
	public boolean populate(HashSet<String[]> airlines, HashSet<String[]> airports, HashSet<String[]> routes) {
		return false;
	}

	@Override
	public IRoute leastCost(String from, String to) throws SkyRoutesException {
		return null;
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
