package service;

import model.Product;
import model.Stock;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class StockService extends Service{
    Stock stock = new Stock();
    ProductService productService = new ProductService();

    @Override
    public void add() {
        productService.show();

        int index = Service.validateInput("Choose Product In stock : ");
        List<Product> lsPro = productService.getAllProduct().stream().filter(product -> Objects.equals(product.getId(), index)).collect(Collectors.toList());

        if (!lsPro.isEmpty()){
            int id = lsPro.get(0).getId();
            String name = lsPro.get(0).getName();
            String category = lsPro.get(0).getCategory();
            double price = lsPro.get(0).getPrice();

            int qty = Service.validateInput("Enter Qty To the stock : ");

            double amount = qty*price;

            System.out.println("---------------------");
            System.out.println(id+"\t"+name+"\t"+category+"\t"+price+"\t"+qty+"\t"+amount);

            try {
                ps = con.prepareStatement("SELECT * FROM tbl_stock WHERE id=?");
                ps.setInt(1,id);
                rs = ps.executeQuery();

                boolean f=false;
                int oQty=0;
                while (rs.next()){
                    f = true;
                    oQty = rs.getInt(4);
                }

                if (f) {
                   ps = con.prepareStatement("UPDATE tbl_stock SET qty=?, amount=? WHERE id=?");
                   ps.setInt(1, qty+oQty);
                   ps.setDouble(2,(qty+oQty)*price);
                   ps.setInt(3, id);
                   ps.executeUpdate();

                }else {
                    ps = con.prepareStatement("INSERT INTO tbl_stock(id,name,category,qty,price,amount) VALUES(?,?,?,?,?,?)");
                    ps.setInt(1, id);
                    ps.setString(2,name);
                    ps.setString(3,category);
                    ps.setInt(4,qty);
                    ps.setDouble(5, price);

                    DecimalFormat df = new DecimalFormat("####,####.##");
                    ps.setDouble(6, Double.parseDouble(df.format(amount)));
                    ps.executeUpdate();
                }

                System.out.println("Successfully Insert To The Stock.");
                System.out.println("----------------------------------");
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        }else {
            System.out.println("Not Found Record!!!");
            System.out.println("--------------------");
        }

    }

    @Override
    public void show() {
        System.out.println("--------------------");
        System.out.println("(2. Show Stock)");
        System.out.println("--------------------");

        try {
            ps = con.prepareCall("SELECT * FROM tbl_stock ORDER BY id ASC");
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int col = metaData.getColumnCount();
            Table categoryTb = new Table(col, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
            for (int i = 1; i <= col; i++) {
                categoryTb.setColumnWidth(i - 1, 20, 50);
                categoryTb.addCell(metaData.getColumnName(i), new CellStyle(CellStyle.HorizontalAlign.center));
            }

            double countCategory = 0;
            while (rs.next()) {
                categoryTb.addCell(rs.getString(1));
                categoryTb.addCell(rs.getString(2));
                categoryTb.addCell(rs.getString(3));
                categoryTb.addCell(rs.getString(4));
                categoryTb.addCell(rs.getString(5));
                categoryTb.addCell(rs.getString(6));
                countCategory+=Double.parseDouble(rs.getString(6));
            }

            DecimalFormat df = new DecimalFormat("$####,####.##");
            categoryTb.addCell("Total Amount Stock ", new CellStyle(CellStyle.HorizontalAlign.center), 5);
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

        int index = validateInput("Enter Stock ID to edit : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_stock WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            if (rs.next()){
                stock.inputProduct();
                int qty = Service.validateInput("Enter Stock Qty   : ");

                ps = con.prepareStatement("UPDATE tbl_stock SET id=?, name=?, category=?,qty=?, price=?,amount=? WHERE id=?");
                ps.setInt(1,index);
                ps.setString(2, stock.getName());
                ps.setString(3, stock.getCategory());
                ps.setInt(4, qty);
                ps.setDouble(5,stock.getPrice());
                ps.setDouble(6,qty*stock.getPrice());
                ps.setInt(7, index);

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
        System.out.println("(5. Search Stock)");
        System.out.println("--------------------");

        int index = validateInput("Enter Stock ID to search : ");

        try {
            ps = con.prepareStatement("SELECT * FROM tbl_stock WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            if (rs.next()){
                System.out.println("ID  |\tName|\tCategory|\tQty|\tPrice|\tAmount");
                System.out.println("------------------------------------------");
                System.out.println(rs.getInt(1) +"\t"+rs.getString(2)+"\t"+rs.getString(3)
                        +"  \t"+rs.getInt(4) +"\t"+ rs.getDouble(5)+"\t"+rs.getDouble(6));
                System.out.println("------------------------------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("------------------------------------------");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println("--------------------");
        System.out.println("(5. Removing the product from the stock(stock out))");
        System.out.println("--------------------");

        int index = validateInput("Enter Stock ID to Removing The Product : ");

        try {
            ps = con.prepareStatement("SELECT qty, price FROM tbl_stock WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            int qty;
            double price;
            if (rs.next()){
                qty = rs.getInt(1);
                price = rs.getDouble(2);

                int newQty;
                do {
                    newQty = Service.validateInput("Enter New Qty Product : ");
                }while (!(newQty<=qty));

                ps = con.prepareStatement("UPDATE tbl_stock SET qty=?, amount=? WHERE id=?");
                ps.setInt(1,qty-newQty);
                ps.setDouble(2,(qty-newQty)*price);
                ps.setInt(3,index);
                ps.executeUpdate();
            }else {
                System.out.println("Record Not Found!!!");
            }


         /*
            if (rs.next()){
                ps = con.prepareStatement("DELETE FROM tbl_stock WHERE id=?");
                ps.setInt(1, index);
                ps.executeUpdate();
                System.out.println("Delete Stock Successfully.");
                System.out.println("--------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("--------------------");
            }*/
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}
