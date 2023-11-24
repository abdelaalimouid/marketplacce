import serviceManagement.*;
import userManagement.*;
import java.util.ArrayList;
import java.util.List;


public class Client implements IUser {
    private String username;
    private String password;  // Added password field
    private List<IService> servicesPurchased;
    private List<IService> purchaseHistory;  // Add purchaseHistory list

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        this.servicesPurchased = new ArrayList<>();
        this.purchaseHistory = new ArrayList<>(); // Initialize purchaseHistory
    }

    public List<IService> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void recordPurchaseInHistory(IService service) {
        purchaseHistory.add(service);
    }
    public void purchaseService(IService service) {
        servicesPurchased.add(service);
        purchaseHistory.add(service); 
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isServiceProvider() {
        return false;
    }

    @Override
    public boolean isClient() {
        return true;
    }

    public List<IService> getServicesPurchased() {
        return servicesPurchased;
    }

    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }
}