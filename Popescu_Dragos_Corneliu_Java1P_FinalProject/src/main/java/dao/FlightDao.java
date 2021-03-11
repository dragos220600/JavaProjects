package dao;

import model.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao {

    private PreparedStatement addFlight;
    private PreparedStatement deleteFlight;
    private PreparedStatement deleteAllFlights;
    private PreparedStatement findBySourceAndDest;
    private PreparedStatement selectAll;

    public FlightDao(Connection connection) {

        try {
            addFlight = connection.prepareStatement("INSERT INTO flights VALUES (null, ?, ?, ?, ?, ?, ?)");
            deleteFlight = connection.prepareStatement("DELETE FROM flights WHERE id = ?");
            deleteAllFlights = connection.prepareStatement("DELETE FROM flights WHERE 1 = 1");
            findBySourceAndDest = connection.prepareStatement("SELECT * FROM flights WHERE (source = ? AND dest = ?)");
            selectAll = connection.prepareStatement("SELECT * FROM flights");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean add(Flight f) {
        try {
            addFlight.setString(1,f.getSursa());
            addFlight.setString(2,f.getDest());
            addFlight.setString(3,f.getOraPlecarii());
            addFlight.setString(4,f.getDurata());
            addFlight.setString(5,f.getZile());
            addFlight.setInt(6,f.getPret());

            return addFlight.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            deleteFlight.setInt(1, id);
            return deleteFlight.executeUpdate() != 0;
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll() {
        try {
            return deleteAllFlights.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Flight> findBySourceAndDestination(String sursa, String dest) {
        try {
            findBySourceAndDest.setString(1, sursa);
            findBySourceAndDest.setString(2, dest);

            ResultSet rs = findBySourceAndDest.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Flight(
                                rs.getInt("id"),
                                rs.getString("source"),
                                rs.getString("dest"),
                                rs.getString("departure_time"),
                                rs.getString("duration"),
                                rs.getString("days"),
                                rs.getInt("price")
                        ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Flight> selectAll() {
        List<Flight> flights = new ArrayList<>();
        try {
            ResultSet rs = selectAll.executeQuery();

            while (rs.next()) {
                Flight f = new Flight(
                        rs.getInt("id"),
                        rs.getString("source"),
                        rs.getString("dest"),
                        rs.getString("departure_time"),
                        rs.getString("duration"),
                        rs.getString("days"),
                        rs.getInt("price")
                );
                flights.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
