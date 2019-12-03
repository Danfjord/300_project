
public class Service { // This is a subclass that will group together services. Each object of service will contain a service code, and a corresponding service name
    int serviceCode;
    String serviceName;
    float serviceFees;

    public Service(int serviceCode, String serviceName) { // Any time we create an object of service, we pass in 2 args to set the service code and service name
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
    }

    Service(int serviceCode, String serviceName, float serviceFees) { // Any time we create an object of service, we pass in 2 args to set the service code and service name
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceFees = serviceFees;
    }
}
