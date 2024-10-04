import java.io.*;
import java.util.*;

import com.google.protobuf.Internal.BooleanList;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution{
  private List<VanSpot> totalVanParkingSpotAvalaible;
  private List<CarSpot> totalCarParkingSpotAvalaible;
  private List<MotorcycleSpot> totalMotorcycleParkingSpotAvalaible;
  private Map<Integer, Boolean> motorcycleReservedInfo;
  private Map<Integer, Boolean> carReservedInfo;
  private Map<Integer, Boolean> vanReservedInfo;

  public Solution(List<VanSpot> totalVanParkingSpotAvalaible, List<CarSpot> totalCarParkingSpotAvalaible, List<MotorcycleSpot> tota…
[8:19 AM, 10/3/2024] Naman Gujarathi: public class VerifyDiscount{
    DiscountAssignment discountAssignment;
    Customer customer;
    Discount discount;
    List<Discount> discountTotalList;
    List<Customer> customerTotalList;
    List<DiscountAssignment> discountAssignmentTotalList;
    List<Pair> loyalCustomerList;
    PriorityQueue<Pair> queue;
    HashMap<Integer, Integer> discountInfo;
    HashMap<Integer, List<Integer>> customerDiscountInfo;
    HashMap<Integer, Integer> customerInfo;
    Set<Integer> usedDiscount;
    
    
// constructor
    
     public VerifyDiscount(List<Discount> discountTotalList, List<Customer> customerTotalList, List<DiscountAssignment> discountAssignmentTotalList  ) {
        this.discountTotalList = discountTotalList;
        this.customerTotalList =customerTotalList;
        this.discountAssignmentTotalList = discountAssignmentTotalList;
         
        // customerDiscountInfo
        customerDiscountInfo = new HashMap<>();
        usedDiscount = new HashSet<>();
        for(int i=0; i<discountAssignmentTotalList.size(); i++){
         DiscountAssignment obj = discountAssignmentTotalList.get(i);
         usedDiscount.add(obj.getDiscountID());
              if(!(customerDiscountInfo.containsKey(obj.getCustomerID()))){
                customerDiscountInfo.put(obj.getCustomerID(), new ArrayList<>()); 
               }  
                 customerDiscountInfo.get(obj.getCustomerID()).add(obj.getDiscountID());
               
         }
         System.out.println("customerDiscountInfo" + customerDiscountInfo);
         
        // customerInfo
        customerInfo = new HashMap<>();
        for(int i=0; i < customerTotalList.size(); i++){
            Customer obj = customerTotalList.get(i);
            customerInfo.put(obj.getCustomerID(), obj.getCustomerYearlySpend());
        }

        System.out.println("customerInfo" + customerInfo);
         
        // discountInfo
         discountInfo = new HashMap<>();
        for(int i=0; i < discountTotalList.size(); i++){
            Discount obj = discountTotalList.get(i);
            discountInfo.put(obj.getDiscountID(), obj.getDiscountPrice());
        }
          System.out.println("discountInfo" + discountInfo);
        loyalCustomerList = new ArrayList<>();
        queue = new PriorityQueue<>((a,b) -> (b.getYearlySpentByCustomer()) - (a.getYearlySpentByCustomer()));
        
    }
    
// method1
    private boolean threeDiscountPerCustomer() {
        System.out.println("I am in method1");
        for(Map.Entry<Integer, List<Integer>> entry : customerDiscountInfo.entrySet()){
            List<Integer> discounts = entry.getValue();
            if(discounts.size()>3){
                throw new DiscountValidationException("More than 3 discount applied");
            }   
        }
        return true;
    }
    
// method 2 
    private boolean allDiscountUsed(){
        System.out.println("I am in method2");
        System.out.println("usedDiscount" + usedDiscount);
        for( Discount aDiscount: discountTotalList) {
            
            if(!usedDiscount.contains(aDiscount.getDiscountID())){
                throw new DiscountValidationException("All discounts were not used");
            }
        }
        System.out.println(" method2 end");
        return true; 
    }
    
// method 3
    private boolean lessTwentyPercent(){
        System.out.println("I am in method3");
        for(Map.Entry<Integer, List<Integer>> entry: customerDiscountInfo.entrySet()){
            int customerID= entry.getKey();
            List<Integer> discounts = entry.getValue();
            int yearlySpentByCustomer = customerInfo.get(customerID);
            int maxDiscount = (yearlySpentByCustomer*20)/ 100;
            int totalDiscountPerCustomer = 0;
            for(int discount : discounts){
                totalDiscountPerCustomer += discountInfo.get(discount);
            }
            if(totalDiscountPerCustomer > maxDiscount){
                throw new DiscountValidationException("more than twenty percent discount given");
            } 
            loyalCustomerList.add(new Pair(yearlySpentByCustomer, totalDiscountPerCustomer));
         
            maxDiscount = totalDiscountPerCustomer;
        } 
       
        return true;
    }
    
// method 4
    private boolean loyalCustomerMoreDiscount() {
        System.out.println("I am in method4");
         for ( Pair loyalCustomerPair : loyalCustomerList){
             queue.add(loyalCustomerPair);
         }
         Pair obj = queue.poll();
         int maxDiscount =  obj.getTotalDiscountPerCustomer();
        System.out.println("maxDiscount");
         while(!queue.isEmpty()){
             Pair obj1 = queue.poll();
             if(obj1.getTotalDiscountPerCustomer() >  maxDiscount) {
                 throw new DiscountValidationException("Discount were not distributed as per customer yearly expense");
             }
             maxDiscount = obj1.getTotalDiscountPerCustomer();
         } 
         
        return true;
    }
         
    
// main method entry point
    public static void main(String args[]){
        int discountData[][] = {{10, 50},{20, 100},{30, 150},{40, 200}};
        int customerData[][] = {{1, 1000}, {2, 1500}, {3, 1800}, {4, 2000}, {5, 2100}};
        int discountAssignmentData[][] = {{10, 1}, {10, 1}, {10, 1}, {20, 2}, {10, 3}, {10, 3}, {10, 3}, {30, 4}, {10, 4}, {40, 5}, {20, 5}};
        
        List<Discount> discountTotalList = new ArrayList<>();
        List<Customer> customerTotalList = new ArrayList<>();
        List<DiscountAssignment> discountAssignmentTotalList = new ArrayList<>();
        
        for(int i=0; i<discountData.length; i++){
            Discount discountObj = new Discount(discountData[i][0], discountData[i][1]);
            discountTotalList.add(discountObj);
        }
        
        for(int i=0; i<customerData.length; i++){
           Customer customerObj = new Customer(customerData[i][0], customerData[i][1]);
           customerTotalList.add(customerObj);    
        }
        
        for(int i=0; i<discountAssignmentData.length; i++){
            DiscountAssignment discountAssignementObj = new DiscountAssignment(discountAssignmentData[i][0], discountAssignmentData[i][1]);
            discountAssignmentTotalList.add(discountAssignementObj);
        }
        
        VerifyDiscount obj = new VerifyDiscount(discountTotalList, customerTotalList, discountAssignmentTotalList);
        
        if(obj.threeDiscountPerCustomer() &&  obj.allDiscountUsed() && obj.lessTwentyPercent() && obj.loyalCustomerMoreDiscount()) {
            System.out.println("Verified Discount for Customer Successfully");
        }
        else{
            System.out.println("Verified Discount Failed");
         }   
    }
}

class Pair {
    private int yearlySpentByCustomer;
    private int totalDiscountPerCustomer;
    public Pair(int yearlySpentByCustomer, int totalDiscountPerCustomer) {
        this.yearlySpentByCustomer = yearlySpentByCustomer;
        this.totalDiscountPerCustomer = totalDiscountPerCustomer;
    }
    public int getYearlySpentByCustomer(){
        return yearlySpentByCustomer;
    }
    
    public void setYearlySpentByCustomer(int yearlySpentByCustomer ){
        this.yearlySpentByCustomer = yearlySpentByCustomer;
    }
    
      public int getTotalDiscountPerCustomer(){
          return totalDiscountPerCustomer;
      }
          
    public void setTotalDiscountPerCustomer(int totalDiscountPerCustomer){
        this.totalDiscountPerCustomer = totalDiscountPerCustomer;
    }
    
  
}

class Discount {
    private int discountID;
    private int discountPrice;
    public Discount(int discountID, int discountPrice){
        this.discountID = discountID;
        this.discountPrice = discountPrice;
    }
    public int getDiscountID(){
        return discountID;
    }
    
    public void setDisocuntID(int discountID){
        this.discountID =  discountID;
    }
    
    public int getDiscountPrice(){
        return discountPrice;
    }
    
    public void setDiscountPrice(int discountPrice){
        this.discountPrice =  discountPrice;
    }
      
}

class Customer {
    private int customerID;
    private int customerYearlySpend;
    
    public Customer(int customerID, int customerYearlySpend){
        this.customerID = customerID;
        this.customerYearlySpend = customerYearlySpend;
    }  
    
    public int getCustomerID(){
        return customerID;
    }
    
    public void setCustomerID(int customerID){
        this.customerID =  customerID;
    }
      
    
    public int getCustomerYearlySpend(){
        return  customerYearlySpend;
    }
    
    public void setCustomerYearlySpend(int customerYearlySpend){
        this.customerYearlySpend =  customerYearlySpend;
    }
}

class DiscountAssignment {
    private int discountID;
    private int customerID;
    public DiscountAssignment(int discountID, int customerID ){
        this.discountID = discountID;
        this.customerID = customerID;
    }
    
    public int getDiscountID(){
        return discountID;
    }
    
    public void setdiscountID(int discountID){
        this.discountID = discountID;
    }
    
    public int getCustomerID(){
        return customerID;
    }
    
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
}
