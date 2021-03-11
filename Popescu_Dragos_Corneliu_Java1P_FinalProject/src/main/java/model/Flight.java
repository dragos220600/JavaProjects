package model;

import view.CurrentlyFlightId;
import view.DeleteConfirmation;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Flight {
    private int id;
    private String sursa;
    private String dest;
    private String oraPlecarii;
    private String durata;
    private String zile;
    private int pret;
    private JButton button;

    public Flight() {
    }

    public Flight(int id, String sursa, String dest, String oraPlecarii, String durata, String zile, int pret) {
        this.id = id;
        this.sursa = sursa;
        this.dest = dest;
        this.oraPlecarii = oraPlecarii;
        this.durata = durata;
        this.zile = zile;
        this.pret = pret;
    }

    public int getId() {
        return id;
    }


    public String getSursa() {
        return sursa;
    }


    public String getDest() {
        return dest;
    }


    public String getOraPlecarii() {
        return oraPlecarii;
    }


    public String getDurata() {
        return durata;
    }


    public String getZile() {
        return zile;
    }


    public int getPret() {
        return pret;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", sursa='" + sursa + '\'' +
                ", dest='" + dest + '\'' +
                ", oraPlecarii='" + oraPlecarii + '\'' +
                ", durata='" + durata + '\'' +
                ", zile='" + zile + '\'' +
                ", pret=" + pret +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void initButton() {
        button = new JButton();
        button.setSize(20,20);
        button.setBackground(Color.RED);
        button.setText("Delete");
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.addActionListener(e -> {
            CurrentlyFlightId.id = this.getId();
            new DeleteConfirmation();
        });
    }

    public JButton getButton() {
        initButton();
        return button;
    }

}
