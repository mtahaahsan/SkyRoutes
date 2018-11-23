import static org.junit.Assert.*;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class SkyRoutesTest {

    IRoutes fi = new SkyRoutes();
    FlightsReader fr;

    @Before
    public void initialize() {
        try {
            fr = new FlightsReader(FlightsReader.AIRLINECODES);
            fi.populate(fr.getAirlines(), fr.getAirports(), fr.getFlights());
        } catch (FileNotFoundException | SkyRoutesException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void partA() {
        GraphPath<String, DefaultWeightedEdge> testPath = SkyRoutes.partA("Dubai", "Sydney");
        assertEquals(320,(int) testPath.getWeight());
        assertEquals("Dubai", testPath.getStartVertex());
        assertEquals("Sydney", testPath.getEndVertex());
        assertEquals(2, testPath.getEdgeList().size());
    }

    @Test
    public void testLeastCost() {
        try {
            IRoute route = fi.leastCost("KUL", "SYD");
            assertEquals(692, route.totalCost());
        } catch (SkyRoutesException e) {
            fail();
        }

    }

    @Test
    public void testAirTime() {
        try {
            IRoute route = fi.leastCost("KUL", "SYD");
            assertEquals(680, route.airTime());
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectingTime() {
        try {
            IRoute route = fi.leastCost("KUL", "SYD");
            assertEquals(1845, route.connectingTime());
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLeastHops() {
        try {
            IRoute route = fi.leastHop("KUL", "SYD");
            assertEquals(2, route.totalHop());
            assertEquals(1184, route.totalCost());
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTotalTime() {
        try {
            IRoute route = fi.leastHop("KUL", "SYD");
            assertEquals(1890, route.totalTime());
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLeastCostExcluding() {
        List<String> exclude = new ArrayList<>();
        exclude.add("MEL");
        try {
            IRoute route = fi.leastCost("KUL", "SYD",exclude);
            assertEquals(1170, route.totalCost());
            assertEquals(1121, route.airTime());
            assertEquals(308, route.connectingTime());
        } catch (SkyRoutesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPopulate() {
        try {
            fr = new FlightsReader(FlightsReader.MOREAIRLINECODES);
            Boolean statement = fi.populate(fr.getAirlines(), fr.getAirports(), fr.getFlights());
            assertTrue(statement ==  true);
        } catch (FileNotFoundException | SkyRoutesException e) {
            e.printStackTrace();
            fail();
        }
    }

    }


