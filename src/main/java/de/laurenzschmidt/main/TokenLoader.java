package de.laurenzschmidt.main;


import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TokenLoader {

    public String getValue() {

        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(Resources.toString(Resources.getResource("secrets/token.properties"), StandardCharsets.UTF_8), JsonObject.class);
            return jsonObject.get("token").getAsString();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}