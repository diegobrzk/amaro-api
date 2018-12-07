package br.com.amaro.api.converter;

import br.com.amaro.api.dto.ProductDTO;
import br.com.amaro.api.model.Product;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;


@WritingConverter
@Component
public class ProductConverter {

    public Product convert(final ProductDTO source) {
        return Product.builder()
                .id(source.getId())
                .name(source.getName())
                .tags(source.getTags())
                .tagsVector(source.getTagsVector()).build();
    }

}
