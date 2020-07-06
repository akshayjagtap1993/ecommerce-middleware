package com.ecommerce.middleware.serviceImpl;

import com.ecommerce.middleware.dto.BeforeOrderDto;
import com.ecommerce.middleware.dto.ProductDto;
import com.ecommerce.middleware.dto.UserOrderDto;
import com.ecommerce.middleware.pojo.BeforeOrder;
import com.ecommerce.middleware.pojo.Product;
import com.ecommerce.middleware.pojo.User;
import com.ecommerce.middleware.pojo.UserOrder;
import com.ecommerce.middleware.repository.BeforeOrderRepository;
import com.ecommerce.middleware.repository.ProductRepository;
import com.ecommerce.middleware.repository.UserOrderRepository;
import com.ecommerce.middleware.repository.UserRepository;
import com.ecommerce.middleware.service.IProductService;
import com.ecommerce.middleware.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService implements IProductService {

    @Autowired
    private Utils utils;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BeforeOrderRepository beforeOrderRepository;

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> fetchedProducts = (List<Product>) productRepository.findAll();
        return fetchedProducts.stream().map(p -> Utils.convertProductToProductDto(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDto addProduct(Product product) {
        product = productRepository.save(product);
        return Utils.convertProductToProductDto(product);
    }

    @Override
    public Product updateProduct(Map<String, Object> product) {
        Product fetchedProduct = null;
        try {
            byte[] myFile = utils.readFile("C:\\Users\\akshay\\workspace\\ecommerce\\ecommerce-ui\\public\\images\\samsungm40.jpeg");
            System.out.println("myFile -> " + myFile.length);

            fetchedProduct = productRepository.findById(1).orElse(null);
            if(fetchedProduct != null) {
                fetchedProduct.setImage(new SerialBlob(myFile));
                fetchedProduct = productRepository.save(fetchedProduct);
            }
            utils.readPictureFromDB(fetchedProduct.getImage(), "C:\\Users\\akshay\\workspace\\ecommerce\\ecommerce-ui\\public\\images\\samsungm40_fromDB.jpeg");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fetchedProduct;
    }

    @Override
    public List<BeforeOrderDto> getAllBeforeOrders(int userId, String beforeOrderType) {
        if(beforeOrderType == null || beforeOrderType.trim().isEmpty())
            beforeOrderType = "cart";
        List<BeforeOrder> beforeOrders = beforeOrderRepository.findByUserIdAndBeforeOrderType(userId, beforeOrderType);
        return beforeOrders.stream().map(bo -> Utils.convertBeforeOrderToDto(bo)).collect(Collectors.toList());
    }

    @Override
    public BeforeOrderDto addBeforeOrder(BeforeOrderDto beforeOrderDto) {

        BeforeOrder existingBeforeOrder = beforeOrderRepository.findByUserIdAndProductId(beforeOrderDto.getUser().getUserId(), beforeOrderDto.getProduct().getProductId());
        if(existingBeforeOrder != null) {
            return Utils.convertBeforeOrderToDto(existingBeforeOrder);
        }

        BeforeOrder beforeOrder = new BeforeOrder();
        beforeOrder.setBeforeOrderType(beforeOrderDto.getBeforeOrderType() == null ? "cart" : beforeOrderDto.getBeforeOrderType());
        beforeOrder.setOrderDate(beforeOrderDto.getOrderDate() == null ? LocalDateTime.now() : beforeOrderDto.getOrderDate());
        beforeOrder.setUser(beforeOrderDto.getUser());
        beforeOrder.setProduct(productRepository.findById(beforeOrderDto.getProduct().getProductId()).orElse(null));

        beforeOrder = beforeOrderRepository.save(beforeOrder);

        return Utils.convertBeforeOrderToDto(beforeOrder);
    }

    @Override
    public String deleteBeforeOrder(int beforeOrderId) {
        String response = "Before order deleted successfully";
        try {
            beforeOrderRepository.deleteById(beforeOrderId);
        } catch (Exception e) {
            response = "Before order is already deleted";
        }
        return response;
    }

    @Override
    public List<UserOrderDto> getAllUserOrders(int userId) {
        List<UserOrder> userOrders = userOrderRepository.findByUserId(userId);
        return userOrders.stream().map(o -> Utils.convertUserOrderToDto(o)).collect(Collectors.toList());
    }

    @Override
    public UserOrderDto addNewUserOrders(int userId, int productId) {

        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {
            throw new IllegalArgumentException("Invalid product");
        }

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setProduct(product);
        userOrder.setOrderDate(LocalDateTime.now());
        userOrder.setStatus("In process");
        userOrder = userOrderRepository.save(userOrder);
        return Utils.convertUserOrderToDto(userOrder);
    }

    @Override
    public String downloadOrderSummery(int userId) throws IOException {

        List<UserOrder> userOrders = userOrderRepository.findByUserId(userId);
        if(userOrders.isEmpty())
            throw new IllegalArgumentException("Order not found");

        int randomNum = new Random().nextInt(10000);
        String fileName = "order-summery-" + randomNum + ".pdf";
        Document document = null;
        FileOutputStream fileOutputStream = null;
        try {
            document = new Document();
            fileOutputStream = new FileOutputStream(new File(fileName));
            PdfWriter.getInstance(document, fileOutputStream);

            document.open();

//            Chunk chunk = new Chunk(new Paragraph("ORDER SUMMERY"));
//            document.add(chunk);
            document.add(new Paragraph("ORDER SUMMERY"));
            document.add(Chunk.NEWLINE);

            PdfPTable pdfPTable = new PdfPTable(4);
            addTableHeader(pdfPTable);
            userOrders.stream().forEach(userOrder -> addRows(pdfPTable, userOrder));
            document.add(pdfPTable);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null)
                fileOutputStream.close();
            if(document != null)
                document.close();
        }
        return fileName;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Product", "Product Name", "Price", "Order Date")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable pdfPTable, UserOrder userOrder) {
        try {
            Blob dbImage = userOrder.getProduct().getImage();
            Image img = Image.getInstance(dbImage.getBytes(1,(int)dbImage.length()));
            //img.scaleAbsolute(72, 80);
            img.scalePercent(20);
            PdfPCell imageCell = new PdfPCell(img);
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            imageCell.setVerticalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(imageCell);
            pdfPTable.addCell(userOrder.getProduct().getProductName());
            pdfPTable.addCell(String.valueOf(userOrder.getProduct().getPrice()));
            pdfPTable.addCell(Utils.dateToStr(userOrder.getOrderDate()));
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
