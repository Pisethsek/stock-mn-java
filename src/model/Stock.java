package model;

public class Stock extends Product{
    public int qty;
    public double getAmount(){
        return this.getPrice()*this.getQty();
    }

    public Stock(){}

    public Stock(int id, String name, double price, String category, int qty) {
        super(id, name, price, category);
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "qty=" + qty +
                '}';
    }

}
