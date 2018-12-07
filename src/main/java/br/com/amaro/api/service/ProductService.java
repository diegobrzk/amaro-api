package br.com.amaro.api.service;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.ProductListDTO;

import java.util.List;

public interface ProductService {
    ProductListDTO populateSimilarity(final List<ProductDTO> products, final String code);
    ProductListDTO populateTagsVectors(final List<ProductDTO> products);
}
