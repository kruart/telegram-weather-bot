package telegram.bot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=b26c753e03712deb5745d9d6fdd63bb8&units=metric", message));

        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();

        while(in.hasNext()) {
            result.append(in.nextLine());
        }

        return result.toString();
    }
}
