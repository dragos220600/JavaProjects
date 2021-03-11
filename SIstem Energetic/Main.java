import com.fasterxml.jackson.databind.ObjectMapper;
import simulation.Simulation;
import simulation.input.SimulationInputData;

import java.io.File;
import java.io.IOException;

public final class Main {

    private static final File TEST_OUTPUT_FILE = new File("results.out");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Main() {
    }

    /**
     * Entry point
     *
     * @param args simulation input file name
     * @throws Exception
     */
    public static void main(final String[] args) throws IOException {

        Simulation simulation = Simulation.instance();
        simulation.clean();

        final File inputFile = new File(args[0]);
        SimulationInputData input = OBJECT_MAPPER.readValue(inputFile, SimulationInputData.class);

        simulation.setup(input.getInitialData());

        for (int i = 0; i <= input.getNumberOfTurns(); i++) {

            if (i != 0) {
                simulation.apply(input.getMonthlyUpdates().get(i - 1));
            }

            if (!simulation.offer()) {
                break;
            }

            if (!simulation.assign()) {
                break;
            }

            simulation.update();
        }

        OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(TEST_OUTPUT_FILE, simulation);
    }
}
