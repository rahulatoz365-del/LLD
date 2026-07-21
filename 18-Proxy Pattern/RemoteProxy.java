interface DataService{
    String fetchData();
}
class ReadDataService implements DataService{
    public ReadDataService(){
        System.out.println("[Real Data Service] Initialized");
    }
    @Override
    public String fetchData(){
        return "[Real Data Service] Fetching Data";
    }
}
//Remote Proxy
class DataServiceProxy implements DataService{
    private ReadDataService realService;
    public DataServiceProxy(){
        this.realService = new ReadDataService();
    }
    @Override
    public String fetchData(){
        System.out.println("[Data Service Proxy] Connecting to Remote Server...");
        return realService.fetchData();
    }
}
public class RemoteProxy{
    public static void main(String[] args) {
        DataService dataService = new DataServiceProxy();
        System.out.println(dataService.fetchData());
    }
}