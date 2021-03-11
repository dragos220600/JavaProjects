package main;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;


public class Comanda {
    /**
     * @param in
     * @param nume
     * @param v
     * @return
     */
        public static String favorite(final Input in, final String nume, final String v) {
            int ok1 = 0, ok2 = 0;
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(v)) {
                            ok1 = 1;
                            break;
                        }
                    }
                }
            }
            if (ok1 == 0) {
                return "error -> " + v + " is not seen";
            }
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    for (String s : u.getFavoriteMovies()) {
                        if (s.equals(v)) {
                            ok2 = 1;
                            break;
                        }
                    }
                }
            }
            if (ok2 == 0) {
                for (UserInputData u : in.getUsers()) {
                    if (nume.equals(u.getUsername())) {
                        u.getFavoriteMovies().add(v);
                    }
                }
                return "success -> " + v + " was added as favourite";
            } else {
                return "error -> " + v + " is already in favourite list";
            }
        }

    /**
     * @param in
     * @param nume
     * @param v
     * @return
     */
        public static String view(final Input in, final String nume, final String v) {
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(v)) {
                            int x = u.getHistory().get(s);
                            x++;
                            u.getHistory().put(v, x);
                            return "success -> " + v + " was viewed with total views of " + x;
                        }
                    }
                }
            }
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    u.getHistory().put(v, 1);
                }
            }
            return "success -> " + v + " was viewed with total views of 1";
        }

    /**
     * @param in
     * @param nume
     * @param v
     * @param r
     * @param index
     * @return
     */
        public static String rateSerial(final Input in, final String nume,
                                        final String v, final double r, final int index) {
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(v)) {
                            for (SerialInputData serial : in.getSerials()) {
                                if (v.equals(serial.getTitle())) {
                                    List<Integer> sezoane = new ArrayList<>();
                                    for (int i = 0; i < serial.getNumberSeason(); i++) {
                                        sezoane.add(0);
                                    }
                                    if (u.getRatedList().get(serial.getTitle()) == null) {
                                        u.getRatedList().put(serial.getTitle(), sezoane);
                                        u.getRatedList().get(serial.getTitle()).add(index - 1, 1);
                                        serial.getSeasons().get(index - 1).getRatings().add(r);
                                        return "success -> " + v + " was rated with "
                                                + r + " by " +  u.getUsername();
                                    } else {
                                        if (u.getRatedList().get(serial.getTitle())
                                                .get(index - 1) == 0) {
                                            u.getRatedList().get(serial.getTitle())
                                                    .add(index - 1, 1);
                                            serial.getSeasons().get(index - 1)
                                                    .getRatings().add(r);
                                            return "success -> " + v + " was rated with "
                                                    + r + " by " +  u.getUsername();
                                        } else {
                                            return "error -> " + v + " has been already rated";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return "error -> " + v + " is not seen";
                }
            }
            return "";
        }

    /**
     * @param in
     * @param nume
     * @param v
     * @param r
     * @return
     */
        public static String rateMovie(final Input in, final String nume,
                                       final String v, final double r) {
            for (UserInputData u : in.getUsers()) {
                if (nume.equals(u.getUsername())) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(v)) {
                            for (MovieInputData m : in.getMovies()) {
                                if (v.equals(m.getTitle())) {
                                    List<Integer> sez = new ArrayList<>();
                                    sez.add(0);
                                    if (u.getRatedList().get(v) == null) {
                                        u.getRatedList().put(v, sez);
                                        u.getRatedList().get(v).add(0, 1);
                                        m.getRatings().add(r);
                                        return "success -> " + v + " was rated with "
                                                + r + " by " +  u.getUsername();
                                    } else {
                                        return "error -> " + v + " has been already rated";
                                    }
                                }
                            }
                        }
                    }
                    return "error -> " + v + " is not seen";
                }
            }
            return "";
        }
}
