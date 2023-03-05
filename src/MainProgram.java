import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.*;

public class MainProgram {
    public static void header(String txt){
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        table.setColumnWidth(0,50,50);
        table.addCell(txt, cellStyle);
        System.out.println(table.render());
    }

    public static void main(String[] args) {
        Service service;
        UserService userService = new UserService();

        header("Login Before using");
        boolean isLogin = userService.userLogin();

        if (isLogin){
            int ch;
            do{
                CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
                Table table = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
                table.setColumnWidth(0,50,50);
                table.addCell("Stock Management", cellStyle);
                table.addCell("1. Category Management");
                table.addCell("2. Product Management");
                table.addCell("3. Stock Management");
                table.addCell("0. Exit");
                System.out.println(table.render());
                ch = Service.validateInput("Choose 1 to 3 : ");

                switch (ch){
                    case 1:
                        service = new CategoryService();
                        header("Category Management");

                        int catCh;
                        do {
                            System.out.println("1. Add Category");
                            System.out.println("2. Edit Category");
                            System.out.println("3. Search Category");
                            System.out.println("4. Delete Category");
                            System.out.println("5. Show Category");
                            System.out.println("0. Exit");
                            System.out.println("--------------------");
                            catCh = Service.validateInput("Choose 1 to 5 : ");

                            switch (catCh){
                                case 1:
                                    service.add();
                                    break;
                                case 2:
                                    service.edit();
                                    break;
                                case 3:
                                    service.search();
                                    break;
                                case 4:
                                    service.delete();
                                    break;
                                case 5:
                                    service.show();
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid Chosen!!!");
                            }
                        }while (catCh!=0);
                        break;
                    case 2:
                        service = new ProductService();
                        header("Product Management");

                        int proCh;
                        do {
                            System.out.println("1. Add Product");
                            System.out.println("2. Edit Product");
                            System.out.println("3. Delete Product");
                            System.out.println("4. Search Product");
                            System.out.println("5. Show Product");
                            System.out.println("0. Exit");
                            System.out.println("--------------------");
                            proCh = Service.validateInput("Choose 1 to 5 : ");

                            switch (proCh){
                                case 1:
                                    service.add();
                                    break;
                                case 2:
                                    service.edit();
                                    break;
                                case 3:
                                    service.delete();
                                    break;
                                case 4:
                                    int searchOption;
                                    do {
                                        System.out.println("1. Search By Product ID");
                                        System.out.println("2. Search By Product Name");
                                        System.out.println("3. Search By Category");
                                        System.out.println("0. Exit ");
                                        System.out.println("--------------------");

                                        searchOption = Service.validateInput("Choose 1 to 3 : ");

                                        switch (searchOption){
                                            case 1:
                                                //s id
                                                service.search();
                                                break;
                                            case 2:
                                                // s name
                                                service.searchByName();
                                                break;
                                            case 3:
                                                // s category
                                                service.searchByCategory();
                                                break;
                                            case 0:
                                                break;
                                            default:
                                                System.out.println("Invalid Chosen!!!");
                                                System.out.println("--------------------");
                                        }
                                    }while (searchOption!=0);
                                    break;
                                case 5:
                                    int showOption;
                                    do {
                                        System.out.println("1. Show By Normal Order");
                                        System.out.println("2. Show By ascending");
                                        System.out.println("3. Show By descending");
                                        System.out.println("0. Exit ");
                                        System.out.println("--------------------");

                                        showOption = Service.validateInput("Choose 1 to 3 : ");

                                        switch (showOption){
                                            case 1:
                                                //s normal
                                                service.show();
                                                break;
                                            case 2:
                                                // s asc
                                                service.showByAsc();
                                                break;
                                            case 3:
                                                // s desc
                                                service.showByDesc();
                                                break;
                                            case 0:
                                                break;
                                            default:
                                                System.out.println("Invalid Chosen!!!");
                                                System.out.println("--------------------");
                                        }
                                    }while (showOption!=0);
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid Chosen!!!");
                            }
                        }while (proCh!=0);
                        break;
                    case 3:
                        header("Stock Management");
                        service = new StockService();
                        int stCh;
                        do{
                            System.out.println("1. Adding Product To The Stock(Stock in)");
                            System.out.println("2. Edit Product in Stock");
                            System.out.println("3. Search the Product inside the Stock");
                            System.out.println("4. Show all product in the stock");
                            System.out.println("5. Removing the product from the stock(stock out)");
                            System.out.println("0. Exit");
                            System.out.println("--------------------");
                            stCh = Service.validateInput("Choose 1 to 5 : ");

                            switch (stCh){
                                case 1:
                                    service.add();
                                    break;
                                case 2:
                                    service.edit();
                                    break;
                                case 3:
                                    service.search();
                                    break;
                                case 4:
                                    service.show();
                                    break;
                                case 5:
                                    service.delete();
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid Chosen!!!");
                            }
                        }while (stCh!=0);
                        break;
                    case 0:
                        service = new ProductService();
                        service.closed();
                        break;
                    default:
                        System.out.println("Invalid Chosen!!!");
                        System.out.println("--------------------");
                }
            }while (ch!=0);
        }

    }

}
