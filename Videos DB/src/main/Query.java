package main;

import actor.ActorsAwards;
import entertainment.Season;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import fileio.Input;


import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Collections;



public class Query {
    /**
     * @param in
     * @param n
     * @param sortType
     * @return
     */
    public static String average(final Input in, int n, final String sortType) {
        List<String> actori = new ArrayList<>();
        List<Double> rating = new ArrayList<>();

        for (ActorInputData a : in.getActors()) {
            actori.add(a.getName());
            double suma1 = 0.0, suma2, suma3;
            int nr2, nr3, nr1 = 0;
            for (String s : a.getFilmography()) {
                for (MovieInputData movie : in.getMovies()) {
                    if (s.equals(movie.getTitle())) {
                        if (!movie.getRatings().isEmpty()) {
                            suma2 = 0.0;
                            nr2 = 0;
                            for (Double r : movie.getRatings()) {
                                if (r != 0) {
                                    suma2 = suma2 + r;
                                    nr2++;
                                }
                            }
                            if (nr2 != 0) {
                                suma1 = suma1 + suma2 / nr2;
                                nr1++;
                            }
                        }
                    }
                }
                for (SerialInputData serial : in.getSerials()) {
                    if (s.equals(serial.getTitle())) {
                        suma2 = 0.0;
                        for (Season sez : serial.getSeasons()) {
                            suma3 = 0.0;
                            nr3 = 0;
                            if (!sez.getRatings().isEmpty()) {
                                for (Double r : sez.getRatings()) {
                                    if (r != 0) {
                                        suma3 = suma3 + r;
                                        nr3++;
                                    }
                                }
                                if (nr3 != 0) {
                                    suma2 = suma2 + suma3 / nr3;
                                }
                            }
                        }
                        suma1 = suma1 + suma2 / serial.getNumberSeason();
                        nr1++;
                    }
                }
            }
            if (suma1 != 0) {
                rating.add(suma1 / nr1);
            } else {
                actori.remove(actori.size() - 1);
            }
        }
        int i, j;
        double aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < rating.size() - 1; i++) {
                for (j = i + 1; j < rating.size(); j++) {
                    if (rating.get(i) > rating.get(j)) {
                        aux1 = rating.get(i);
                        rating.set(i, rating.get(j));
                        rating.set(j, aux1);

                        aux2 = actori.get(i);
                        actori.set(i, actori.get(j));
                        actori.set(j, aux2);
                    }
                    if (rating.get(i).equals(rating.get(j))) {
                        if (actori.get(i).compareTo(actori.get(j)) > 0) {
                            aux2 = actori.get(i);
                            actori.set(i, actori.get(j));
                            actori.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < rating.size() - 1; i++) {
                for (j = i + 1; j < rating.size(); j++) {
                    if (rating.get(i) < rating.get(j)) {
                        aux1 = rating.get(i);
                        rating.set(i, rating.get(j));
                        rating.set(j, aux1);

                        aux2 = actori.get(i);
                        actori.set(i, actori.get(j));
                        actori.set(j, aux2);
                    }
                    if (rating.get(i).equals(rating.get(j))) {
                        if (actori.get(i).compareTo(actori.get(j)) < 0) {
                            aux2 = actori.get(i);
                            actori.set(i, actori.get(j));
                            actori.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > actori.size()) {
            n = actori.size();
        }
        for (i = 0; i < n - 1; i++) {
                finalString.append(actori.get(i)).append(", ");
        }
        finalString.append(actori.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param sortType
     * @param stringAwards
     * @return
     */
    public static String awards(final Input in, final String sortType,
                                final List<String> stringAwards) {
        List<String> actori = new ArrayList<>();
        List<Integer> views = new ArrayList<>();

        for (ActorInputData a : in.getActors()) {
            int ok = 0, numar = 0;
            for (String s : stringAwards) {
                for (ActorsAwards actorsAwards : a.getAwards().keySet()) {
                    if (s.equals(actorsAwards.toString())) {
                        ok++;
                    }
                }
            }
            if (ok == stringAwards.size()) {
                for (ActorsAwards actorsAwards : a.getAwards().keySet()) {
                    numar = numar + a.getAwards().get(actorsAwards);
                }
                actori.add(a.getName());
                views.add(numar);
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i <  views.size() - 1; i++) {
                for (j = i + 1; j < views.size(); j++) {
                    if (views.get(i) > views.get(j)) {
                        aux1 =  views.get(i);
                        views.set(i,  views.get(j));
                        views.set(j, aux1);

                        aux2 =  actori.get(i);
                        actori.set(i,  actori.get(j));
                        actori.set(j, aux2);
                    }
                    if (views.get(i).equals(views.get(j))) {
                        if (actori.get(i).compareTo(actori.get(j)) > 0) {
                            aux2 = actori.get(i);
                            actori.set(i, actori.get(j));
                            actori.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < views.size() - 1; i++) {
                for (j = i + 1; j < views.size(); j++) {
                    if (views.get(i) < views.get(j)) {
                        aux1 = views.get(i);
                        views.set(i, views.get(j));
                        views.set(j, aux1);

                        aux2 = actori.get(i);
                        actori.set(i, actori.get(j));
                        actori.set(j, aux2);
                    }
                    if (views.get(i).equals(views.get(j))) {
                        if (actori.get(i).compareTo(actori.get(j)) < 0) {
                            aux2 = actori.get(i);
                            actori.set(i, actori.get(j));
                            actori.set(j, aux2);
                        }
                    }
                }
            }
        }
        String finalString = "Query result: ";
        finalString = finalString + actori;
        return finalString;
    }

    /**
     * @param in
     * @param sortType
     * @param words
     * @return
     */
    public static String filterDescription(final Input in, final String sortType,
                                           final List<String> words) {
        List<String> actors = new ArrayList<>();
        for (ActorInputData a : in.getActors()) {
            int ok = 0;
            for (String s : words) {
                StringTokenizer st = new StringTokenizer(a.getCareerDescription(), ",.() !?;-");
                    while (st.hasMoreTokens()) {
                        if (s.contentEquals(st.nextToken().toLowerCase())) {
                            ok++;
                            break;
                         }
                }
            }
            if (ok == words.size()) {
                actors.add(a.getName());
            }
        }
        if (sortType.equals("asc")) {
            Collections.sort(actors);
        } else {
            actors.sort(Comparator.reverseOrder());
        }
        String finalString = "Query result: ";
        finalString = finalString + actors;
        return finalString;
    }
    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String ratingMovie(final Input in, int n,
                                     final String sortType, final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Double> rateinguri = new ArrayList<>();

        double suma, nr;
        int ok1, ok;
        for (MovieInputData m : in.getMovies()) {
            ok = 0;
            ok1 = 0;
            for (String g : m.getGenres()) {
                if (g.equals(gen)) {
                    ok = 1;
                    break;
                }
            }
            if (m.getYear() == an || an == 0) {
                ok1 = 1;
            }
            if (ok1 == 1 && ok == 1) {
                if (!m.getRatings().isEmpty()) {
                    suma = 0;
                    for (Double r : m.getRatings()) {
                        suma = suma + r;
                    }
                    if (suma != 0) {
                        nr = suma / m.getRatings().size();
                        filme.add(m.getTitle());
                        rateinguri.add(nr);
                    }
                }
            }
        }
        if (filme.size() == 0) {
            return "Query result: []";
        }
        int i, j;
        double aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < rateinguri.size() - 1; i++) {
                for (j = i + 1; j < rateinguri.size(); j++) {
                    if (rateinguri.get(i) > rateinguri.get(j)) {
                        aux1 = rateinguri.get(i);
                        rateinguri.set(i, rateinguri.get(j));
                        rateinguri.set(j, aux1);

                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (rateinguri.get(i).equals(rateinguri.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < rateinguri.size() - 1; i++) {
                for (j = i + 1; j < rateinguri.size(); j++) {
                    if (rateinguri.get(i) < rateinguri.get(j)) {
                        aux1 = rateinguri.get(i);
                        rateinguri.set(i, rateinguri.get(j));
                        rateinguri.set(j, aux1);

                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (rateinguri.get(i).equals(rateinguri.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String ratingSerial(final Input in, int n,
                                      final String sortType, final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Double> rateinguri = new ArrayList<>();

        int ok, ok1;
        double suma1, suma2, nr;
        for (SerialInputData ser : in.getSerials()) {
            ok = 0;
            ok1 = 0;
            if (ser.getYear() == an || an == 0) {
                ok = 1;
            }
            for (String g : ser.getGenres()) {
                if (g.equals(gen)) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok1 == 1 && ok == 1) {
                suma1 = 0;
                for (Season sez : ser.getSeasons()) {
                    if (!sez.getRatings().isEmpty()) {
                        suma2 = 0;
                        nr = 0;
                        for (Double d : sez.getRatings()) {
                            if (d != 0) {
                                suma2 = suma2 + d;
                                nr++;
                            }
                        }
                        suma1 = suma1 + suma2 / nr;
                    }
                }
                if (suma1 != 0) {
                    filme.add(ser.getTitle());
                    rateinguri.add(suma1 / ser.getNumberSeason());
                }
            }
        }
        int i, j;
        double aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < rateinguri.size() - 1; i++) {
                for (j = i + 1; j < rateinguri.size(); j++) {
                    if (rateinguri.get(i) > rateinguri.get(j)) {
                        aux1 = rateinguri.get(i);
                        rateinguri.set(i, rateinguri.get(j));
                        rateinguri.set(j, aux1);

                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (rateinguri.get(i).equals(rateinguri.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < rateinguri.size() - 1; i++) {
                for (j = i + 1; j < rateinguri.size(); j++) {
                    if (rateinguri.get(i) < rateinguri.get(j)) {
                        aux1 = rateinguri.get(i);
                        rateinguri.set(i, rateinguri.get(j));
                        rateinguri.set(j, aux1);

                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (rateinguri.get(i).equals(rateinguri.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String favoriteMovie(final Input in, int n, final String sortType,
                                       final String gen, final int an) {
       List<String> filme = new ArrayList<>();
       List<Integer> numere = new ArrayList<>();

        int nr;
        int ok, ok1;
        for (MovieInputData m : in.getMovies()) {
            nr = 0;
            ok = 0;
            ok1 = 0;
            if (gen == null) {
                ok = 1;
            } else {
                for (String g : m.getGenres()) {
                    if (g.equals(gen)) {
                        ok = 1;
                        break;
                    }
                }
            }
            if (m.getYear() == an || an == 0) {
                ok1 = 1;
            }
            for (UserInputData u : in.getUsers()) {
                for (String s : u.getFavoriteMovies()) {
                    if (s.equals(m.getTitle())) {
                        nr++;
                    }
                }
            }
            if (nr != 0 && ok == 1 && ok1 == 1) {
                filme.add(m.getTitle());
                numere.add(nr);
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < numere.size() - 1; i++) {
                for (j = i + 1; j < numere.size(); j++) {
                    if (numere.get(i) > numere.get(j)) {
                        aux1 = numere.get(i);
                        numere.set(i, numere.get(j));
                        numere.set(j, aux1);

                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (numere.get(i).equals(numere.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < numere.size() - 1; i++) {
                for (j = i + 1; j < numere.size(); j++) {
                    if (numere.get(i) < numere.get(j)) {
                        aux1 = numere.get(i);
                        numere.set(i, numere.get(j));
                        numere.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (numere.get(i).equals(numere.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String favoriteSerial(final Input in, int n, final String sortType,
                                        final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Integer> numere = new ArrayList<>();

        int nr;
        int ok, ok1;
        for (SerialInputData ser : in.getSerials()) {
            nr = 0;
            ok = 0;
            ok1 = 0;
            for (String g : ser.getGenres()) {
                if (g.equals(gen)) {
                    ok = 1;
                    break;
                }
            }
            if (ser.getYear() == an || an == 0) {
                ok1 = 1;
            }
            for (UserInputData u : in.getUsers()) {
                for (String s : u.getFavoriteMovies()) {
                    if (s.equals(ser.getTitle())) {
                        nr++;
                    }
                }
            }
            if (nr != 0 && ok == 1 && ok1 == 1) {
                filme.add(ser.getTitle());
                numere.add(nr);
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < numere.size() - 1; i++) {
                for (j = i + 1; j < numere.size(); j++) {
                    if (numere.get(i) > numere.get(j)) {
                        aux1 = numere.get(i);
                        numere.set(i, numere.get(j));
                        numere.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (numere.get(i).equals(numere.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < numere.size() - 1; i++) {
                for (j = i + 1; j < numere.size();  j++) {
                    if (numere.get(i) < numere.get(j)) {
                        aux1 = numere.get(i);
                        numere.set(i, numere.get(j));
                        numere.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (numere.get(i).equals(numere.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String longestMovie(final Input in, int n, final String sortType,
                                      final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Integer> durate = new ArrayList<>();

        int ok, ok1;
        for (MovieInputData m : in.getMovies()) {
            ok = 0;
            ok1 = 0;
            if (m.getYear() == an || an == 0) {
                ok = 1;
            }
            if (gen == null) {
                ok1 = 1;
            } else {
                for (String g : m.getGenres()) {
                    if (g.equals(gen)) {
                        ok1 = 1;
                        break;
                    }
                }
            }
            if (ok == 1 && ok1 == 1) {
                filme.add(m.getTitle());
                durate.add(m.getDuration());
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < durate.size() - 1; i++) {
                for (j = i + 1; j < durate.size(); j++) {
                    if (durate.get(i) > durate.get(j)) {
                        aux1 = durate.get(i);
                        durate.set(i, durate.get(j));
                        durate.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (durate.get(i).equals(durate.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < durate.size() - 1; i++) {
                for (j = i + 1; j < durate.size(); j++) {
                    if (durate.get(i) < durate.get(j)) {
                        aux1 = durate.get(i);
                        durate.set(i, durate.get(j));
                        durate.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (durate.get(i).equals(durate.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String longestShow(final Input in, int n, final String sortType,
                                     final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Integer> durate = new ArrayList<>();

        int ok1, ok;
        int suma;
        for (SerialInputData ser : in.getSerials()) {
            ok = 0;
            ok1  = 0;
            if (ser.getYear() == an || an == 0) {
                ok = 1;
            }
            for (String g : ser.getGenres()) {
                if (g.equals(gen)) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok == 1 && ok1 == 1) {
                suma = 0;
                for (Season sez : ser.getSeasons()) {
                    suma = suma + sez.getDuration();
                }
                filme.add(ser.getTitle());
                durate.add(suma);
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < durate.size() - 1; i++) {
                for (j = i + 1; j < durate.size(); j++) {
                    if (durate.get(i) > durate.get(j)) {
                        aux1 = durate.get(i);
                        durate.set(i, durate.get(j));
                        durate.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (durate.get(i).equals(durate.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < durate.size() - 1; i++) {
                for (j = i + 1; j < durate.size(); j++) {
                    if (durate.get(i) < durate.get(j)) {
                        aux1 = durate.get(i);
                        durate.set(i, durate.get(j));
                        durate.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (durate.get(i).equals(durate.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        if (n > filme.size())  {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String mostViewMovie(final Input in, int n, final String sortType,
                                       final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Integer> vizualizari = new ArrayList<>();

        int ok, ok1, suma;
        for (MovieInputData m : in.getMovies()) {
            ok = 0;
            ok1 = 0;
            if (m.getYear() == an || an == 0) {
                ok = 1;
            }
            if (gen == null) {
                ok1 = 1;
            } else {
                for (String g : m.getGenres()) {
                    if (g.equals(gen)) {
                        ok1 = 1;
                        break;
                    }
                }
            }
            if (ok == 1 && ok1 == 1) {
                suma = 0;
                for (UserInputData u : in.getUsers()) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(m.getTitle())) {
                            int val = u.getHistory().get(s);
                            suma = suma + val;
                        }
                    }
                }
                if (suma != 0) {
                    filme.add(m.getTitle());
                    vizualizari.add(suma);
                }
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < vizualizari.size() - 1; i++) {
                for (j = i + 1; j < vizualizari.size(); j++) {
                    if (vizualizari.get(i) > vizualizari.get(j)) {
                        aux1 = vizualizari.get(i);
                        vizualizari.set(i, vizualizari.get(j));
                        vizualizari.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (vizualizari.get(i).equals(vizualizari.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < vizualizari.size() - 1; i++) {
                for (j = i + 1; j < vizualizari.size(); j++) {
                    if (vizualizari.get(i) < vizualizari.get(j)) {
                        aux1 = vizualizari.get(i);
                        vizualizari.set(i, vizualizari.get(j));
                        vizualizari.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (vizualizari.get(i).equals(vizualizari.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        System.out.println(filme);
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @param gen
     * @param an
     * @return
     */
    public static String mostViewSerial(final Input in, int n, final String sortType,
                                        final String gen, final int an) {
        List<String> filme = new ArrayList<>();
        List<Integer> vizualizari = new ArrayList<>();

        int ok, ok1, suma;
        for (SerialInputData ser : in.getSerials()) {
            ok = 0;
            ok1 = 0;
            if (ser.getYear() == an || an == 0) {
                ok = 1;
            }
            for (String g : ser.getGenres()) {
                if (g.equals(gen)) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok == 1 && ok1 == 1) {
                suma = 0;
                for (UserInputData u : in.getUsers()) {
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(ser.getTitle())) {
                            int val = u.getHistory().get(s);
                            suma = suma + val;
                        }
                    }
                }
                if (suma != 0) {
                    filme.add(ser.getTitle());
                    vizualizari.add(suma);
                }
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < vizualizari.size() - 1; i++) {
                for (j = i + 1; j < vizualizari.size(); j++) {
                    if (vizualizari.get(i) > vizualizari.get(j)) {
                        aux1 = vizualizari.get(i);
                        vizualizari.set(i, vizualizari.get(j));
                        vizualizari.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (vizualizari.get(i).equals(vizualizari.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) > 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < vizualizari.size() - 1; i++) {
                for (j = i + 1; j < vizualizari.size(); j++) {
                    if (vizualizari.get(i) < vizualizari.get(j)) {
                        aux1 = vizualizari.get(i);
                        vizualizari.set(i, vizualizari.get(j));
                        vizualizari.set(j, aux1);
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                    if (vizualizari.get(i).equals(vizualizari.get(j))) {
                        if (filme.get(i).compareTo(filme.get(j)) < 0) {
                            aux2 = filme.get(i);
                            filme.set(i, filme.get(j));
                            filme.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        System.out.println(filme);
        if (n > filme.size()) {
            n = filme.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(filme.get(i)).append(", ");
        }
        finalString.append(filme.get(n - 1)).append("]");
        return finalString.toString();
    }

    /**
     * @param in
     * @param n
     * @param sortType
     * @return
     */
    public static String numberUsers(final Input in, int n, final String sortType) {
        List<String> nume = new ArrayList<>();
        List<Integer> nrrate = new ArrayList<>();
        for (UserInputData u : in.getUsers()) {
            if (!u.getRatedList().isEmpty()) {
                nume.add(u.getUsername());
                nrrate.add(u.getRatedList().size());
            }
        }
        int i, j;
        int aux1;
        String aux2;
        if (sortType.equals("asc")) {
            for (i = 0; i < nrrate.size() - 1; i++) {
                for (j = i + 1; j < nrrate.size(); j++)  {
                    if (nrrate.get(i) > nrrate.get(j)) {
                        aux1 = nrrate.get(i);
                        nrrate.set(i, nrrate.get(j));
                        nrrate.set(j, aux1);
                        aux2 = nume.get(i);
                        nume.set(i, nume.get(j));
                        nume.set(j, aux2);
                    }
                    if (nrrate.get(i).equals(nrrate.get(j))) {
                        if (nume.get(i).compareTo(nume.get(j)) > 0) {
                            aux2 = nume.get(i);
                            nume.set(i, nume.get(j));
                            nume.set(j, aux2);
                        }
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (i = 0; i < nrrate.size() - 1; i++) {
                for (j = i + 1; j < nrrate.size(); j++) {
                    if (nrrate.get(i) < nrrate.get(j)) {
                        aux1 = nrrate.get(i);
                        nrrate.set(i, nrrate.get(j));
                        nrrate.set(j, aux1);
                        aux2 = nume.get(i);
                        nume.set(i, nume.get(j));
                        nume.set(j, aux2);
                    }
                    if (nrrate.get(i).equals(nrrate.get(j))) {
                        if (nume.get(i).compareTo(nume.get(j)) < 0) {
                            aux2 = nume.get(i);
                            nume.set(i, nume.get(j));
                            nume.set(j, aux2);
                        }
                    }
                }
            }
        }
        StringBuilder finalString = new StringBuilder("Query result: [");
        System.out.println(nume);
        System.out.println(nrrate);
        if (n > nume.size()) {
            n = nume.size();
        }
        if (n == 0) {
            return "Query result: []";
        }
        for (i = 0; i < n - 1; i++) {
            finalString.append(nume.get(i)).append(", ");
        }
        finalString.append(nume.get(n - 1)).append("]");
        return finalString.toString();
    }
}
