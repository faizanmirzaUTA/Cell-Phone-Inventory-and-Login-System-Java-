package Lab_5_Starter;

public class InvalidRetailPrice extends Exception{
    public InvalidRetailPrice(){
        super("Invalid RetailPrice");
    }
    public InvalidRetailPrice(double price){
        super("Invalid RetailPrice: " + price);
    }
}
