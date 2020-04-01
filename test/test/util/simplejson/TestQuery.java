package test.util.simplejson;

import static org.junit.Assert.assertEquals;
import static util.simplejson.Json.get;
import static util.simplejson.Json.list;
import static util.simplejson.Json.map;
import static util.simplejson.Json.parse;
import static util.simplejson.Json.select;

import org.junit.Test;

public class TestQuery {

    String json = ""
        + "{"
        + "    a0 : {"
        + "        b0 : {c0 : 0},"
        + "        b1 : {c0 : 1}"
        + "    },"
        + "    a1 : {"
        + "        b0 : {c0 : 2},"
        + "        b1 : [3, \"four\"]"
        + "    }"
        + "}";
    Object parsed = parse(json);

    @Test
    public void testGet() {
        assertEquals(1.0, get(parsed, "a0.b1.c0"));
        assertEquals(map("c0", 0.0), get(parsed, "a0.b0"));
        assertEquals(list(3.0, "four"), get(parsed, "a1.b1"));
        assertEquals(2.0, get(parsed, "a1.*.c0"));
        assertEquals(0.0, get(parsed, "a0.*.c0"));
        assertEquals(0.0, get(parsed, "*.*.c0"));
        assertEquals(0.0, get(parsed, "**.c0"));
        assertEquals(3.0, get(parsed, "**.0"));
    }

    @Test
    public void testSelect() {
        assertEquals(list(1.0), select(parsed, "a0.b1.c0"));
        assertEquals(list(map("c0", 0.0)), select(parsed, "a0.b0"));
        assertEquals(list(list(3.0, "four")), select(parsed, "a1.b1"));
        assertEquals(list(2.0), select(parsed, "a1.*.c0"));
        assertEquals(list(0.0, 1.0), select(parsed, "a0.*.c0"));
        assertEquals(list(0.0, 1.0, 2.0), select(parsed, "*.*.c0"));
        assertEquals(list(0.0, 1.0, 2.0), select(parsed, "**.c0"));
        assertEquals(list(3.0), select(parsed, "**.0"));
    }

}
