package telegram.bot;

import lombok.Data;

@Data
public class Model {
    private String name;
    private Double temp;
    private Double humidity;
    private Double pressure;
    private String icon;
    private String main;
}
