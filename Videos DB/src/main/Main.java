package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import fileio.ActionInputData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        String username, title, message, sortType, objectType, gen;
        double grade;
        int index, n, year;

        for (ActionInputData a : input.getCommands()) {
            if (a.getActionType().equals(Constants.COMMAND)) {
                username = a.getUsername();
                title = a.getTitle();
                if (a.getType().equals("favorite")) {
                    message = Comanda.favorite(input, username, title);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("view")) {
                    message = Comanda.view(input, username, title);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("rating")) {
                    grade = a.getGrade();
                    if (a.getSeasonNumber() != 0) {
                        index = a.getSeasonNumber();
                        message = Comanda.rateSerial(input, username, title, grade, index);
                        JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                        arrayResult.add(jsonObject);
                    } else {
                        message = Comanda.rateMovie(input, username, title, grade);
                        JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                        arrayResult.add(jsonObject);
                    }
                }
            }
            if (a.getActionType().equals(Constants.QUERY)) {
                n = a.getNumber();
                sortType = a.getSortType();
                if (a.getCriteria().equals("average")) {
                    message = Query.average(input, n, sortType);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("awards")) {
                    message = Query.awards(input, sortType, a.getFilters().get(3));
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("filter_description")) {
                    message = Query.filterDescription(input, sortType, a.getFilters().get(2));
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("ratings")) {
                    String conv = a.getFilters().get(0).get(0);
                    year = 0;
                    if (conv != null) {
                        year = Integer.parseInt(conv);
                    }
                    gen = a.getFilters().get(1).get(0);
                    objectType = a.getObjectType();
                    message = "";
                    if (objectType.equals("movies")) {
                        message = Query.ratingMovie(input, n, sortType, gen, year);
                    }
                    if (objectType.equals("shows")) {
                        message = Query.ratingSerial(input, n, sortType, gen, year);
                    }
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("favorite")) {
                    String conv = a.getFilters().get(0).get(0);
                    year = 0;
                    if (conv != null) {
                        year = Integer.parseInt(conv);
                    }
                    gen = a.getFilters().get(1).get(0);
                    objectType = a.getObjectType();
                    message = "";
                    if (objectType.equals("movies")) {
                        message = Query.favoriteMovie(input, n, sortType, gen, year);
                    }
                    if (objectType.equals("shows")) {
                        message = Query.favoriteSerial(input, n, sortType, gen, year);
                    }
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("longest")) {
                    String conv = a.getFilters().get(0).get(0);
                    year = 0;
                    if (conv != null) {
                        year = Integer.parseInt(conv);
                    }
                    gen = a.getFilters().get(1).get(0);
                    objectType = a.getObjectType();
                    message = "";
                    if (objectType.equals("movies")) {
                        message = Query.longestMovie(input, n, sortType, gen, year);
                    }
                    if (objectType.equals("shows")) {
                        message = Query.longestShow(input, n, sortType, gen, year);
                    }
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("most_viewed")) {
                    String conv = a.getFilters().get(0).get(0);
                    year = 0;
                    if (conv != null) {
                        year = Integer.parseInt(conv);
                    }
                    gen = a.getFilters().get(1).get(0);
                    objectType = a.getObjectType();
                    message = "";
                    if (objectType.equals("movies")) {
                        message = Query.mostViewMovie(input, n, sortType, gen, year);
                    }
                    if (objectType.equals("shows")) {
                        message = Query.mostViewSerial(input, n, sortType, gen, year);
                    }
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getCriteria().equals("num_ratings")) {
                    message = Query.numberUsers(input, n, sortType);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
            }
            if (a.getActionType().equals(Constants.RECOMMENDATION)) {
                username = a.getUsername();
                if (a.getType().equals("standard")) {
                    message = Recomandare.getStandard(input, username);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("best_unseen")) {
                    message = Recomandare.bestUnseen(input, username);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("popular")) {
                    message = Recomandare.popular(input, username);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("search")) {
                    gen = a.getGenre();
                    message = Recomandare.search(input, username, gen);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
                if (a.getType().equals("favorite")) {
                    message = Recomandare.favorite(input, username);
                    JSONObject jsonObject = fileWriter.writeFile(a.getActionId(), "", message);
                    arrayResult.add(jsonObject);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
