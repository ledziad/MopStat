package com.mopstat.mopstat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping
    public ResponseEntity<WeatherDto> getWeather() {
        double lat = 54.0387;
        double lon = 21.7640;
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current_weather=true";
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Object> current = (Map<String, Object>) response.get("current_weather");
            double temp = (Double) current.get("temperature");
            int code = (Integer) current.get("weathercode");
            String desc = getWeatherDesc(code);
            String emoji = getWeatherEmoji(code);

            WeatherDto dto = new WeatherDto(temp, desc, emoji);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(502).body(new WeatherDto(null, "Błąd pobierania pogody", "❓"));
        }
    }

    private String getWeatherDesc(int code) {
        // Oficjalne kody: https://open-meteo.com/en/docs#api_form
        switch (code) {
            case 0: return "Słonecznie";
            case 1: case 2: case 3: return "Częściowo pochmurno";
            case 45: case 48: return "Mgła";
            case 51: case 53: case 55: return "Mżawka";
            case 56: case 57: return "Marznąca mżawka";
            case 61: case 63: case 65: return "Deszcz";
            case 66: case 67: return "Marznący deszcz";
            case 71: case 73: case 75: return "Śnieg";
            case 77: return "Ziarnisty śnieg";
            case 80: case 81: case 82: return "Przelotny deszcz";
            case 85: case 86: return "Przelotne opady śniegu";
            case 95: return "Burza";
            case 96: case 99: return "Burza z gradem";
            default: return "Nieznana pogoda";
        }
    }

    private String getWeatherEmoji(int code) {
        switch (code) {
            case 0: return "☀️";
            case 1: case 2: case 3: return "⛅";
            case 45: case 48: return "🌫️";
            case 51: case 53: case 55: return "🌦️";
            case 56: case 57: return "🌧️";
            case 61: case 63: case 65: return "🌧️";
            case 66: case 67: return "🌧️";
            case 71: case 73: case 75: return "❄️";
            case 77: return "🌨️";
            case 80: case 81: case 82: return "🌦️";
            case 85: case 86: return "🌨️";
            case 95: return "🌩️";
            case 96: case 99: return "⛈️";
            default: return "❓";
        }
    }

    static class WeatherDto {
        public Double temperature;
        public String description;
        public String emoji;

        public WeatherDto(Double temperature, String description, String emoji) {
            this.temperature = temperature;
            this.description = description;
            this.emoji = emoji;
        }
    }
}
