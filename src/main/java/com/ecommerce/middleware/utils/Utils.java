package com.ecommerce.middleware.utils;

import com.ecommerce.middleware.dto.BeforeOrderDto;
import com.ecommerce.middleware.dto.ProductDto;
import com.ecommerce.middleware.dto.UserOrderDto;
import com.ecommerce.middleware.pojo.BeforeOrder;
import com.ecommerce.middleware.pojo.Product;
import com.ecommerce.middleware.pojo.UserOrder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class Utils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static final DateTimeFormatter dateFormatterSimple = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate strToDate(String date) {
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public static String dateToStr(LocalDateTime localDateTime) {
        return localDateTime.format(dateFormatterSimple);
    }

    public static ProductDto convertProductToProductDto(Product product) {
        if(product == null)
            return null;
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setOwner(product.getOwner());
        productDto.setPrice(product.getPrice());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setQuantity(product.getQuantity());

        Blob dbImage = product.getImage();
        if(dbImage != null){
            try {
                byte byteArray[] = dbImage.getBytes(1,(int)dbImage.length());
                String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(byteArray);
                productDto.setImage(encodeImage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return productDto;
    }

    public byte[] readFile(String file) throws IOException {
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        byte[] response = null;
        try {
            File f = new File(file);
            fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
            response = bos != null ? bos.toByteArray() : null;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        } finally {
            if(bos != null)
                bos.close();
            if(fis != null)
                fis.close();
        }
        return response;
    }

    public void readPictureFromDB(Blob dbImage, String filename) throws IOException {
        FileOutputStream fos = null;
        try{
            byte byteArray[] = dbImage.getBytes(1,(int)dbImage.length());
            FileOutputStream outPutStream = new FileOutputStream(filename);
            outPutStream.write(byteArray);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null)
                fos.close();
        }
    }

    public static BeforeOrderDto convertBeforeOrderToDto(BeforeOrder beforeOrder) {
        BeforeOrderDto beforeOrderDto = new BeforeOrderDto();
        beforeOrderDto.setBeforeOrderId(beforeOrder.getBeforeOrderId());
        beforeOrderDto.setBeforeOrderType(beforeOrder.getBeforeOrderType());
        beforeOrderDto.setOrderDate(beforeOrder.getOrderDate());
        beforeOrderDto.setUser(beforeOrder.getUser());
        beforeOrderDto.setProduct(convertProductToProductDto(beforeOrder.getProduct()));
        return beforeOrderDto;
    }

    public static UserOrderDto convertUserOrderToDto(UserOrder userOrder) {
        UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setUserOrderId(userOrder.getUserOrderId());
        userOrderDto.setUser(userOrder.getUser());
        userOrderDto.setOrderDate(userOrder.getOrderDate());
        userOrderDto.setStatus(userOrder.getStatus());
        userOrderDto.setProduct(convertProductToProductDto(userOrder.getProduct()));
        return userOrderDto;
    }
}
