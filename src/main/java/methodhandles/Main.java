package methodhandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by daniel on 02/09/2016.
 */
public class Main {
    public static void main(String[] args) throws Throwable {
//        //Create a method handle
//        MethodHandle sayHelloHandle =
//                MethodHandles.lookup().findVirtual(
//                        Hello.class,
//                        "sayHello",
//                        MethodType.methodType(String.class, String.class));
//
//        System.out.println(sayHelloHandle);
//
//        //Invoke a method handle
//        MethodHandle sayHelloHandleBound = sayHelloHandle.bindTo(new Hello());
//        sayHelloHandleBound.invokeWithArguments("RebelLabs");
//        sayHelloHandleBound.invokeWithArguments("Oleg");
//
//        //Create a Proxy
//        HelloI h = (HelloI) Proxy.newProxyInstance(HelloI.class.getClassLoader(), new Class[] { HelloI.class },
//                (proxy, method, margs) -> {
//                    System.out.println("Proxying: " + method.getName() + " " + Arrays.toString(margs));
//                    if("sayHello".equals(method.getName()))
//                        return sayHelloHandleBound.invokeWithArguments(margs);
//                    return null;
//                }
//        );
//
//        //Invoke the proxy
//        h.sayHello("proxy");
        HelloI h = HelloFactory.createHello();
        h.sayHello("proxy");

    }
}
