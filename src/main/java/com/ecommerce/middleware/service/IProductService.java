package com.ecommerce.middleware.service;

import com.ecommerce.middleware.dto.BeforeOrderDto;
import com.ecommerce.middleware.dto.ProductDto;
import com.ecommerce.middleware.dto.UserOrderDto;
import com.ecommerce.middleware.pojo.Product;
import com.itextpdf.text.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IProductService {

    public List<ProductDto> getAllProducts();

    public ProductDto addProduct(Product product);

    public Product updateProduct(Map<String, Object> product);

    public List<BeforeOrderDto> getAllBeforeOrders(int userId, String beforeOrderType);

    public BeforeOrderDto addBeforeOrder(BeforeOrderDto beforeOrderDto);

    public String deleteBeforeOrder(int beforeOrderId);

    public List<UserOrderDto> getAllUserOrders(int userId);

    public UserOrderDto addNewUserOrders(int userId, int productId);

    public String downloadOrderSummery(int userId) throws IOException;
}
