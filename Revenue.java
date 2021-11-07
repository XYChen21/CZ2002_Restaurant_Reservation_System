package restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.*;

public class Revenue implements Serializable {
    /**
     * orders:
     *      ArrayList of orders
     * saleItems:
     *      HashMap<Food_object, quantities> of individual saleItems
     * salePkgs:
     *      HashMap<Food_object, quantities> of individual salePkgs
     */  
    private ArrayList<Order> orders;
    private HashMap<Food, Integer> saleItems;

    public Revenue(ArrayList<Order> orders, HashMap<Food, Integer> h){
        this.orders = orders;
        this.saleItems = h;
    }

    public void printRevenueReport(LocalDateTime start, LocalDateTime end) {
        Revenue temp = this.cut(start, end);
        double total = 0;
        double p = 0;

        System.out.println("                Restaurant Name                 ");
        System.out.println("        ********************************        ");
        System.out.println();
        System.out.println("From \t" + start.toString() + "To \t" + end.toString());
        System.out.println();
        System.out.println("------------------------------------------------");
        for(Map.Entry<Food, Integer> i: temp.saleItems.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getName() + "\t\t\t" + p);
            total += p;
        }
        System.out.println("------------------------------------------------");
        System.out.println("                                 Total: " + String.format("%-8.2f", total));
    }

    public void printRevenueReport() {
        double total = 0;
        Revenue temp = this;
        double p = 0;

        System.out.println("                Restaurant Name                 ");
        System.out.println("        ********************************        ");
        System.out.println();
        System.out.println("------------------------------------------------");
        for(Map.Entry<Food, Integer> i: temp.saleItems.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getName() + "\t\t\t" + p);
            total += p;
        }
        System.out.println("------------------------------------------------");
        System.out.println("                                 Total: " + String.format("%-8.2f", total));
    }

    public double getTotalRevenue(LocalDateTime start, LocalDateTime end) {
        return this.cut(start, end).getOrders().stream().mapToDouble(o -> o.getTotal()).sum();
    }

    public double getTotalRevenue() {
        return this.getOrders().stream().mapToDouble(o -> o.getTotal()).sum();
    }

    public void clear() {
        this.getOrders().clear();
        this.getSaleItems().clear();
    }

    public void addOrder(Order o) {
        this.orders.add(o);
        for (Map.Entry<Food, Integer> entry : o.ordersFood.entrySet()) {
            Food key = entry.getKey();
            int value = entry.getValue();
            this.getSaleItems().put(key, value + getSaleItems().getOrDefault(key, 0));
        }
    }

    public void removeOrder(int orderId) {
        this.getOrders().removeIf(o -> (o.getorderID() == orderId));
    }

    public ArrayList<Order> sortOrders(boolean byDate) {
        /**
         * byData: 
         *      True: sort by datetime
         *      False: sort by stuff
         */
        ArrayList<Order> temp = new ArrayList<>(this.getOrders());
        if(byDate){
            temp.sort((a, b) -> a.getTime().compareTo(b.getTime()));
        }
        else{
            temp.sort((a, b) -> a.getTime().compareTo(b.getTime()));
        }
        return temp;
    }

    private Revenue cut(LocalDateTime start, LocalDateTime end){
        /**
         * return:
         *      new Revenue within "start" and "end"
         */
        ArrayList<Order> new_orders = this.getOrders().stream()
        .filter(o -> (o.getTime().isAfter(start) && o.getTime().isBefore(end)))
        .collect(Collectors.toCollection(ArrayList::new));
        HashMap<Food, Integer> new_saleItems = new HashMap<>();
        for(Order o: new_orders){
            for (Map.Entry<Food, Integer> entry : o.ordersFood.entrySet()) {
                Food key = entry.getKey();
                int value = entry.getValue();
                new_saleItems.put(key, value + this.getSaleItems().getOrDefault(key, 0));
            }
        }
        return new Revenue(new_orders, new_saleItems);
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public HashMap<Food, Integer> getSaleItems() {
        return this.saleItems;
    }
}