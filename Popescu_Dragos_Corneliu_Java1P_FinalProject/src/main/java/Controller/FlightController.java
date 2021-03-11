package Controller;

import dao.FlightDao;
import model.Flight;

import java.util.List;
import java.util.Optional;

public class FlightController {

    private static final class Singleton{
        public static final FlightController INSTANCE = new FlightController();
    }

    private final FlightDao flightDao;

    private FlightController() {
        flightDao = new FlightDao(ConnectionManager.getInstance().getConnection());
    }

    public static FlightController getInstance() {
        return Singleton.INSTANCE;
    }

    public boolean create(Flight f) {
        return flightDao.add(f);
    }

    public boolean removeAll() {
        return flightDao.deleteAll();
    }

    public boolean remove(int id) {
        return flightDao.delete(id);
    }

    public Optional<Flight> findBySrcAndDest(String sursa, String dest) {
        return flightDao.findBySourceAndDestination(sursa, dest);
    }

    public List<Flight> select(){
        return flightDao.selectAll();
    }
}
