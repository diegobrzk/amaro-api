package br.com.amaro.api.controller;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.ProductListDTO;
import br.com.amaro.api.dto.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static final String P1_ID = "8001";
    private static final String P1_NAME = "PRODUCT 1";
    private static final String[] P1_TAGS = new String[]{Tag.NEUTRO.getName(), Tag.VELUDO.getName()};
    private List<Integer> P1_VECTORTAGS;

    private static final String P2_ID = "8002";
    private static final String P2_NAME = "PRODUCT 2";
    private static final String[] P2_TAGS = new String[]{Tag.NEUTRO.getName(), Tag.VELUDO.getName()};
    private static final Double P2_SIMILARITY = 1.0D;

    private static final String P3_ID = "8003";
    private static final String P3_NAME = "PRODUCT 3";
    private static final String[] P3_TAGS = new String[]{Tag.VELUDO.getName(), Tag.MODERNO.getName()};
    private static final Double P3_SIMILARITY = 0.41D;

    private static final String P_NOTFOUND = "0000";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnVectorTags() {

        final Map<String, Integer> tags = Tag.valuesAsMap();

        tags.replace(Tag.NEUTRO.getName(), 1);
        tags.replace(Tag.VELUDO.getName(), 1);

        P1_VECTORTAGS = new ArrayList<>(tags.values());

        final ProductDTO p1 = ProductDTO.builder().id(P1_ID).name(P1_NAME).tags(asList(P1_TAGS)).build();

        final ResponseEntity<ProductListDTO> response = this.restTemplate.postForEntity(
                "/product/tags", ProductListDTO.builder().product(p1).build(), ProductListDTO.class);

        assertEquals("HttpStatus from request should return OK (200).", HttpStatus.OK, response.getStatusCode());

        final ProductDTO product = response.getBody().getProducts().get(0);

        assertTrue(!CollectionUtils.isEmpty(product.getTagsVector()));
        assertTrue(P1_VECTORTAGS.size() == product.getTagsVector().size());

        IntStream.range(0, P1_VECTORTAGS.size()).forEach(
                i -> assertEquals(product.getTagsVector().get(i), P1_VECTORTAGS.get(i)));
    }

    @Test
    public void shouldReturnSimilarity() {

        final ProductDTO p1 = ProductDTO.builder().id(P1_ID).name(P1_NAME).tags(asList(P1_TAGS)).build();
        final ProductDTO p2 = ProductDTO.builder().id(P2_ID).name(P2_NAME).tags(asList(P2_TAGS)).build();
        final ProductDTO p3 = ProductDTO.builder().id(P3_ID).name(P3_NAME).tags(asList(P3_TAGS)).build();

        final ProductListDTO products = ProductListDTO.builder().product(p1).product(p2).product(p3).build();

        final ProductListDTO tags = this.restTemplate.postForEntity("/product/tags", products, ProductListDTO.class).getBody();

        tags.setCode(P1_ID);

        final ResponseEntity<ProductListDTO> response = this.restTemplate.postForEntity("/product/similarity", tags, ProductListDTO.class);

        assertEquals("HttpStatus from request should return OK (200).", HttpStatus.OK, response.getStatusCode());

        assertEquals(P2_SIMILARITY, response.getBody().getProducts().get(0).getSimilarity());
        assertEquals(P3_SIMILARITY, response.getBody().getProducts().get(1).getSimilarity());
    }

    @Test
    public void shouldNotFoundProduct() {

        final ProductListDTO tags = ProductListDTO.builder().build();

        tags.setCode(P_NOTFOUND);

        final ResponseEntity<ProductListDTO> response = this.restTemplate.postForEntity("/product/similarity", tags, ProductListDTO.class);

        assertEquals("HttpStatus from request should return OK (200).", HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
