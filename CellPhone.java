package Lab_5_Starter;

import Lab_5_Starter.InvalidManufacturerException;
import Lab_5_Starter.InvalidModelException;
import Lab_5_Starter.InvalidRetailPrice;

public class CellPhone
{
   private String model;
   private String manufacturer;
   private double retailPrice;

   public CellPhone(String m, String man, double price)
      throws InvalidModelException, InvalidManufacturerException, InvalidRetailPrice
   {
      setModel (m);
      setManufacturer(man);
      setRetailPrice(price);
   }
   public CellPhone() throws InvalidModelException, InvalidManufacturerException, InvalidRetailPrice
   {
      this("","", 0.0);
   }

   public void setModel(String m) throws InvalidModelException {
      if (m == null || m.length() ==0)
         throw new InvalidModelException();
      else
         model = m;
   }
   public void setManufacturer(String man) throws InvalidManufacturerException
   {
      if (man == null || man.length() == 0)
         throw new InvalidManufacturerException();
      else
         manufacturer = man;
   }
   public void setRetailPrice(double price) throws InvalidRetailPrice
   { if (price < 0 || price > 1500)
      throw new InvalidRetailPrice(price);
   else
      retailPrice = price;
   }

   public String getModel()
   {
      return model;
   }
   public String getManufacturer(){return manufacturer;}

   public double getRetailPrice() {return retailPrice;}

   @Override
   public String toString(){
      return String.format("Model: %-20sManufacturer: %-20sretail price: %10.2f%n", getModel(), getManufacturer(),
              getRetailPrice());
   }
}
