package service;

import model.Category;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryService extends Service{
    Category category = new Category();

    @Override
    public void add() {
        System.out.println("--------------------");
        System.out.println("(1. Add Category)");
        System.out.println("--------------------");

        category.inputCategory();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        try {
            ps = con.prepareStatement("INSERT INTO tbl_category(id,name,description,created) VALUES(?,?,?,?)");
            ps.setInt(1,category.getId());
            ps.setString(2,category.getName());
            ps.setString(3,category.getDescription());
            ps.setString(4, dtf.format(now));
            ps.executeUpdate();

            System.out.println("Add Category Successfully.");
            System.out.println("--------------------------");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("--------------------------");
        }
    }

    @Override
    public void show() {
        System.out.println("--------------------");
        System.out.println("(5. Show Category)");
        System.out.println("--------------------");

        try {
            ps = con.prepareCall("SELECT * FROM tbl_category ORDER BY id ASC");
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int col = metaData.getColumnCount();
            Table categoryTb = new Table(col, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
            for (int i = 1; i <= col; i++) {
                categoryTb.setColumnWidth(i - 1, 15, 30);
                categoryTb.addCell(metaData.getColumnName(i), new CellStyle(CellStyle.HorizontalAlign.center));
            }

            int countCategory = 0;
            while (rs.next()) {
                countCategory++;
                categoryTb.addCell(rs.getString(1));
                categoryTb.addCell(rs.getString(2));
                categoryTb.addCell(rs.getString(3));
                categoryTb.addCell(rs.getString(4));
            }

            categoryTb.addCell("Total Category ", new CellStyle(CellStyle.HorizontalAlign.center), 3);
            categoryTb.addCell(countCategory + "", new CellStyle(CellStyle.HorizontalAlign.center));
            System.out.println(categoryTb.render());

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("--------------------");
        System.out.println("(2. Edit Category)");
        System.out.println("--------------------");

        Scanner input = new Scanner(System.in);
        int index = validateInput("Enter Category ID to edit : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_category WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            if (rs.next()){
                System.out.print("Enter New Category Name        : ");
                category.setName(input.nextLine().toLowerCase().trim());
                System.out.print("Enter New Category Description : ");
                category.setDescription(input.nextLine().toLowerCase().trim());
                System.out.println("-------------------------");

                ps = con.prepareStatement("UPDATE tbl_category SET name=?, description=?, created=? WHERE id=?");
                ps.setString(1, category.getName());
                ps.setString(2, category.getDescription());
                ps.setInt(4, index);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                ps.setString(3, dtf.format(now));
                ps.executeUpdate();
                System.out.println("Updated Category Successfully.");
                System.out.println("-------------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("-------------------------");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println("--------------------");
        System.out.println("(4. Delete Category)");
        System.out.println("--------------------");

        int index = validateInput("Enter Category ID to delete : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_category WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            if (rs.next()){
                ps = con.prepareStatement("DELETE FROM tbl_category WHERE id=?");
                ps.setInt(1, index);
                ps.executeUpdate();

                System.out.println("Delete Category Successfully.");
                System.out.println("-------------------------");
            }else {
                System.out.println("Record Not Found!!!");
                System.out.println("-------------------------");
            }

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void search() {
        System.out.println("--------------------");
        System.out.println("(3. Search Category)");
        System.out.println("--------------------");

        int index = validateInput("Enter Category ID to search : ");
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_category WHERE id=?");
            ps.setInt(1,index);
            rs = ps.executeQuery();

            System.out.println("------------------------------------------");
            if (rs.next()){
                System.out.println("ID  |\tName|\tDescription|\tCreated");
                System.out.println("------------------------------------------");
                System.out.println(rs.getInt(1) +"\t"+rs.getString(2)+"\t"+rs.getString(3)+"    \t"+rs.getString(4));
            }else {
                System.out.println("Record Not Found!!!");
            }
            System.out.println("------------------------------------------");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    //get all categoryName
    public List<String> allCategoryName(){
        List<String> lsCat = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM tbl_category");
            rs = ps.executeQuery();

            while (rs.next()){
                String str = rs.getString(2);
                lsCat.add(str);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return lsCat;
    }

}
