package br.com.amaro.api.controller;

import br.com.amaro.api.dto.ProductListDTO;
import br.com.amaro.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public ProductListDTO calculateTags(@RequestBody final ProductListDTO json) {
        return productService.populateTagsVectors(json.getProducts());
    }

    @RequestMapping(value = "/similarity")
    public ProductListDTO calculateSimilarity(@RequestBody final ProductListDTO json) {
        return productService.populateSimilarity(json.getProducts(), json.getCode());
    }

}
