import userManagement.*;
import serviceManagement.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvider implements IUser {
    private String username;
    private String password;
    private List<IService> servicesOffered;
    private List<IService> serviceHistory;  

    public ServiceProvider(String username, String password) {
        this.username = username;
        this.password = password;
        this.servicesOffered = new ArrayList<>();
        this.serviceHistory = new ArrayList<>();  
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isServiceProvider() {
        return true;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    public void addService(IService service) {
        servicesOffered.add(service);
    }

    public List<IService> getServicesOffered() {
        return servicesOffered;
    }

    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public void createService(String serviceName, double price) {
        IService newService = new Service(serviceName, price);
        addService(newService);
        serviceHistory.add(newService);  
        System.out.println("New service created: " + serviceName);
    }

    public List<IService> getServiceHistory() {
        return serviceHistory;
    }
}
