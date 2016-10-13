package methodhandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by daniel on 02/09/2016.
 */
public class HelloFactory {
    static MethodHandle sayHelloHandleBound = null;

    public static HelloI createHello() throws NoSuchMethodException, IllegalAccessException {
        if(sayHelloHandleBound==null) {
            MethodHandle sayHelloHandle =
                    MethodHandles.lookup().findVirtual(
                            HelloI.class,
                            "sayHello",
                            MethodType.methodType(String.class, String.class));

            //System.out.println(sayHelloHandle);

            //Invoke a method handle
            sayHelloHandleBound = sayHelloHandle.bindTo(new Hello());
        }

        HelloI h = (HelloI) Proxy.newProxyInstance(HelloI.class.getClassLoader(), new Class[] { HelloI.class },
                (proxy, method, margs) -> {
                    System.out.println("Proxying: " + method.getName() + " " + Arrays.toString(margs));
                    if("sayHello".equals(method.getName()))
                        return sayHelloHandleBound.invokeWithArguments(margs);
                    return null;
                }
        );
        return h;
    }
}
