package main;

import entertainment.Genre;
import entertainment.Season;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Comparator;


public class Recomandare {
    /**
     * @param in
     * @param username
     * @return
     */
    public static String getStandard(final Input in, final String username) {
        int ok;
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(username)) {
                for (MovieInputData m : in.getMovies()) {
                    ok = 0;
                    for (String s : u.getHistory().keySet())  {
                        if (s.equals(m.getTitle())) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 0) {
                        return "StandardRecommendation result: " + m.getTitle();
                    }
                }
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * @param in
     * @param username
     * @return
     */
    public static String bestUnseen(final Input in, final String username) {
        List<String> nume = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        int ok;
        double suma, medie, suma2;
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(username)) {
                for (MovieInputData m : in.getMovies()) {
                    ok = 0;
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(m.getTitle())) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 0) {
                        suma = 0;
                        for (Double r : m.getRatings()) {
                            suma = suma + r;
                        }
                        nume.add(m.getTitle());
                        medie = suma / m.getRatings().size();
                        rates.add(medie);
                    }
                }
            }
        }
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(username)) {
                for (SerialInputData ser : in.getSerials()) {
                    ok = 0;
                    for (String s : u.getHistory().keySet()) {
                        if (s.equals(ser.getTitle())) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 0) {
                        suma = 0;
                        for (Season sez : ser.getSeasons()) {
                                suma2 = 0;
                                for (Double d : sez.getRatings()) {
                                        suma2 = suma2 + d;
                                    suma = suma + suma2 / sez.getRatings().size();
                                }
                        }
                            medie = suma / ser.getNumberSeason();
                            nume.add(ser.getTitle());
                            rates.add(medie);
                    }
                }
            }
        }
        int i, j;
        double aux1;
        String aux2;
        for (i = 0; i < rates.size() - 1; i++) {
            for (j = i + 1; j < rates.size(); j++) {
                if (rates.get(i) < rates.get(j)) {
                    aux1 = rates.get(i);
                    rates.set(i, rates.get(j));
                    rates.set(j, aux1);
                    aux2 = nume.get(i);
                    nume.set(i, nume.get(j));
                    nume.set(j, aux2);
                }
            }
        }
        if (nume.size() == 1) {
            return "BestRatedUnseenRecommendation result: " + nume.get(0);
        }
        i = 0;
        if (nume.size() >= 2) {
            String finalFilm = nume.get(0);
            int contor1 = -1, contor2, poz, poz1 = 0;
            for (MovieInputData m : in.getMovies()) {
                if (!m.getTitle().equals(nume.get(i))) {
                    contor1++;
                    break;
                }
            }
            if (contor1 == -1) {
                contor1 = in.getMovies().size() - 1;
                for (SerialInputData ser : in.getSerials()) {
                    if (!ser.getTitle().equals(nume.get(i))) {
                        contor1++;
                        break;
                    }
                }
            }
            poz = contor1;
            i = 1;
            while (rates.get(poz1).equals(rates.get(i)) && Double.isNaN(rates.get(poz1))) {
                contor2 = -1;
                for (MovieInputData m : in.getMovies()) {
                    if (!m.getTitle().equals(nume.get(i))) {
                        contor2++;
                    }
                }
                if (contor2 == -1) {
                    contor2 = in.getMovies().size() - 1;
                    for (SerialInputData ser : in.getSerials()) {
                        if (!ser.getTitle().equals(nume.get(i))) {
                            contor2++;
                        }
                    }
                }
                if (poz > contor2) {
                    finalFilm = nume.get(i);
                    poz1 = i;
                    poz = contor2;
                }
                i++;
            }
            if (rates.get(i) != 0) {
                finalFilm = nume.get(i);
                contor1 = -1;
                poz1 = i;
                for (MovieInputData m : in.getMovies()) {
                    if (!m.getTitle().equals(nume.get(i))) {
                        contor1++;
                        break;
                    }
                }
                if (contor1 == -1) {
                    contor1 = in.getMovies().size() - 1;
                    for (SerialInputData ser : in.getSerials()) {
                        if (!ser.getTitle().equals(nume.get(i))) {
                            contor1++;
                            break;
                        }
                    }
                }
                poz = contor1;
                i++;
                while (i < rates.size() && rates.get(poz1).equals(rates.get(i))) {
                    contor2 = -1;
                    for (MovieInputData m : in.getMovies()) {
                        if (!m.getTitle().equals(nume.get(i))) {
                            contor2++;
                        }
                    }
                    if (contor2 == -1) {
                        contor2 = in.getMovies().size() - 1;
                        for (SerialInputData ser : in.getSerials()) {
                            if (!ser.getTitle().equals(nume.get(i))) {
                                contor2++;
                            }
                        }
                    }
                    if (poz > contor2) {
                        finalFilm = nume.get(i);
                        poz1 = i;
                        poz = contor2;
                    }
                    i++;
                }
            }
            return "BestRatedUnseenRecommendation result: " + finalFilm;
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * @param in
     * @param user
     * @return
     */
    public static String popular(final Input in, final String user) {
        Map<String, Integer> map = new LinkedHashMap<>();
        Genre[] array = Genre.values();

        int count;
        for (Genre gen : array) {
            count = 0;
            for (MovieInputData m : in.getMovies()) {
                for (String g : m.getGenres()) {
                    if (g.toUpperCase().equals(gen.toString())) {
                        for (UserInputData u : in.getUsers()) {
                            for (String h : u.getHistory().keySet()) {
                                if (h.equals(m.getTitle())) {
                                    count = count + u.getHistory().get(h);
                                }
                            }
                        }
                    }
                }
            }
            for (SerialInputData ser : in.getSerials()) {
                for (String g : ser.getGenres()) {
                    if (g.toUpperCase().equals(gen.toString())) {
                        for (UserInputData u : in.getUsers()) {
                            for (String h : u.getHistory().keySet()) {
                                if (h.equals(ser.getTitle())) {
                                    count = count + u.getHistory().get(h);
                                }
                            }
                        }
                    }
                }
            }
            map.put(gen.toString(), count);
        }
        Map<String, Integer> finalMap = new LinkedHashMap<>();
        map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(x -> finalMap.put(x.getKey(), x.getValue()));
        int ok;
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(user)) {
                if (u.getSubscriptionType().equals("BASIC")) {
                    return "PopularRecommendation cannot be applied!";
                }
                for (String gen : finalMap.keySet()) {
                    for (MovieInputData m : in.getMovies()) {
                        for (String g : m.getGenres()) {
                            if (g.toUpperCase().equals(gen)) {
                                ok = 0;
                                for (String s : u.getHistory().keySet()) {
                                    if (s.equals(m.getTitle())) {
                                        ok = 1;
                                        break;
                                    }
                                }
                                if (ok == 0) {
                                    return "PopularRecommendation result: " + m.getTitle();
                                }
                            }
                        }
                    }
                    for (SerialInputData ser : in.getSerials()) {
                        for (String g : ser.getGenres()) {
                            if (g.toUpperCase().equals(gen)) {
                                ok = 0;
                                for (String s : u.getHistory().keySet()) {
                                    if (s.equals(ser.getTitle())) {
                                        ok = 1;
                                        break;
                                    }
                                }
                                if (ok == 0) {
                                    return "PopularRecommendation result: " + ser.getTitle();
                                }
                            }
                        }
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * @param in
     * @param user
     * @param gen
     * @return
     */
    public static String search(final Input in, final String user, final String gen) {
        List<String> filme = new ArrayList<>();
        List<Double> rates = new ArrayList<>();

        int ok, ok2;
        double suma, suma2;
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(user)) {
                if (u.getSubscriptionType().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                }
                for (MovieInputData m : in.getMovies()) {
                    for (String g : m.getGenres()) {
                        if (gen.equals(g)) {
                            ok = 0;
                            for (String s : u.getHistory().keySet()) {
                                if (s.equals(m.getTitle())) {
                                    ok = 1;
                                    break;
                                }
                            }
                            if (ok == 0) {
                                ok2 = 1;
                                for (String s : filme) {
                                    if (s.equals(m.getTitle())) {
                                        ok2 = 0;
                                        break;
                                    }
                                }
                                if (ok2 == 1) {
                                    suma = 0;
                                    for (Double d : m.getRatings()) {
                                        suma = suma + d;
                                    }
                                    filme.add(m.getTitle());
                                    rates.add(suma / m.getRatings().size());
                                }
                            }
                        }
                    }
                }
                for (SerialInputData ser : in.getSerials()) {
                    for (String g : ser.getGenres()) {
                        if (g.equals(gen)) {
                            ok = 0;
                            for (String s : u.getHistory().keySet()) {
                                if (s.equals(ser.getTitle())) {
                                    ok = 1;
                                    break;
                                }
                            }
                            if (ok == 0) {
                                suma = 0;
                                for (Season sez : ser.getSeasons()) {
                                    suma2 = 0;
                                    for (Double d : sez.getRatings()) {
                                        suma2 = suma2 + d;
                                    }
                                    suma = suma + suma2 / sez.getRatings().size();
                                    ok2 = 1;
                                    for (String s : filme) {
                                        if (s.equals(ser.getTitle())) {
                                            ok2 = 0;
                                            break;
                                        }
                                    }
                                    if (ok2 == 1) {
                                        filme.add(ser.getTitle());
                                        rates.add(suma / ser.getNumberSeason());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int i, j;
        String aux2;
        double aux1;
        for (i = 0; i < rates.size() - 1; i++) {
            for (j = i + 1; j < rates.size(); j++) {
                if (rates.get(i) > rates.get(j)) {
                    aux1 = rates.get(i);
                    rates.set(i, rates.get(j));
                    rates.set(j, aux1);
                    aux2 = filme.get(i);
                    filme.set(i, filme.get(j));
                    filme.set(j, aux2);
                }
                if (rates.get(i).equals(rates.get(j))) {
                    if (filme.get(i).compareTo(filme.get(j)) > 0) {
                        aux2 = filme.get(i);
                        filme.set(i, filme.get(j));
                        filme.set(j, aux2);
                    }
                }
            }
        }
        if (filme.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + filme;
    }

    /**
     * @param in
     * @param user
     * @return
     */
    public static String favorite(final Input in, final String user) {
        Map<String, Integer> map = new LinkedHashMap<>();
        int count;
        for (MovieInputData m : in.getMovies()) {
            count = 0;
            for (UserInputData u : in.getUsers()) {
                if (!u.getUsername().equals(user)) {
                    for (String s : u.getFavoriteMovies()) {
                        if (s.equals(m.getTitle())) {
                            count++;
                        }
                    }
                }
            }
            if (count >= 1) {
                map.put(m.getTitle(), count);
            }
        }
        for (SerialInputData ser : in.getSerials()) {
            count = 0;
            for (UserInputData u: in.getUsers()) {
                if (!u.getUsername().equals(user)) {
                    for (String s: u.getFavoriteMovies()) {
                        if (s.equals(ser.getTitle())) {
                            count++;
                        }
                    }
                }
            }
            if (count >= 1) {
                map.put(ser.getTitle(), count);
            }
        }
        Map<String, Integer> finalMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> finalMap.put(e.getKey(), e.getValue()));
        for (UserInputData u : in.getUsers()) {
            if (u.getUsername().equals(user)) {
                if (u.getSubscriptionType().equals("BASIC")) {
                    return "FavoriteRecommendation cannot be applied!";
                }
                for (String s : u.getHistory().keySet()) {
                    finalMap.remove(s, finalMap.get(s));
                }
            }
        }
        if (finalMap.size() != 0) {
            String finalFilm = "";
            int ok = 2;
            String film2 = "";
            for (String s : finalMap.keySet()) {
                if (ok == 2) {
                    finalFilm = s;
                    ok--;
                }
                if (ok == 1) {
                    film2 = s;
                    ok--;
                }
            }
            int contor1 = -1, contor2, poz;
            for (MovieInputData m : in.getMovies()) {
                if (!m.getTitle().equals(finalFilm)) {
                    contor1++;
                    break;
                }
            }
            if  (contor1 == -1) {
                contor1 = in.getMovies().size() - 1;
                for (SerialInputData ser : in.getSerials()) {
                    if (!ser.getTitle().equals(finalFilm)) {
                        contor1++;
                        break;
                    }
                }
            }
            poz = contor1;
            int i = 2;
            while (map.get(finalFilm).equals(map.get(film2))) {
                contor2 = -1;
                for (MovieInputData m : in.getMovies()) {
                    if (!m.getTitle().equals(film2)) {
                        contor2++;
                    }
                }
                if (contor2 == -1) {
                    contor2 = in.getMovies().size() - 1;
                    for (SerialInputData ser : in.getSerials()) {
                        if (!ser.getTitle().equals(film2)) {
                            contor2++;
                        }
                    }
                }
                if (poz > contor2) {
                    finalFilm = film2;
                    poz = contor2;
                }
                i++;
                if (i <= finalMap.size() + 1) {
                    ok = i;
                    for (String s : finalMap.keySet()) {
                        if (ok == 0) {
                            film2 = s;
                            ok--;
                        }
                    }
                }
                if (i == finalMap.size() + 2) {
                    return "FavoriteRecommendation result: " + finalFilm;
                }
            }
            return "FavoriteRecommendation result: " + finalFilm;
        }
        return "FavoriteRecommendation cannot be applied!";
    }
}
