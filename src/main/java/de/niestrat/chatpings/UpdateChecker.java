package de.niestrat.chatpings;

import de.niestrat.chatpings.main.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class UpdateChecker {

    private final static String versionURL = "https://api.spiget.org/v2/resources/76261/versions?size=1000";
    private final static String descriptionURL = "https://api.spiget.org/v2/resources/76261/versions?size=1000";

    public static Object[] getUpdate() throws IOException {
        URL url = new URL(versionURL);
        Main at = Main.getInstance();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "ATPluginAgent");
        InputStream inputStream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        JSONArray versionsArray = null;
        try {
            versionsArray = (JSONArray) new JSONParser().parse(reader);
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        int size = Objects.requireNonNull(versionsArray).size();
        String[] lastVersion = ((JSONObject) versionsArray.get(size - 1)).get("name").toString().split("\\.");
        String[] currentVersion = at.getDescription().getVersion().split("\\.");
        String latestVersionString = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString();

        url = new URL(descriptionURL);
        connection = (HttpURLConnection) url.openConnection();
        inputStream = connection.getInputStream();
        reader = new InputStreamReader(inputStream);
        JSONArray updatesArray = null;
        try {
            updatesArray = (JSONArray) new JSONParser().parse(reader);
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        if (!Arrays.asList(lastVersion).toString().equals(Arrays.asList(currentVersion).toString())) {

            assert updatesArray != null;
            Object updateName = ((JSONObject) updatesArray.get(Objects.requireNonNull(updatesArray).size() - 1)).get("title");
            if (updateName == null) {
                updateName = "Nothing found!";
            }

            return new Object[]{lastVersion, updateName, latestVersionString};
        }
        return null;
    }
}
