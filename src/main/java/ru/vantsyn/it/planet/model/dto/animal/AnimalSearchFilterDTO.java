package ru.vantsyn.it.planet.model.dto.animal;

import jakarta.validation.constraints.Min;
import ru.vantsyn.it.planet.model.validate.IsGender;
import ru.vantsyn.it.planet.model.validate.IsLifeStatus;
import ru.vantsyn.it.planet.model.validate.ValidDateFormat;

public class AnimalSearchFilterDTO {
    @ValidDateFormat
    private String startDateTime;
    @ValidDateFormat
    private String endDateTime;
    @Min(1)
    private Integer chipperId;
    @Min(1)
    private Long chippingLocationId;
    @IsLifeStatus
    private String lifeStatus;
    @IsGender
    private String gender;
    @Min(0)
    private int from = 0;
    @Min(1)
    private int size = 10;

    public AnimalSearchFilterDTO() {
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

    public Integer getChipperId() {
        return chipperId;
    }

    public void setChipperId(int chipperId) {
        this.chipperId = chipperId;
    }

    public Long getChippingLocationId() {
        return chippingLocationId;
    }

    public void setChippingLocationId(long chippingLocationId) {
        this.chippingLocationId = chippingLocationId;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        return "AnimalSearchFilterDTO{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", chipperId=" + chipperId +
                ", chipperLocationId=" + chippingLocationId +
                ", lifeStatus='" + lifeStatus + '\'' +
                ", gender='" + gender + '\'' +
                ", from=" + from +
                ", size=" + size +
                '}';
    }
}
