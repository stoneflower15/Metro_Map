import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    List<Station> route;
    List<Station> routeActual;
    ArrayList<Station> expected;
    List<Station> actual;
    StationIndex stationIndex = new StationIndex();
    RouteCalculator testCalculator = new RouteCalculator(stationIndex);

    @Override
    protected void setUp() throws Exception {

 /*                      ___Дубравная____Слобода___Лагерная
                                            |
                                        Набережная
                                            |
                                          Горки
                                            |
                                          Ометьево
                                            |
                                         Кремлёвская
                                             ___Авиастроительный завод___ посёлок Залесный
                                            |
                                          Осиново
*/

        Line line1 = new Line(1, "Красная");
        line1.addStation(new Station("Дубравная", line1));
        line1.addStation(new Station("Слобода", line1));
        line1.addStation(new Station("Лагерная", line1));
        stationIndex.addStation(new Station("Дубравная", line1));
        stationIndex.addStation(new Station("Слобода", line1));
        stationIndex.addStation(new Station("Лагерная", line1));

        Line line2 = new Line(2, "Зеленая");
        line2.addStation(new Station("Набережная", line2));
        line2.addStation(new Station("Горки", line2));
        line2.addStation(new Station("Ометьево", line2));
        line2.addStation(new Station("Кремлёвская", line2));
        stationIndex.addStation(new Station("Набережная", line2));
        stationIndex.addStation(new Station("Горки", line2));
        stationIndex.addStation(new Station("Ометьево", line2));
        stationIndex.addStation(new Station("Кремлёвская", line2));

        Line line3 = new Line(3, "Желтая");
        line3.addStation(new Station("Авиастроительный завод", line3));
        line3.addStation(new Station("посёлок Залесный", line3));
        line3.addStation(new Station("Осиново", line3));
        stationIndex.addStation(new Station("Авиастроительный завод", line3));
        stationIndex.addStation(new Station("посёлок Залесный", line3));
        stationIndex.addStation(new Station("Осиново", line3));

        List<Station> connections1 = new ArrayList<>();
        List<Station> connections2 = new ArrayList<>();
        connections1.add(stationIndex.getStation("Слобода"));
        connections1.add(stationIndex.getStation("Набережная"));
        connections2.add(stationIndex.getStation("Кремлёвская"));
        connections2.add(stationIndex.getStation("Авиастроительный завод"));
        stationIndex.addConnection(connections1);
        stationIndex.addConnection(connections2);

        route = new ArrayList<>();
        route.add(stationIndex.getStation("Дубравная"));
        route.add(stationIndex.getStation("Слобода"));
        route.add(stationIndex.getStation("Набережная"));
        route.add(stationIndex.getStation("Горки"));
        route.add(stationIndex.getStation("Ометьево"));
        route.add(stationIndex.getStation("Кремлёвская"));
        route.add(stationIndex.getStation("Авиастроительный завод"));
        route.add(stationIndex.getStation("посёлок Залесный"));
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 19.5;
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteOnTheLine() {
        Station from = stationIndex.getStation("Дубравная");
        Station to = stationIndex.getStation("Лагерная");
        routeActual = testCalculator.getShortestRoute(from, to);
        actual = new ArrayList<Station>();
        actual.addAll(routeActual);
        expected = new ArrayList<Station>();
        expected.add(0, stationIndex.getStation("Дубравная"));
        expected.add(1, stationIndex.getStation("Слобода"));
        expected.add(2, stationIndex.getStation("Лагерная"));
        assertEquals(3, routeActual.size());
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithOneConnections() {
        Station from = stationIndex.getStation("Дубравная");
        Station to = stationIndex.getStation("Горки");
        routeActual = testCalculator.getShortestRoute(from, to);
        actual = new ArrayList<Station>();
        actual.addAll(routeActual);
        expected = new ArrayList<Station>();
        expected.add(0, stationIndex.getStation("Дубравная"));
        expected.add(1, stationIndex.getStation("Слобода"));
        expected.add(2, stationIndex.getStation("Набережная"));
        expected.add(3, stationIndex.getStation("Горки"));
        assertEquals(4, routeActual.size());
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithTwoConnections() {
        Station from = stationIndex.getStation("Дубравная");
        Station to = stationIndex.getStation("Авиастроительный завод");
        routeActual = testCalculator.getShortestRoute(from, to);
        actual = new ArrayList<Station>();
        actual.addAll(routeActual);
        expected = new ArrayList<Station>();
        expected.add(0, stationIndex.getStation("Дубравная"));
        expected.add(1, stationIndex.getStation("Слобода"));
        expected.add(2, stationIndex.getStation("Набережная"));
        expected.add(3, stationIndex.getStation("Горки"));
        expected.add(4, stationIndex.getStation("Ометьево"));
        expected.add(5, stationIndex.getStation("Кремлёвская"));
        expected.add(6, stationIndex.getStation("Авиастроительный завод"));
        System.out.println(routeActual);
        assertEquals(7, routeActual.size());
        assertEquals(expected, actual);
    }
}