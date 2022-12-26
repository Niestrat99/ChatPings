package de.niestrat.chatpings.hooks;

import de.niestrat.chatpings.main.Main;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VAC {
    /**
     * / VAC = VersionAvailabilityCheck
     * / This function allows to check, if the server is running the required version or higher
     * / to use certain features.
     * / It'll return a bool depending on whether the requirement is reached or not.
     **/


    public static boolean checkVersion(int requiredVersion) { // Idea right now is to use something like 1192
        // Get Package Version out of Package Name
        String serverPackageName    = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        // Extract numbers out of version
        String versionNumberExtract = serverPackageName.replaceAll("[^0-9]", "");
        // Extract release number
        String releaseNumber        = serverPackageName.substring(serverPackageName.indexOf("R") + 1, serverPackageName.length());

        // Get actual version
        int version                 = Integer.parseInt(versionNumberExtract) - Integer.parseInt(releaseNumber);

        // Right now the way I wrote the version it'll return 1190.

        Main.getInstance().getLogger().info("SERVER VERSION: " + version);

        // Check for validation
        boolean isValid             = false;

        if (version <= requiredVersion) {
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }
}
