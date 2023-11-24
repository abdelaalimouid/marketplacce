import serviceManagement.*;

public class JobOffer implements IService {
    private String name;
    private double price;
    private String description;
    private Client client;

    public JobOffer(String name, double price, String description, Client client) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void performService() {
        System.out.println("Performing the job offer: " + name);
        System.out.println("Description: " + description);
    }

    public Client getClient() {
        return client;
    }
}
