package br.com.amaro.api.service.impl;

import br.com.amaro.api.converter.ProductConverter;
import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.ProductListDTO;
import br.com.amaro.api.dto.Tag;
import br.com.amaro.api.model.Product;
import br.com.amaro.api.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static br.com.amaro.api.util.ProductUtils.populateProductTagsVector;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DefaultProductServiceTest {

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

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductConverter converter;

    @InjectMocks
    private DefaultProductService productService;

    @Test
    public void shouldPopulateTagsVector() {

        final ProductDTO p1 = ProductDTO.builder().id(P1_ID).name(P1_NAME).tags(asList(P1_TAGS)).build();

        final Map<String, Integer> tags = Tag.valuesAsMap();

        tags.replace(Tag.NEUTRO.getName(), 1);
        tags.replace(Tag.VELUDO.getName(), 1);

        P1_VECTORTAGS = new ArrayList<>(tags.values());

        final ProductListDTO list = productService.populateTagsVectors(singletonList(p1));

        final ProductDTO product = list.getProducts().get(0);

        assertTrue(!CollectionUtils.isEmpty(product.getTagsVector()));
        assertTrue(P1_VECTORTAGS.size() == product.getTagsVector().size());

        IntStream.range(0, P1_VECTORTAGS.size()).forEach(
                i -> assertEquals(product.getTagsVector().get(i), P1_VECTORTAGS.get(i)));
    }

    @Test
    public void shouldPopulateSimilarity() {

        final ProductDTO p1 = ProductDTO.builder().id(P1_ID).name(P1_NAME).tags(asList(P1_TAGS)).build();
        final ProductDTO p2 = ProductDTO.builder().id(P2_ID).name(P2_NAME).tags(asList(P2_TAGS)).build();
        final ProductDTO p3 = ProductDTO.builder().id(P3_ID).name(P3_NAME).tags(asList(P3_TAGS)).build();

        populateProductTagsVector(p1);
        populateProductTagsVector(p2);
        populateProductTagsVector(p3);

        final Product product = Product.builder().id(p1.getId()).name(p1.getName()).tags(p1.getTags()).tagsVector(p1.getTagsVector()).build();

        when(repository.findById(P1_ID)).thenReturn(Optional.of(product));

        final ProductListDTO list = productService.populateSimilarity(asList(p1, p2, p3), P1_ID);

        assertEquals(list.getProducts().get(0).getSimilarity(), P2_SIMILARITY);
        assertEquals(list.getProducts().get(1).getSimilarity(), P3_SIMILARITY);
    }

    @Test
    public void shouldReturnNullWhenProductNotFound() {
        assertNull(productService.populateSimilarity(emptyList(), P1_ID));
    }
}
