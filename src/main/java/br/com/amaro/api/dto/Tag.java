package br.com.amaro.api.dto;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Tag {
    NEUTRO("neutro"),
    VELUDO("veludo"),
    COURO("couro"),
    BASICS("basics"),
    FESTA("festa"),
    WORKWEAR("workwear"),
    INVERNO("inverno"),
    BOHO("boho"),
    ESTAMPAS("estampas"),
    BALADA("balada"),
    COLORIDO("colorido"),
    CASUAL("casual"),
    LISO("liso"),
    MODERNO("moderno"),
    PASSEIO("passeio"),
    METAL("metal"),
    VIAGEM("viagem"),
    DELICADO("delicado"),
    DESCOLADO("descolado"),
    ELASTANO("elastano");

    private String name;

    Tag(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Map<String, Integer> valuesAsMap() {
        return Arrays.stream(Tag.values()).collect(Collectors.toMap(Tag::getName, i -> 0, (u, v) -> u, LinkedHashMap::new));
    }
}
