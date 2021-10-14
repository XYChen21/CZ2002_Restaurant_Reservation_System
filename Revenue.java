
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Revenue {
    /**
     * orders:
     *      ArrayList of orders
     * saleItems:
     *      HashMap<Food_object, quantities> of individual saleItems
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
        for(Order o: temp.getOrders()){
            total += o.subTotal;
            System.out.println(o.subTotal);
            /**
             * sth to print
             */
        }
    }

    public void printRevenueReport() {
        double total = 0;
        for(Order o: this.getOrders()){
            total += o.subTotal;
            System.out.println(o.subTotal);
            /**
             * sth to print
             */
        }
    }

    public double getTotalRevenue(LocalDateTime start, LocalDateTime end) {
        return this.cut(start, end).getOrders().stream().mapToDouble(o -> o.subTotal).sum();
    }

    public double getTotalRevenue() {
        return this.getOrders().stream().mapToDouble(o -> o.subTotal).sum();
    }

    public void clear() {
        this.getOrders().clear();
        this.getSaleItems().clear();
    }

    public void addOrder(Order o) {
        this.getOrders().add(o);
        for (Map.Entry<Food, Integer> entry : o.items.entrySet()) {
            Food key = entry.getKey();
            int value = entry.getValue();
            this.getSaleItems().put(key, value + getSaleItems().getOrDefault(key, 0));
        }
    }

    public void removeOrder(int orderId) {
        this.getOrders().removeIf(o -> (o.id == orderId));
    }

    public ArrayList<Order> sortOrders(boolean byDate) {
        /**
         * byData: 
         *      True: sort by datetime
         *      False: sort by stuff
         */
        ArrayList<Order> temp = new ArrayList<>(this.getOrders());
        if(byDate){
            temp.sort((a, b) -> a.time.compareTo(b.time));
        }
        else{
            temp.sort((a, b) -> a.staff.compareTo(b.staff));
        }
        return temp;
    }

    private Revenue cut(LocalDateTime start, LocalDateTime end){
        /**
         * return:
         *      new Revenue within "start" and "end"
         */
        ArrayList<Order> new_orders = this.getOrders().stream().filter(o -> (o.time.isAfter(start) && o.time.isBefore(end))).collect(Collectors.toCollection(ArrayList::new));
        HashMap<Food, Integer> new_saleItems = new HashMap<>();
        for(Order o: new_orders){
            for (Map.Entry<Food, Integer> entry : o.items.entrySet()) {
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
