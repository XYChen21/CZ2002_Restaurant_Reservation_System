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
    private HashMap<Item, Integer> saleItems;
    private HashMap<Package, Integer> salePkgs;

    public Revenue(ArrayList<Order> orders, HashMap<Item, Integer> h, HashMap<Package, Integer> p){
        this.orders = orders;
        this.saleItems = h;
        this.salePkgs = p;
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
        for(Map.Entry<Item, Integer> i: temp.saleItems.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getName() + "\t\t\t" + p);
            total += p;
        }
        for(Map.Entry<Package, Integer> i: temp.salePkgs.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPackagePrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getPackageName() + "\t\t\t" + p);
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
        for(Map.Entry<Item, Integer> i: temp.saleItems.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getName() + "\t\t\t" + p);
            total += p;
        }
        for(Map.Entry<Package, Integer> i: temp.salePkgs.entrySet()){
            p = i.getValue() * i.getValue() * i.getKey().getPackagePrice();
            System.out.println(i.getValue().toString() + "\t" + i.getKey().getPackageName() + "\t\t\t" + p);
            total += p;
        }
        System.out.println("------------------------------------------------");
        System.out.println("                                 Total: " + String.format("%-8.2f", total));
    }

    public double getTotalRevenue(LocalDateTime start, LocalDateTime end) {
        return this.cut(start, end).getOrders().stream().mapToDouble(o -> o.Total).sum();
    }

    public double getTotalRevenue() {
        return this.getOrders().stream().mapToDouble(o -> o.Total).sum();
    }

    public void clear() {
        this.getOrders().clear();
        this.getSaleItems().clear();
    }

    public void addOrder(Order o) {
        this.orders.add(o);
        for (Map.Entry<Item, Integer> entry : o.ordersAC.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            this.getSaleItems().put(key, value + getSaleItems().getOrDefault(key, 0));
        }
        for (Map.Entry<Package, Integer> entry : o.ordersP.entrySet()) {
            Package key = entry.getKey();
            int value = entry.getValue();
            this.getSalePkgs().put(key, value + getSaleItems().getOrDefault(key, 0));
        }
    }

    public void removeOrder(int orderId) {
        this.getOrders().removeIf(o -> (o.orderID == orderId));
    }

    public ArrayList<Order> sortOrders(boolean byDate) {
        /**
         * byData: 
         *      True: sort by datetime
         *      False: sort by stuff
         */
        ArrayList<Order> temp = new ArrayList<>(this.getOrders());
        if(byDate){
            temp.sort((a, b) -> a.orderDateTime.compareTo(b.orderDateTime));
        }
        else{
            temp.sort((a, b) -> a.staffServer.compareTo(b.staffServer));
        }
        return temp;
    }

    private Revenue cut(LocalDateTime start, LocalDateTime end){
        /**
         * return:
         *      new Revenue within "start" and "end"
         */
        ArrayList<Order> new_orders = this.getOrders().stream()
        .filter(o -> (o.orderDateTime.isAfter(start) && o.orderDateTime.isBefore(end)))
        .collect(Collectors.toCollection(ArrayList::new));
        HashMap<Item, Integer> new_saleItems = new HashMap<>();
        HashMap<Package, Integer> new_salePkgs = new HashMap<>();
        for(Order o: new_orders){
            for (Map.Entry<Item, Integer> entry : o.ordersAC.entrySet()) {
                Item key = entry.getKey();
                int value = entry.getValue();
                new_saleItems.put(key, value + this.getSaleItems().getOrDefault(key, 0));
            }
        }
        for(Order o: new_orders){
            for (Map.Entry<Package, Integer> entry : o.ordersP.entrySet()) {
                Package key = entry.getKey();
                int value = entry.getValue();
                new_salePkgs.put(key, value + this.getSaleItems().getOrDefault(key, 0));
            }
        }
        return new Revenue(new_orders, new_saleItems, new_salePkgs);
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public HashMap<Item, Integer> getSaleItems() {
        return this.saleItems;
    }

    public HashMap<Package, Integer> getSalePkgs() {
        return salePkgs;
    }
}
