package model;

/**
 * Created by ShimaK on 08-Apr-17.
 */
public class Card {
    private String number;
    private String name;

    public Card(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
