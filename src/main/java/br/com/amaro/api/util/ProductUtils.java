package br.com.amaro.api.util;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.Tag;

import java.util.ArrayList;
import java.util.Map;

public class ProductUtils {

    public static void populateProductTagsVector(final ProductDTO product) {
        final Map<String, Integer> tags = Tag.valuesAsMap();
        product.getTags().forEach(t -> tags.replace(t, 1));
        product.setTagsVector(new ArrayList<>(tags.values()));
    }

}
