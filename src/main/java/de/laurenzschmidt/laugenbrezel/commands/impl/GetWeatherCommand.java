package de.laurenzschmidt.laugenbrezel.commands.impl;

import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import de.laurenzschmidt.laugenbrezel.commands.Command;
import de.laurenzschmidt.laugenbrezel.objects.WeatherData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class GetWeatherCommand extends Command {


    public GetWeatherCommand(String name) {
        super(name);
    }

    private static final String apiKey = "984ba0f3806e4de3b1a0a6064b638920";
    private static final Gson GSON = new Gson();

    @Override
    void execute(SlashCommandInteractionEvent event) {
        String city = Objects.requireNonNull(event.getOption("stadt")).getAsString();
        String apiUrl = "https://api.weatherbit.io/v2.0/current?city=" + city + "&key=" + apiKey;
        try {
            JsonNode response = Unirest.get(apiUrl).header("accept", "application/json").asJson().getBody();
            String jsonString = response.toString();
            // für vollständigen, aber blöd sortierten daten dem link "apiUrl" folgen
            // String jsonString = "{\"count\":1,\"data\":[{\"app_temp\":7.8,\"aqi\":9,\"city_name\":\"Berlin\",\"clouds\":97,\"country_code\":\"DE\",\"datetime\":\"2023-12-31:14\",\"dewpt\":3.9,\"dhi\":39.52,\"dni\":342.99,\"elev_angle\":5.84,\"ghi\":64.82,\"gust\":7.72,\"h_angle\":67.5,\"lat\":52.52437,\"lon\":13.41053,\"ob_time\":\"2023-12-31 14:18\",\"pod\":\"d\",\"precip\":0,\"pres\":998,\"rh\":77,\"slp\":1003.5,\"snow\":0,\"solar_rad\":18.1,\"sources\":[\"analysis\",\"C6421\",\"radar\",\"satellite\"],\"state_code\":\"16\",\"station\":\"C6421\",\"sunrise\":\"07:16\",\"sunset\":\"15:01\",\"temp\":7.8,\"timezone\":\"Europe/Berlin\",\"ts\":1704032307,\"uv\":0.4184287,\"vis\":16,\"weather\":{\"code\":804,\"icon\":\"c04d\",\"description\":\"Overcast clouds\"},\"wind_cdir\":\"SSE\",\"wind_cdir_full\":\"south-southeast\",\"wind_dir\":148,\"wind_spd\":4.12}]}";
            WeatherData weatherData = GSON.fromJson(jsonString, WeatherData.class);
            String temperature = String.valueOf(weatherData.data.get(0).temp);
            String sunriseTime = String.valueOf(weatherData.data.get(0).sunrise);
            String sunsetTime = String.valueOf(weatherData.data.get(0).sunset);
            String cloudPercentage = String.valueOf(weatherData.data.get(0).clouds);
            String windSpeed = String.valueOf(weatherData.data.get(0).wind_spd);
            String cityName = String.valueOf(weatherData.data.get(0).city_name);
            String currentDate = String.valueOf(weatherData.data.get(0).datetime);
            String description = String.valueOf(weatherData.data.get(0).weather.description);
            String uv = String.valueOf(weatherData.data.get(0).uv);
            // get user
            StringBuilder botschaft = new StringBuilder();

            String temperatureOut;
            String windSpeedOut;
            if (Double.parseDouble(temperature) <= 5) {
                temperatureOut = "Brrr, dass ist kalt.";
            } else if (Double.parseDouble(temperature) <= 15) {
                temperatureOut = "Ganz schön frisch.";
            } else {
                temperatureOut = "Heute ist es angenehm warm.";
            }
            if (Double.parseDouble(windSpeed) < 2) {
                windSpeedOut = "Windstille";
            } else if (Double.parseDouble(windSpeed) < 6) {
                windSpeedOut = "ein leiser Zug";
            } else if (Double.parseDouble(windSpeed) < 11) {
                windSpeedOut = "eine leichte Brise";
            } else if (Double.parseDouble(windSpeed) < 18) {
                windSpeedOut = "eine schwache Brise";
            } else if (Double.parseDouble(windSpeed) < 28) {
                windSpeedOut = "eine mäßige Brise";
            } else if (Double.parseDouble(windSpeed) < 39) {
                windSpeedOut = "eine frische Brise";
            } else if (Double.parseDouble(windSpeed) < 50) {
                windSpeedOut = "starker Wind";
            } else if (Double.parseDouble(windSpeed) < 61) {
                windSpeedOut = "steifer Wind";
            } else if (Double.parseDouble(windSpeed) < 74) {
                windSpeedOut = "stürmischer Wind";
            } else if (Double.parseDouble(windSpeed) < 86) {
                windSpeedOut = "ein Sturm";
            } else if (Double.parseDouble(windSpeed) < 102) {
                windSpeedOut = "ein schwerer Sturm";
            } else if (Double.parseDouble(windSpeed) < 118) {
                windSpeedOut = "ein orkanartiger Sturm";
            } else {
                windSpeedOut = "ein Orkan";
            }
            LocalDateTime currentTime = LocalDateTime.now();
            ZoneId berlinZone = ZoneId.of("Europe/Berlin");
            ZonedDateTime zonedDateTime = ZonedDateTime.of(currentTime, berlinZone);
            boolean isDaylightSavingTime = zonedDateTime.getZone().getRules().isDaylightSavings(zonedDateTime.toInstant());

            String[] x;
            String[] z;
            String sunriseTime2 = sunriseTime;
            String sunsetTime2 = sunsetTime;
            String[] f;

            int y;
            int e;
            int t;
            if (isDaylightSavingTime) {
                // sommerzeit
                // weatherbit  gibt die zeit immer in der sommer zeit aus, deshalb die überprüfung, um gegenbenenfalls die uhrzeit anzupassen
            } else {
                x = sunriseTime.split(":");
                y = Integer.parseInt(x[0]);
                y++;
                x[0] = "" + y;
                sunriseTime2 = x[0] + ":" + x[1];

                z = sunsetTime.split(":");
                e = Integer.parseInt(z[0]);
                e++;
                z[0] = "" + e;
                sunsetTime2 = z[0] + ":" + z[1];

                f = currentDate.split(":");
                t = Integer.parseInt(f[1]);
                t++;
                f[1] = "" + t;
            }
            String userName = Objects.requireNonNull(event.getMember()).getUser().getName();
            botschaft.append("Hallo ").append(userName).append(",\nin der Stadt ").append(cityName).append(" beginnt ein neuer Tag mit einem atemberaubenden Sonnenaufgang um ").append(sunriseTime2)
                    .append(" Uhr. Der Tag endet mit einem malerischen Sonnenuntergang um ").append(sunsetTime2).append(" Uhr. Die Himmelsschicht zeigt ").append(cloudPercentage)
                    .append("% Wolken, was auf eine ").append(description).append(" Szenerie hindeutet.\nDie aktuelle Temperatur beträgt ").append(temperature).append(" °C. ").append(temperatureOut).append(" Der Wind weht mit einer Geschwindigkeit von ")
                    .append(windSpeed).append(" km/h, wodurch ").append(windSpeedOut).append(" entsteht. Trotz einiger Wolken ist die UV-Strahlung mit einem Wert von ")
                    .append(uv).append(" auf einem moderaten Niveau, aber es wird empfohlen, angemessene Sonnenschutzmaßnahmen zu ergreifen.\n")
                    .append("\nDer Zeitpunkt dieser Messwerte ist am ").append(currentDate).append(" Uhr").append(" gewesen, und es scheint, als wäre es ein perfekter Tag, um die Schönheit der Natur zu genießen.");
            System.out.println(botschaft);
            event.reply(String.valueOf(botschaft)).setEphemeral(false).queue();
        } catch (Exception exception) {
            event.reply("Error 40 discovered!").setEphemeral(false).queue();
        }
    }

}
