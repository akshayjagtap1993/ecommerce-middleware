package com.ecommerce.middleware.utils;

import com.ecommerce.middleware.pojo.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class CustomBlobSerializer extends JsonSerializer<Blob> {

    @Override
    public void serialize(Blob image, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        byte byteArray[] = new byte[0];
        try {
            byteArray = image.getBytes(1,(int)image.length());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(byteArray);
        jsonGenerator.writeString(encodeImage);
    }
}
