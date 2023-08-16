package ru.vantsyn.it.planet.model.dto.animal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.vantsyn.it.planet.model.validate.*;

import java.util.List;

public class AnimalCreateDTO {
    @NotNull
    @NotEmptyCollection
    @AllValuesNotNull
    @ValidCollectionValues
    private List<Long> animalTypes;
    @NotNull
    @HardMin(0)
    private Float weight;
    @NotNull
    @HardMin(0)
    private Float length;
    @NotNull
    @HardMin(0)
    private Float height;
    @NotNull
    @IsGender
    private String gender;
    @NotNull
    @Min(1)
    private Integer chipperId;
    @NotNull
    @Min(1)
    private Long chippingLocationId;

    public AnimalCreateDTO() {
    }

    public List<Long> getAnimalTypes() {
        return animalTypes;
    }

    public void setAnimalTypes(List<Long> animalTypes) {
        this.animalTypes = animalTypes;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
