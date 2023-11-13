package xyz.hlafaille.espresso;

import com.google.gson.Gson;
import xyz.hlafaille.espresso.configuration.dto.ConfigurationFileDto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("example/example.espresso.json"))) {
            ConfigurationFileDto configurationFileDto = gson.fromJson(reader, ConfigurationFileDto.class);
            System.out.println(configurationFileDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}