package de.niestrat.chatpings.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.niestrat.chatpings.commands.Toggle;
import de.niestrat.chatpings.main.Main;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.UUID;

public class MutePings {

    public static final File uuid = new File(Main.getInstance().getDataFolder(), "UUIDs.json");

    public static JSONArray readWriter = new JSONArray();

    public static void read() throws IOException, ParseException {
        InputStreamReader input = new InputStreamReader(new FileInputStream(uuid));
        readWriter = (JSONArray) new JSONParser().parse(input);

        for (Object uniqueID : readWriter) {
            Toggle.mutePing.add(UUID.fromString(String.valueOf(uniqueID)));
        }
    }

    public static void write() throws IOException {
        readWriter.clear();
        for (UUID uniqueID : Toggle.mutePing) {
            readWriter.add(uniqueID.toString());
        }
        if (uuid.exists()) {
            PrintWriter writer = new PrintWriter(uuid);
            writer.print("");
            writer.close();
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create(); // Allows the file to look pretty
        String s = gson.toJson(readWriter); // Convert the JSONObject to a string
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(uuid)); // This is used to write the JSON to the file
        try {
            fw.write(s.replace("\u0026", "&")); // Then write it
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw.flush(); // Before flushing and closing it!
            fw.close();
        }
    }

    public static void create() throws IOException {
        if (!uuid.exists()) {
            uuid.createNewFile();
            JSONArray object = new JSONArray();
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create(); // Allows the file to look pretty
            String s = gson.toJson(object); // Convert the JSONObject to a string
            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(uuid)); // This is used to write the JSON to the file
            try {
                fw.write(s.replace("\u0026", "&")); // Then write it
            } finally {
                fw.flush(); // Before flushing and closing it!
                fw.close();
            }
        }
    }
}
