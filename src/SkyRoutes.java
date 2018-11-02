import org.jgrapht.Graph;

import java.util.HashSet;
import java.util.List;

public class SkyRoutes implements IRoutes {

	// TO IMPLEMENT

	public static void partA() {
		// TO IMPLEMENT
	}

	public static void partB() {
		// TO IMPLEMENT
	}

	public static void partC() {
		// TO IMPLEMENT
	}

	public static void main(String[] args) {
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
