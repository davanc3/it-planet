package ru.vantsyn.it.planet.model.dto.animalLocation;

import jakarta.validation.constraints.Min;
import ru.vantsyn.it.planet.model.validate.ValidDateFormat;

public class AnimalLocationSearchDTO {
    @ValidDateFormat
    private String startDateTime;
    @ValidDateFormat
    private String endDateTime;
    @Min(0)
    private int from = 0;
    @Min(1)
    private int size = 10;

    public AnimalLocationSearchDTO() {
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        if (startDateTime != null) {
            startDateTime = startDateTime.replaceAll(" ", "+");
        }
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        if (endDateTime != null) {
            endDateTime = endDateTime.replaceAll(" ", "+");
        }
        this.endDateTime = endDateTime;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AnimalLocationSearchDTO{" +
                "startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", from=" + from +
                ", size=" + size +
                '}';
    }
}
