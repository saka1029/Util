package test.util.regex;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

public class TestRegex {

    static void commonMethod(List<Object> alldata) throws IllegalArgumentException, IllegalAccessException {
        for (Object obj : alldata) {
            Class<?> clazz = obj.getClass();
            for (Field f : clazz.getFields()) {
                f.setAccessible(true);
                System.out.print(" " + f.getName() + "=" + f.get(obj));
            }
            System.out.println();
        }
    }

    public String A(String text, String prefix) {
        return text.replaceAll("(?m)^.*$", prefix + "$0");
    }

    static <T> void forEach(Iterator<T> iterator, Consumer<T> body) {
        while (iterator.hasNext())
            body.accept(iterator.next());
    }

    @Test
    public void testReplaceAll() throws IllegalArgumentException, IllegalAccessException, IOException {
        Iterator<String> 繰り返し = Arrays.asList("いち", "に", "さん").iterator();
        for (String 文字列 : (Iterable<String>) () -> 繰り返し)
            System.out.println(文字列);
        try (Closeable 最後に実行 = () -> System.out.print("終了")) {
            System.out.println("開始");
        }
    }

}
