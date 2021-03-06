package services.fileReaderService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Flight;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FlightServices {

    public List<Flight> getFlightsFromFile(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));


        return gson.fromJson(reader, new TypeToken<List<Flight>>() {}.getType());
    }
}