import serviceManagement.IService;
import userManagement.IUser;

import java.util.List;
import java.util.Scanner;


public class Main {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static Scanner scanner = new Scanner(System.in);

    private static List<IUser> users = List.of(
            new ServiceProvider("ServiceProvider", "sp_password"),
            new Client("Client", "client_password")
    );

        public static void main(String[] args) {
            
            IUser authenticatedUser = displayLoginScreen();
        
            if (authenticatedUser != null) {
                System.out.println("Welcome to the Service Marketplace!");
        
                if (authenticatedUser.isServiceProvider()) {
                    ServiceProvider serviceProvider = (ServiceProvider) authenticatedUser;
                    runServiceProvider(serviceProvider);
                } else if (authenticatedUser.isClient()) {
                    Client client = (Client) authenticatedUser;
                    ServiceProvider serviceProvider = createServiceProvider();
                    createInitialServices(serviceProvider);
                    runClient(client, serviceProvider);
                }
            } else {
                System.out.println("Exiting the program. Goodbye!");
            }
        
            scanner.close();
        }
        
    
    private static IUser displayLoginScreen() {
        System.out.println("Welcome to the Service Marketplace!");
        String username = UserInput.getInput("Enter your username: ");
        String password = UserInput.getInput("Enter your password: ");
    
        return getAuthenticatedUser(username, password);
    }
    

    private static IUser getAuthenticatedUser(String username, String password) {
        for (IUser user : users) {
            if (user.getUsername().equals(username) && authenticatePassword(user, password)) {
                return user; 
            }
        }
        return null;
    }
    

    private static boolean authenticatePassword(IUser user, String password) {
        if (user instanceof ServiceProvider) {
            return ((ServiceProvider) user).authenticate(password);
        } else if (user instanceof Client) {
            return ((Client) user).authenticate(password);
        }
        return false;
    }

    private static void runServiceProvider(ServiceProvider serviceProvider) {
        boolean continueRunning = true;
    
        createInitialServices(serviceProvider);
    
        while (continueRunning) {
            displayServiceProviderMenu();
            int choice = getUserInputInt("Enter your choice: ");
    
            switch (choice) {
                case 1:
                    displayServicesWithIndex(serviceProvider.getServicesOffered());
                    break;
                case 2:
                    createServiceInteractive(serviceProvider);
                    break;
                case 3:
                    displayServiceHistory(serviceProvider.getServiceHistory());
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    

    private static void runClient(Client client, ServiceProvider serviceProvider) {
        boolean continueRunning = true;
    
        while (continueRunning) {
            displayClientMenu();
            int choice = getUserInputInt("Enter your choice: ");
    
            switch (choice) {
                case 1:
                    displayServicesWithIndex(serviceProvider.getServicesOffered());
                    break;
                case 2:
                    purchaseServiceInteractive(client, serviceProvider);
                    break;
                case 3:
                    displayPurchaseHistory(client.getPurchaseHistory());
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    
    private static ServiceProvider createServiceProvider() {
        return new ServiceProvider("TemporaryServiceProvider", "temporary_password");
    }


    private static void displayServiceProviderMenu() {
        System.out.println("\n" + ANSI_CYAN + "Service Provider Menu:" + ANSI_RESET);
        System.out.println("1. Display Services Offered");
        System.out.println("2. Create a New Service");
        System.out.println("3. Display Service History");
        System.out.println("4. Exit");
    }

    private static void displayClientMenu() {
        System.out.println("\n" + ANSI_CYAN + "Client Menu:" + ANSI_RESET);
        System.out.println("1. Display Services Available for Purchase");
        System.out.println("2. Purchase a Service");
        System.out.println("3. Display Purchase History");
        System.out.println("4. Exit");
    }

    private static void createInitialServices(ServiceProvider serviceProvider) {
        Service service1 = new Service("Cleaning", 25.0);
        Service service2 = new Service("Gardening", 30.0);
        Service service3 = new Service("Plumbing", 40.0);

        serviceProvider.addService(service1);
        serviceProvider.addService(service2);
        serviceProvider.addService(service3);
    }

    private static void displayServicesWithIndex(List<IService> services) {
        for (int i = 0; i < services.size(); i++) {
            IService service = services.get(i);
            System.out.println(ANSI_GREEN + "  Index " + i + ": " +
                    "Service: " + service.getName() +
                    " - Price: $" + service.getPrice() + ANSI_RESET);
        }
    }

    private static void createServiceInteractive(ServiceProvider serviceProvider) {
        try {
            System.out.println(ANSI_CYAN + "Service Creation:" + ANSI_RESET);

            String serviceName = UserInput.getInput("Enter the name of the new service: ").trim();

            if (!serviceName.isEmpty()) {
                double price = getUserInputDouble("Enter the price of the new service: ");

                System.out.println("DEBUG: Creating service - Name: " + serviceName + ", Price: " + price);

                serviceProvider.createService(serviceName, price);
                System.out.println("Service created: " + serviceName);
            } else {
                System.out.println("Service name cannot be empty. Service creation canceled.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while creating the service. Service creation canceled.");
        }
    }

    private static void purchaseServiceInteractive(Client client, ServiceProvider serviceProvider) {
        try {
            System.out.println(ANSI_CYAN + "Service Purchase:" + ANSI_RESET);

            displayServicesWithIndex(serviceProvider.getServicesOffered());

            int selectedIndex = getUserInputInt("Enter the index of the service you want to purchase: ");
            IService selectedService = getServiceByIndex(serviceProvider.getServicesOffered(), selectedIndex);

            if (selectedService != null) {
                client.purchaseService(selectedService);
                System.out.println("Service purchased: " + selectedService.getName());
            } else {
                System.out.println("Invalid index. Service not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while purchasing the service. Service purchase canceled.");
        }
    }
    

    private static void displayPurchaseHistory(List<IService> purchaseHistory) {
        System.out.println(ANSI_CYAN + "Purchase History:" + ANSI_RESET);
        for (IService service : purchaseHistory) {
            System.out.println("Purchased: " + service.getName() + " - Price: $" + service.getPrice());
        }
    }

    private static void displayServiceHistory(List<IService> serviceHistory) {
        System.out.println(ANSI_CYAN + "Service History:" + ANSI_RESET);
        for (IService service : serviceHistory) {
            System.out.println("Created: " + service.getName() + " - Price: $" + service.getPrice());
        }
    }

    private static IService getServiceByIndex(List<IService> services, int index) {
        if (index >= 0 && index < services.size()) {
            return services.get(index);
        }
        return null;
    }

    private static double getUserInputDouble(String prompt) {
        double userInput = 0.0;
        boolean isValidInput = false;

        do {
            try {
                userInput = Double.parseDouble(UserInput.getInput(prompt));
                isValidInput = true;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!isValidInput);

        return userInput;
    }

    private static int getUserInputInt(String prompt) {
        int userInput = 0;
        boolean isValidInput = false;

        do {
            try {
                userInput = Integer.parseInt(UserInput.getInput(prompt));
                isValidInput = true;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (!isValidInput);

        return userInput;
    }
}

