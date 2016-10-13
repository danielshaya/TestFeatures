package btrace;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by daniel on 27/09/2016.
 */
@BTrace
public class TraceInteger {
    private static Map<String, AtomicInteger> map = Collections.newHashMap();
    private static Map<String, String> methods = Collections.newHashMap();

    //@TLS
    static AtomicInteger count = Atomic.newAtomicInteger(0);

    static {
        Collections.put(methods, "btrace.TraceInt:sleep", "btrace.TraceInt");
    }

    @OnMethod(
            clazz = "/btrace.*/",
            method = "/.*/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/")
    )
    public static void m(@TargetMethodOrField String method,
                         @ProbeMethodName String probeMethod, @ProbeClassName String probeClass) {

        String methodName = probeClass + ":" + method;
        String probeMethodName = probeClass + ":" + probeMethod;

        if (Collections.get(methods, methodName) != null && Collections.get(methods, probeMethodName) != null) {
            //println(probeMethod + " in " + probeClass + " pm " + method);
            Atomic.incrementAndGet(count);
            AtomicInteger ai = Collections.get(map, methodName);

            if (ai == null) {
                ai = Atomic.newAtomicInteger(1);
                Collections.put(map, methodName, ai);
            } else {
                Atomic.incrementAndGet(ai);
                if (Atomic.get(ai) > 995 && Atomic.get(ai) < 1000) {
                    println("**** STACK BELOW ***** " + Atomic.get(ai));
                    jstack();

                    println("STATE OF MAP ");
                    printMap(map);
                }
            }
            return;
        }

        if (compare(methodName, "btrace.TraceInt:endOfWarmUp")) {
            println("****** EndOfWarmUp ******");
        }
        if (compare(methodName, "btrace.TraceInt:end")) {
            println("****** End ******");
            printMap(map);
            println("-------COUNT " + Atomic.get(count));
        }
    }

    @OnTimer(4000)
    public static void print() {
        if (Collections.size(map) != 0) {
            println("Printing map");
            printMap(map);
        }
    }
}
