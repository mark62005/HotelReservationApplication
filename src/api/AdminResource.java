package api;

public class AdminResource {

    private static AdminResource instance;

    private AdminResource(){}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

}
