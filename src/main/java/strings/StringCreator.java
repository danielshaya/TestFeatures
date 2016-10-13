package strings;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * Created by daniel on 28/09/2016.
 */
public class StringCreator {
    private static Field S_VALUE = null;
    private static Field SB_VALUE = null;
    private static Field SB_COUNT = null;

    static {
        try{
            S_VALUE = String.class.getDeclaredField("value");
            S_VALUE.setAccessible(true);
            SB_VALUE = Class.forName("java.lang.AbstractStringBuilder").getDeclaredField("value");
            SB_VALUE.setAccessible(true);
            SB_COUNT = Class.forName("java.lang.AbstractStringBuilder").getDeclaredField("count");
            SB_COUNT.setAccessible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        char[] chars = new char[100];
        ByteBuffer buffer = ByteBuffer.wrap("Hello World".getBytes());
        String s = "";
        StringBuilder sb = new StringBuilder();

        getString(buffer, chars, s);

        getStringBuilder(buffer, chars, sb);
    }

    private static void getString(ByteBuffer buffer, char[] chars, String s) throws Exception{
        for (int i = 0; i < buffer.limit(); i++) {
            chars[i] = (char)buffer.get(i);
        }
        S_VALUE.set(s, chars);
        System.out.println(s);
        System.out.println(s.length());
    }

    private static void getStringBuilder(ByteBuffer buffer, char[] chars, StringBuilder sb) throws Exception{
        for (int i = 0; i < buffer.limit(); i++) {
            chars[i] = (char)buffer.get(i);
        }
        SB_VALUE.set(sb, chars);
        SB_COUNT.set(sb, buffer.limit());
        System.out.println(sb);
        System.out.println(sb.length());
    }
}
