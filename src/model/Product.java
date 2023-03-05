package model;

import service.CategoryService;
import service.Service;

import java.util.List;
import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;

    public void inputProduct(){
        Scanner input = new Scanner(System.in);
        CategoryService categoryService= new CategoryService();
        List<String> lsCat = categoryService.allCategoryName();

        setId(Service.validateInput("Enter Product ID      : "));

        System.out.print("Enter Product Name    : ");
        setName(input.nextLine());

        System.out.print("Enter Product Price   : ");
        setPrice(input.nextDouble());

        System.out.println("Select Product Category: ");
        for (int i=1; i<=lsCat.size(); i++){
            System.out.println(i+". "+lsCat.get(i-1));
        }
        System.out.println("------------------");
        int ch = Service.validateInput("Choose Category  : ");
        setCategory(String.valueOf(lsCat.get(ch-1)));
        System.out.println("-------------------");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Product(){}

    public Product(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
