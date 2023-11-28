package co.edu.uptc.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import co.edu.uptc.model.Brand;


public class PersistenceJson {
	public Gson gson;
	
	public PersistenceJson() {
		gson = new Gson();
	}
	
	public void readDates(ArrayList<Brand> brands) throws IOException {
		JsonReader reader = new JsonReader(new FileReader("data/dates.json"));
		brands = gson.fromJson(reader, Brand[].class);
		reader.close();
	}
	
	public void writeDates(ArrayList<Brand> brands) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("data/dates.json");
		writer.write(gson.toJson(brands));
		writer.close();
	}
}
