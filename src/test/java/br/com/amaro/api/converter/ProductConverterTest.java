package br.com.amaro.api.converter;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.Tag;
import br.com.amaro.api.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ProductConverterTest {

    private static final String PRODUCT_ID = "8001";
    private static final String PRODUCT_NAME = "PRODUCT 1";
    private static final String[] PRODUCT_TAGS = new String[]{Tag.VELUDO.getName(), Tag.MODERNO.getName()};

    @InjectMocks
    private ProductConverter converter;

    @Test
    public void shouldConvert() {

        final ProductDTO productDTO = ProductDTO.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .tags(asList(PRODUCT_TAGS))
                .build();

        final Product product = converter.convert(productDTO);

        assertEquals(product.getId(), PRODUCT_ID);
        assertEquals(product.getName(), PRODUCT_NAME);
        assertArrayEquals(product.getTags().toArray(), PRODUCT_TAGS);
    }

}
