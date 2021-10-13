
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Revenue {
    private ArrayList<Order> orders;
    private HashMap<Food, Integer> saleItems;

    public Revenue(ArrayList<Order> orders, HashMap<Food, Integer> h){
        this.orders = orders;
        this.saleItems = h;
    }
    public void generateRevenueReport(LocalDateTime start, LocalDateTime end) {
        System.out.println("");
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public HashMap<Food, Integer> getSaleItems() {
        return saleItems;
    }

    public double getTotalRevenue(LocalDateTime start, LocalDateTime end) {
        return Double.MIN_VALUE;
    }

    public void clearOrders() {

    }

    public void addOrder(Order o) {
        orders.add(o);
        for (Map.Entry<Food, Integer> entry : o.items.entrySet()) {
            Food key = entry.getKey();
            int value = entry.getValue();
            saleItems.put(key, value + saleItems.get(key));
        }
    }

    public void removeOrder(int orderId) {
        orders.removeIf(o -> (o.id == orderId));
    }

    public ArrayList<Order> sortOrders(boolean byDate) {
        /**
         * byData: 
         *      True: sort by datetime
         *      False: sort by stuff
         */
        ArrayList<Order> temp = new ArrayList<>(this.orders);
        if(byDate){
            temp.sort((a, b) -> a.time.compareTo(b.time));
        }
        else{
            temp.sort((a, b) -> a.staff.compareTo(b.staff));
        }
        return temp;
    }

    private Revenue cut(LocalDateTime start, LocalDateTime end){
        ArrayList<Order> orders = this.orders.stream().filter(o -> (o.time.isAfter(start) && o.time.isBefore(end))).collect(Collectors.toCollection(ArrayList::new));

        return this;
    }
}
