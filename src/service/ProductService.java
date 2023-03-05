package service;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductService extends Service{
    Product product = new Product();

    @Override
    public void add() {
        System.out.println("--------------------");
        System.out.println("(1. Add Product)");
        System.out.println("--------------------");

        product.inputProduct();

        try{
            ps = con.prepareStatement("INSERT INTO tbl_product(id,name,category,price) VALUES(?,?,?,?)");
            ps.setInt(1,product.getId());
            ps.setString(2,product.getName());
            ps.setString(3, product.getCategory());
            ps.setDouble(4,product.getPrice());

            ps.executeUpdate();
            System.out.println("Add Product Successful.");
            System.out.println("--------------------------");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("--------------------------");
        }
    }

    @Override
    public void show() {
        System.out.println("--------------------");
        System.out.println("(2. Show Product)");
        System.out.println("--------------------");

        try {
            ps = con.prepareCall("SELECT * FROM tbl_product");
            rs = ps.executeQuery();


            ResultSetMetaData metaData = rs.getMetaData();
            int col = metaData.getColumnCount();
            Table categoryTb = new Table(col, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
            for (int i = 1; i <= col; i++) {
                categoryTb.setColumnWidth(i - 1, 15, 30);
                categoryTb.addCell(metaData.getColumnName(i), new CellStyle(CellStyle.HorizontalAlign.center));
            }

            double countCategory = 0;
            while (rs.next()) {

                categoryTb.addCell(rs.getString(1));
                categoryTb.addCell(rs.getString(2));
                categoryTb.addCell(rs.getString(3));
                categoryTb.addCell(rs.getString(4));
                countCategory+=Double.parseDouble(rs.getString(4));
            }

            DecimalFormat df = new DecimalFormat("$####,####.##");
            categoryTb.addCell("Total Amount Product ", new CellStyle(CellStyle.HorizontalAlign.center), 3);
            categoryTb.addCell(df.format(countCategory) + "", new CellStyle(CellStyle.HorizontalAlign.center));
            System.out.println(categoryTb.render());

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("--------------------");
        System.out.println("(3. Edit Product)");
        System.out.println("--------------------");

        int index = validateInput("Enter Product ID to edit : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_product WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            if (rs.next()){
                product.inputProduct();

                ps = con.prepareStatement("UPDATE tbl_product SET id=?, name=?, category=?, price=? WHERE id=?");
                ps.setInt(1,index);
                ps.setString(2, product.getName());
                ps.setString(3, product.getCategory());
                ps.setDouble(4,product.getPrice());
                ps.setInt(5, index);

                ps.executeUpdate();

                System.out.println("Updated Product Successfully.");
                System.out.println("--------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("--------------------");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void search() {
        System.out.println("--------------------");
        System.out.println("(5. Search Product)");
        System.out.println("--------------------");

        int index = validateInput("Enter Product ID to search : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_product WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("ID  |\tName|\tCategory|\tPrice");
                System.out.println("------------------------------------------");
                System.out.println(rs.getInt(1) +"\t"+rs.getString(2)+"\t"+rs.getString(3)
                        +"  \t"+rs.getDouble(4));
                System.out.println("------------------------------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("------------------------------------------");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void searchByName(){
        Scanner input = new Scanner(System.in);

        List<Product> lsPro = getAllProduct();
        System.out.print("Enter Product Name To Search : ");
        String name = input.nextLine();

        Product arr = lsPro.stream().filter(p -> name.equalsIgnoreCase(p.getName())).findAny().orElse(null);

        if (arr != null){
            System.out.println("ID  |\tName|\tCategory|\tPrice");
            System.out.println("------------------------------------------");
            System.out.println(arr);
            System.out.println("------------------------------------------");
        }else {
            System.out.println("Record Not Found!!!");
            System.out.println("------------------------------------------");
        }

    }

    public void searchByCategory(){
        Scanner input = new Scanner(System.in);

        List<Product> lsPro = getAllProduct();
        System.out.print("Enter Product Category To Search : ");
        String cat = input.nextLine();

       List<Product>  arr =  lsPro.stream().filter(p -> cat.equalsIgnoreCase(p.getCategory())).collect(Collectors.toList());

        System.out.println("ID  |\tName|\tCategory|\tPrice");
        System.out.println("------------------------------------------");
       arr.stream().forEach(System.out::println);
        System.out.println("------------------------------------------");

    }
    @Override
    public void delete() {
        System.out.println("--------------------");
        System.out.println("(4. Delete Product)");
        System.out.println("--------------------");

        int index = validateInput("Enter Product ID to delete : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_product WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();
            if (rs.next()){
                ps = con.prepareStatement("DELETE FROM tbl_product WHERE id=?");
                ps.setInt(1, index);
                ps.executeUpdate();
                System.out.println("Delete Product Successfully.");
                System.out.println("--------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("--------------------");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public List<Product> getAllProduct(){
        List<Product> ls = new ArrayList<>();

        try {
            ps = con.prepareStatement("SELECT * FROM tbl_product");
            rs = ps.executeQuery();

            Product pro;
            while (rs.next()){
                pro = new Product();
                pro.setId(rs.getInt(1));
                pro.setName(rs.getString(2));
                pro.setCategory(rs.getString(3));
                pro.setPrice(rs.getDouble(4));

                ls.add(pro);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return ls;
    }


    //show by asc
    public void showByAsc(){
       List<Product> lsPro = getAllProduct().stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
/*        System.out.println("---------------------------------");
       lsPro.forEach(e -> System.out.println("id "+e.getId()+"\t"+e.getName()+"\t"+e.getCategory()+"\t"+e.getPrice()));
        System.out.println("---------------------------------");*/

        System.out.println("ID  |\tName|\tCategory|\tPrice");
        System.out.println("------------------------------------------");
        lsPro.stream().forEach(System.out::println);
        System.out.println("------------------------------------------");

    }

    //show by desc
    public void showByDesc(){
        List<Product> lsPro = getAllProduct().stream().sorted(Comparator.comparing(Product::getId).reversed()).collect(Collectors.toList());


        System.out.println("ID  |\tName|\tCategory|\tPrice");
        System.out.println("------------------------------------------");
        lsPro.stream().forEach(System.out::println);
        System.out.println("------------------------------------------");

    }


    /*
    *  ResultSetMetaData metaData = rs.getMetaData();
            int col = metaData.getColumnCount();

            for (int i = 1; i <= col; i++) {
                categoryTb.setColumnWidth(i - 1, 15, 30);
                categoryTb.addCell(metaData.getColumnName(i), new CellStyle(CellStyle.HorizontalAlign.center));
            }

            double countCategory = 0;
            while (rs.next()) {


            }

            DecimalFormat df = new DecimalFormat("$####,####.##");
            categoryTb.addCell("Total Amount Product ", new CellStyle(CellStyle.HorizontalAlign.center), 3);
            categoryTb.addCell(df.format(countCategory) + "", new CellStyle(CellStyle.HorizontalAlign.center));
            System.out.println(categoryTb.render());
    * */

}
