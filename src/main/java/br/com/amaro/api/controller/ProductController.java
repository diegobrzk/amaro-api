package br.com.amaro.api.controller;

import br.com.amaro.api.dto.ProductListDTO;
import br.com.amaro.api.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Calculate the tags vector for products.", response = ProductListDTO.class)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success calculating the vector tags."),
            @ApiResponse(code = 401, message = "Not authorized."),
            @ApiResponse(code = 403, message = "The requested resource is forbidden"),
            @ApiResponse(code = 404, message = "The requested API was not found.")
    })
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public ProductListDTO calculateTags(@RequestBody final ProductListDTO products) {
        return productService.populateTagsVectors(products.getProducts());
    }

    @ApiOperation(value = "Calculate product similarity between the others.", response = ProductListDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success calculating the similarity."),
            @ApiResponse(code = 401, message = "Not authorized."),
            @ApiResponse(code = 403, message = "The requested resource is forbidden"),
            @ApiResponse(code = 404, message = "The requested API was not found.")
    })
    @RequestMapping(value = "/similarity", method = RequestMethod.POST)
    public ProductListDTO calculateSimilarity(@RequestBody final ProductListDTO products) {
        return productService.populateSimilarity(products.getProducts(), products.getCode());
    }

}
