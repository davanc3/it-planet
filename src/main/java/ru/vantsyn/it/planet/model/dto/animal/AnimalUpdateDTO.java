package ru.vantsyn.it.planet.model.dto.animal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.vantsyn.it.planet.model.validate.*;

public class AnimalUpdateDTO {
    @NotNull
    @HardMin(0)
    private Float weight;
    @NotNull
    @HardMin(0)
    private Float length;
    @NotNull
    @HardMin(0)
    private Float height;
    @IsGender
    private String gender;
    @IsLifeStatus
    private String lifeStatus;
    @NotNull
    @Min(1)
    private Integer chipperId;
    @NotNull
    @Min(1)
    private Long chippingLocationId;

    public AnimalUpdateDTO() {
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public Integer getChipperId() {
        return chipperId;
    }

    public void setChipperId(Integer chipperId) {
        this.chipperId = chipperId;
    }

    public Long getChippingLocationId() {
        return chippingLocationId;
    }

    public void setChippingLocationId(Long chippingLocationId) {
        this.chippingLocationId = chippingLocationId;
    }
}
