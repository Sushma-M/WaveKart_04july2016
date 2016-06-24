/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.wavemaker.sampleapps.wavekart.eshopping.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import com.wavemaker.sampleapps.wavekart.eshopping.service.ItemorderService;
import com.wavemaker.sampleapps.wavekart.eshopping.service.OrderdetailService;
import com.wavemaker.sampleapps.wavekart.eshopping.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.sampleapps.wavekart.eshopping.Itemorder;
import com.wavemaker.sampleapps.wavekart.eshopping.Orderdetail;
import com.wavemaker.sampleapps.wavekart.eshopping.Product;
import com.wordnik.swagger.annotations.*;
import com.wavemaker.sampleapps.wavekart.eshopping.*;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;

/**
 * Controller object for domain model class Product.
 * @see Product
 */
@RestController(value = "Eshopping.ProductController")
@RequestMapping("/eshopping/Product")
@Api(description = "Exposes APIs to work with Product resource.", value = "ProductController")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    @Qualifier("eshopping.ProductService")
    private ProductService productService;

    @Autowired
    @Qualifier("eshopping.ItemorderService")
    private ItemorderService itemorderService;

    @Autowired
    @Qualifier("eshopping.OrderdetailService")
    private OrderdetailService orderdetailService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of Product instances matching the search criteria.")
    public Page<Product> findProducts(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Products list");
        return productService.findAll(queryFilters, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Product instances.")
    public Page<Product> getProducts(Pageable pageable) {
        LOGGER.debug("Rendering Products list");
        return productService.findAll(pageable);
    }

    @RequestMapping(value = "/{id:.+}/orderdetails", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the orderdetails instance associated with the given id.")
    public Page<Orderdetail> findAssociatedorderdetails(Pageable pageable, @PathVariable("id") Integer id) {
        LOGGER.debug("Fetching all associated orderdetails");
        return orderdetailService.findAssociatedValues(id, "product", "id", pageable);
    }

    @RequestMapping(value = "/{id:.+}/itemorders", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the itemorders instance associated with the given id.")
    public Page<Itemorder> findAssociateditemorders(Pageable pageable, @PathVariable("id") Integer id) {
        LOGGER.debug("Fetching all associated itemorders");
        return itemorderService.findAssociatedValues(id, "product", "id", pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
    protected void setProductService(ProductService service) {
        this.productService = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Creates a new Product instance.")
    public Product createProduct(@RequestBody Product instance) {
        LOGGER.debug("Create Product with information: {}", instance);
        instance = productService.create(instance);
        LOGGER.debug("Created Product with information: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the total count of Product instances.")
    public Long countAllProducts() {
        LOGGER.debug("counting Products");
        Long count = productService.countAll();
        return count;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the Product instance associated with the given id.")
    public Product getProduct(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Product with id: {}", id);
        Product instance = productService.findById(id);
        LOGGER.debug("Product details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Updates the Product instance associated with the given id.")
    public Product editProduct(@PathVariable(value = "id") Integer id, @RequestBody Product instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Product with id: {}", instance.getId());
        instance.setId(id);
        instance = productService.update(instance);
        LOGGER.debug("Product details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Deletes the Product instance associated with the given id.")
    public boolean deleteProduct(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Product with id: {}", id);
        Product deleted = productService.delete(id);
        return deleted != null;
    }
}
