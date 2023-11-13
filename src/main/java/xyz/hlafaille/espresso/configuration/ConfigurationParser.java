package xyz.hlafaille.espresso.configuration;


import com.google.gson.Gson;
import lombok.Getter;
import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.configuration.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.util.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

/**
 * ConfigurationParser deals with parsing the `espresso.json` file. When instantiated, this class will automatically
 * parse the `espresso.json` file.
 *
 * @author hlafaille
 * @version 1.0.0
 */
@Getter
public class ConfigurationParser {
    private final EspressoProjectConfiguration espressoProjectConfiguration;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public ConfigurationParser() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader(Constants.ESPRESSO_CONFIG_PATH));
        espressoProjectConfiguration = gson.fromJson(reader, EspressoProjectConfiguration.class);
        logger.info("configuration loaded, %s dependencies".formatted(espressoProjectConfiguration.getDependencies().size()));
    }
}
