package methodhandles;

/**
 * Created by daniel on 02/09/2016.
 */
public class Hello implements HelloI {
    public String sayHello(String message) {
        String x = "Hello " + message;
        System.out.println(x);
        return x;
    }
}
