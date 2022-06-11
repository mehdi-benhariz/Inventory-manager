package com.example.provider;

import com.example.DAO.ProductDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;

import javafx.collections.FXCollections;
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

    public static Product getProduct(String ref) {
        for (ProductDTO p : products)
            if (p.getRef().equals(ref))
                return p.getProduct();

        return null;
    }

    public static ObservableList<String> getRefs() {
        ObservableList<String> refs = FXCollections.observableArrayList();
        for (ProductDTO p : products)
            refs.add(p.getRef());

        return refs;
    }

}
