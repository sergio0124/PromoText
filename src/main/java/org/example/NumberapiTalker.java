package org.example;

import org.example.model.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NumberapiTalker {

    private final static String NUMBERSAPI_URL = "http://numbersapi.com/<number>/trivia";
    private final static String NUMBER_STRING_PATTERN = "<number>";

    private String urlAddress;
    private Response response;

    public String makeRequest() {
        int number = new Random().nextInt(0, 1000);
        this.urlAddress = NUMBERSAPI_URL.replaceAll(NUMBER_STRING_PATTERN, String.valueOf(number));
        this.response = makeResponse(urlAddress);

        if (response == null) {
            return "some error occurred";
        }

        StringBuilder builder = new StringBuilder();

        builder.append(urlAddress).append("\n").append(response.getText()).append("\n");
        builder.append(printMap()).append("\n");
        builder.append(printFrequency()).append("\n");
        builder.append(printEntriesWithNearestValue()).append("\n");

        return builder.toString();
    }

    private static Response makeResponse(String urlAddress) {
        URL url;
        Response response = null;
        try {
            url = new URL(urlAddress);
            response = new Response(RequestSender.sendGET(url));
        } catch (IOException e) {
            System.out.println("");
        }
        return response;
    }


    public String printMap() {
        StringBuilder builder = new StringBuilder();
        builder.append("Частоты:\n");
        for (var entry : response.getUniqueCharacters().entrySet()
        ) {
            String raz = "раз";
            if (entry.getValue() % 10 >= 2 && entry.getValue() % 10 <= 4) {
                raz = "раза";
            }
            builder.append(String.format("%c - %d %s\n", entry.getKey(), entry.getValue(), raz));
        }
        return builder.toString();
    }


    public String printFrequency() {
        var map = response.getUniqueCharacters();
        return String.format("Среднее значение частоты %d/%d = %.5f\n",
                response.getText().length(),
                response.getUniqueCharacters().size(),
                (double) map.values().stream().mapToInt(rec -> rec).sum() / map.size());
    }


    public String printEntriesWithNearestValue() {
        var map = response.getUniqueCharacters();

        double frequency = (double) map.values().stream().mapToInt(rec -> rec).sum() / map.size();
        final var round = new Object() {
            int value = 0;
        };
        map.forEach((key, value) -> {
            if (Math.abs(value - frequency) < Math.abs(round.value - frequency)) {
                round.value = value;
            }
        });
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Символы, которые соответствуют условию наиболее близкого значения частоты к среднему значанию (%d):", round.value));

        Map<Character, Integer> nearest = map
                .entrySet()
                .stream()
                .filter(rec -> rec.getValue() == round.value)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        TreeMap::new
                ));

        nearest.forEach((key, value) -> builder.append(String.format(" %c(%d),", key, Integer.valueOf(key))));
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }


    public String getUrlAddress() {
        return urlAddress;
    }

    public Response getResponse() {
        return response;
    }
}
