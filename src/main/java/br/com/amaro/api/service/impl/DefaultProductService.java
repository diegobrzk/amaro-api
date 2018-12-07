package br.com.amaro.api.service.impl;

import br.com.amaro.api.converter.ProductConverter;
import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.dto.ProductListDTO;
import br.com.amaro.api.dto.Tag;
import br.com.amaro.api.model.Product;
import br.com.amaro.api.repository.ProductRepository;
import br.com.amaro.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static br.com.amaro.api.util.SimilarityUtils.calculateSimilarity;
import static br.com.amaro.api.util.SimilarityUtils.convertSimilarityArray;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;


@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public ProductListDTO populateSimilarity(final List<ProductDTO> products, final String code) {

        final Optional<Product> p1 = productRepository.findById(code);

        if (!p1.isPresent())
            return null;

        return ProductListDTO.builder()
                .products(products.stream()
                    .filter(p2 -> !p2.getId().equalsIgnoreCase(code))
                    .map(p2 -> populateSimilarity(p1.get(), p2))
                    .sorted(Comparator.comparing(ProductDTO::getSimilarity, reverseOrder()))
                    .limit(3)
                    .map(p2 -> ProductDTO.builder().id(p2.getId()).name(p2.getName()).similarity(p2.getSimilarity()).build())
                    .collect(toList())).build();
    }

    private ProductDTO populateSimilarity(final Product p1, final ProductDTO p2) {

        BigDecimal similarity = BigDecimal.valueOf(calculateSimilarity(
                convertSimilarityArray(p1.getTagsVector()),
                convertSimilarityArray(p2.getTagsVector())));

        p2.setSimilarity(similarity.setScale(2, RoundingMode.HALF_EVEN).doubleValue());

        return p2;
    }

    @Override
    public ProductListDTO populateTagsVectors(final List<ProductDTO> products) {

        products.forEach(p -> p.setTagsVector(getProductTagsVector(p)));

        final List<Product> sources = products.stream().map(p -> productConverter.convert(p)).collect(toList());
        productRepository.saveAll(sources);

        return ProductListDTO.builder().products(products).build();
    }

    private List<Integer> getProductTagsVector(final ProductDTO product) {

        final Map<String, Integer> tags = Tag.valuesAsMap();

        product.getTags().stream().forEach(tag -> tags.replace(tag, 1));

        return new ArrayList<>(tags.values());
    }
}
