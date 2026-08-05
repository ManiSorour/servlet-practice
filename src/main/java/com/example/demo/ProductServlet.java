package com.example.demo;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Product;
import model.role.Admin;
import model.role.User;
import repository.ProductJsonRepository;
import repository.ProductRepository;
import service.WareHouseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "productServlet", value = "/api/products")
public class ProductServlet extends HttpServlet {
    private WareHouseService wareHouseService;
    private final Gson gson = new Gson();


    @Override
    public void init() {
        ProductRepository repository = new ProductJsonRepository("C:/Users/aydak/Downloads/first-proj-warehouse-managment-master/first-proj-warehouse-managment-master/products.json"
        );
        wareHouseService = new WareHouseService(repository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Product> products = wareHouseService.getAllProducts();
        String json = gson.toJson(products);


        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        BufferedReader reader = request.getReader();
        Product order  = gson.fromJson(reader , Product.class);

        User performedBy = new Admin(1,"mani","8871");
        try {
            wareHouseService.addProduct(
                    order.getName(),
                    order.getCode(),
                    order.getCategory(),
                    order.getPurchasePrice(),
                    order.getSellPrice(),
                    order.getQuantity(),
                    20,
                    performedBy
            );
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(gson.toJson(e.getMessage()));
        }







    }
}



















