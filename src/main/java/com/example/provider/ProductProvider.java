package com.example.provider;

import com.example.DAO.ProductDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;

import javafx.collections.ObservableList;

public class ProductProvider {
    public static Product currentProduct;
    public static ObservableList<ProductDTO> products;

    public static void init() {
        // todo add pagination to DAO
        products = ProductDAO.getStock();
    }

    public static void setProducts(ObservableList<ProductDTO> newProducts) {
        ProductProvider.products = newProducts;

    }

    public static Product getCurrentProduct() {
        return currentProduct;
    }

    public static void setCurrentProduct(Product productDTO) {
        ProductProvider.currentProduct = productDTO;
    }

    public static void setCurrentProduct(ProductDTO productDTO) {
        ProductProvider.currentProduct = productDTO.getProduct();
    }

}
