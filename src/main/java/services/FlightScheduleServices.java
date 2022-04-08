package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.FlightSchedule;
import entities.Plane;
import repositories.FlightScheduleRepository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class  FlightScheduleServices {

    private FlightScheduleRepository flightScheduleRepository;

    public FlightScheduleServices() {
        flightScheduleRepository = new FlightScheduleRepository();
    }

    public List<FlightSchedule> getFlightsFromFile(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));

        return gson.fromJson(reader, new TypeToken<List<FlightSchedule>>() {}.getType());

    }
}
