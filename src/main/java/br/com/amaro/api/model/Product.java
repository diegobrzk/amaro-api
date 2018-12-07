package br.com.amaro.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "amaro", type = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

    @Id
    private String id;

    private String name;
    private List<String> tags;
    private List<Integer> tagsVector;

}
