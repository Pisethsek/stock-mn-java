package model;

import service.Service;

import java.util.Scanner;

public class Category {
    private int id;
    private String name;
    private String description;

    public void inputCategory(){
        Scanner input = new Scanner(System.in);
        setId(Service.validateInput("Enter Category ID          : "));
        System.out.print("Enter Category Name        : ");
        setName(input.nextLine().trim().toLowerCase());
        System.out.print("Enter Category Description : ");
        setDescription(input.nextLine().trim().toLowerCase());
        System.out.println("--------------------------");
    }

    public Category(){}

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
