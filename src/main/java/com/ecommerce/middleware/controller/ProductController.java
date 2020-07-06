package com.ecommerce.middleware.controller;

import com.ecommerce.middleware.dto.BeforeOrderDto;
import com.ecommerce.middleware.dto.ProductDto;
import com.ecommerce.middleware.dto.RestApiResponse;
import com.ecommerce.middleware.dto.UserOrderDto;
import com.ecommerce.middleware.pojo.Product;
import com.ecommerce.middleware.repository.ProductRepository;
import com.ecommerce.middleware.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/api/products")
    public RestApiResponse getAllProducts() {
        //return new RestApiResponse ("success", productService.getAllProducts(), "all products fetched successfully");
        return new RestApiResponse ("success", (List<Product>) productRepository.findAll(), "all products fetched successfully");
    }

    @GetMapping("/api/products/{productId}")
    public RestApiResponse getProduct(@PathVariable("productId") int productId) {
        return new RestApiResponse ("success", productRepository.findById(productId).orElse(null), "all products fetched successfully");
    }

    @PostMapping(value = "/api/products", consumes = {"multipart/form-data"})
    public RestApiResponse addProduct(@RequestParam(required = true, value = "product") String product, @RequestParam(required = true, value = "file") MultipartFile imageInReq) throws IOException, SQLException {

        ObjectMapper objectMapper = new ObjectMapper();
        Product productObj = objectMapper.readValue(product, Product.class);
        productObj.setImage(new SerialBlob(imageInReq.getBytes()));
        ProductDto productToSend = productService.addProduct(productObj);
        if(productToSend != null)
            return new RestApiResponse("success", productToSend, "Product updated successfully");
        else
            return new RestApiResponse("failure", null, "Product update failed");
    }

    @PatchMapping("/api/products")
    public RestApiResponse updateProduct(@RequestBody Map<String, Object> product) {
        Product updatedProduct = productService.updateProduct(product);
        if(updatedProduct != null)
            return new RestApiResponse("success", updatedProduct, "Product updated successfully");
        else
            return new RestApiResponse("failure", null, "Product update failed");
    }

    @GetMapping(value = "/api/before-order")
    public ResponseEntity<List<BeforeOrderDto>> getAllBeforeOrders(@RequestParam(value = "userId") int userId, @RequestParam(required = false, value = "beforeOrderType") String beforeOrderType) {
        List<BeforeOrderDto> beforeOrderDtos = productService.getAllBeforeOrders(userId, beforeOrderType);
        return ResponseEntity.ok(beforeOrderDtos);
    }

    @PostMapping(value = "/api/before-order")
    public ResponseEntity<BeforeOrderDto> addBeforeOrder(@RequestBody BeforeOrderDto beforeOrderDtoReq) {
        BeforeOrderDto beforeOrderDto = productService.addBeforeOrder(beforeOrderDtoReq);
        return ResponseEntity.ok(beforeOrderDto);
    }

    @DeleteMapping(value = "/api/before-order")
    public ResponseEntity<String> deleteBeforeOrder(@RequestParam(value = "beforeOrderId") int beforeOrderId) {
        String response = productService.deleteBeforeOrder(beforeOrderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/orders")
    public ResponseEntity<List<UserOrderDto>> getAllUserOrders(@RequestParam(value = "userId") int userId) {
        List<UserOrderDto> userOrderDtos = productService.getAllUserOrders(userId);
        return ResponseEntity.ok(userOrderDtos);
    }

    @PostMapping(value = "/api/orders")
    public ResponseEntity<UserOrderDto> addNewUserOrders(@RequestParam(value = "userId") int userId, @RequestParam(value = "productId") int productId) {
        UserOrderDto userOrderDto = productService.addNewUserOrders(userId, productId);
        return ResponseEntity.ok(userOrderDto);
    }

    @GetMapping(value = "/api/download-order-summery")
    public void downloadOrderSummery(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userId", defaultValue = "1") int userId) throws IOException {
        String fileName = productService.downloadOrderSummery(userId);

        File file = new File(fileName);
        if(file.exists()) {
            response.setContentType("application/pdf");
            //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
