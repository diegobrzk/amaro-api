package br.com.amaro.api.service;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.ProductListDTO;

import java.util.List;

public interface ProductService {

    /**
     * This implementation calculate the most similar products for a product in a list.
     *
     * @param products
     * @param code
     * @return Retrieve a {@link ProductListDTO} that contains the first three most similar products
     * with the one that contains code as {@param code}.
     */
    ProductListDTO populateSimilarity(final List<ProductDTO> products, final String code);

    /**
     * This implementation calculate the tags vector for a list of products.
     *
     * @param products
     * @return Retrieve a {@link ProductListDTO} that contains the same products with the calculated
     * tags vector.
     */
    ProductListDTO populateTagsVectors(final List<ProductDTO> products);
}
