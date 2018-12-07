package br.com.amaro.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ProductDTO> products = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
}
