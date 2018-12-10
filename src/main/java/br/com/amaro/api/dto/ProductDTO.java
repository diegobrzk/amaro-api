package br.com.amaro.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Double similarity;

    @Singular("tag")
    private List<String> tags;

    @Singular("tagVector")
    private List<Integer> tagsVector;
}
