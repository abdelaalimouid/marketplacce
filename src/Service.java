

import serviceManagement.*;

public class Service implements IService {
    private String name;
  private double price;

  public Service(String name, double price)
 
{
    this.name = name;
    this.price = price;
  }

  @Override

  
public String getName()
 
{
    return name;
  }

  @Override

  
public
 
double
 
getPrice()
 
{
    return price;
  }



  @Override

  
public
 
void performService() {
    System.out.println("Performing the service: " + name);
  }
}