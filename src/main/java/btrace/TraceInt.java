package btrace;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by daniel on 27/09/2016.
 *
 * java -classpath . -javaagent:/Users/daniel/Code/btrace-bin-1.3.8.3/build/btrace-agent.jar
 *         =script=/Users/daniel/Code/java/TestFeatures/target/classes/btrace/TraceInteger.class,
 *         stdout=true btrace.TraceInt
 */
public class TraceInt {
    static AtomicInteger totalCount = new AtomicInteger(0);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future f = executorService.submit(()-> {
            TraceInt ti = new TraceInt();
            int counter = 6_000;
            while (--counter > 0) {
                Integer.valueOf((int) System.currentTimeMillis());
                ti.sleep(1);
                if (counter == 5_000) {
                    ti.endOfWarmUp(true);
                    System.out.println("WARMUP called");
                }
            }
            System.out.println("TOTAL COUNT " + totalCount);
        });

//        Future f1 = executorService.submit(()-> {
//            TraceInt ti = new TraceInt();
//            int counter = 10_000;
//            while (--counter > 0) {
//                Integer.valueOf((int) System.currentTimeMillis());
//                ti.sleep(1);
//                if (counter == 5_000) {
//                    ti.endOfWarmUp(true);
//                    System.out.println("WARMUP called");
//                }
//            }
//            System.out.println("TOTAL COUNT " + totalCount);
//        });

        try {
            f.get();
            end(true);
//            f1.get();

            executorService.shutdown();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        totalCount.incrementAndGet();
    }

    public void endOfWarmUp(boolean b){

    }

    public static void end(boolean b){

    }
}
