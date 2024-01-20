package utilities.envParser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public final class EnvParser {

    private static EnvParser instance;
    private final HashMap<String, String> env;

    public static EnvParser getInstance(final String envPath){
        if(instance == null){
            instance = new EnvParser(envPath);
        }
        return instance;
    }

    private EnvParser(final String envPath){
        this.env = decodeFromPath(envPath);
    }

    public Optional<String> getFromEnv(final String key){
        return Optional.ofNullable(env.get(key));
    }

    private HashMap<String, String> decodeFromPath(final String envPath) {
        final var env = new HashMap<String, String>();
        final var envFile = new File(envPath);

        if(!envFile.exists()) {
            System.err.println("Env file does not exist!");
            return env;
        }


        try(final var scanner = new Scanner(envFile, StandardCharsets.UTF_8)){

            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                final var line = scanner.next();
                final var split = line.split("=");
                if(split.length != 2){
                    System.err.println("Invalid line in env file: " + line);
                    continue;
                }
                env.put(split[0], split[1]);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return env;
    }


}
