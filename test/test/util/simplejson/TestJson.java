package test.util.simplejson;

import static org.junit.Assert.*;
import static util.simplejson.Json.*;
import static util.simplejson.Json.format;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import util.simplejson.Json;

public class TestJson {

    @Test
    public void test() {
        String json = ""
            + "{\n"
            + "    \"1\": {\n"
            + "        \"state\": {\n"
            + "            \"on\": false,\n"
            + "            \"bri\": 0,\n"
            + "            \"hue\": 0,\n"
            + "            \"sat\": 0,\n"
            + "            \"effect\": \"none\",\n"
            + "            \"xy\": [\n"
            + "                0,\n"
            + "                0\n"
            + "            ],\n"
            + "            \"ct\": 0,\n"
            + "            \"alert\": \"none\",\n"
            + "            \"colormode\": \"hs\",\n"
            + "            \"reachable\": false\n"
            + "        },\n"
            + "        \"type\": \"Extended color light\",\n"
            + "        \"name\": \"Hue Lamp 1\",\n"
            + "        \"modelid\": \"LCT001\",\n"
            + "        \"manufacturername\": \"Philips\",\n"
            + "        \"uniqueid\": \"00:17:88:01:00:f4:5a:aa-0b\",\n"
            + "        \"swversion\": \"5.23.1.13452\"\n"
            + "    },\n"
            + "    \"2\": {\n"
            + "        \"state\": {\n"
            + "            \"on\": false,\n"
            + "            \"bri\": 254,\n"
            + "            \"hue\": 8000,\n"
            + "            \"sat\": 200,\n"
            + "            \"effect\": \"none\",\n"
            + "            \"xy\": [\n"
            + "                0.5469,\n"
            + "                0.3819\n"
            + "            ],\n"
            + "            \"ct\": 500,\n"
            + "            \"alert\": \"none\",\n"
            + "            \"colormode\": \"hs\",\n"
            + "            \"reachable\": true\n"
            + "        },\n"
            + "        \"type\": \"Extended color light\",\n"
            + "        \"name\": \"Hue Lamp 2\",\n"
            + "        \"modelid\": \"LCT001\",\n"
            + "        \"manufacturername\": \"Philips\",\n"
            + "        \"uniqueid\": \"00:17:88:01:00:f4:5c:55-0b\",\n"
            + "        \"swversion\": \"5.23.1.13452\"\n"
            + "    }\n"
            + "}\n";
        Object parsed = parse(json);
        System.out.println(parsed);
        assertEquals(254.0, get(parsed, "2.state.bri"));
        assertEquals(0.5469, get(parsed, "2.state.xy.0"));
        assertEquals(List.of(0.0, 0.3819), select(parsed, "*.state.xy.1"));
        assertEquals(List.of(0.0, 254.0), select(parsed, "*.state.bri"));
        assertEquals(254.0, asInt(get(parsed, "2.*.bri")), 0.000001);
        assertEquals(List.of(0.0, 0.5469), select(parsed, "**.xy.0"));
        assertEquals(List.of(0.0, 0.3819), select(parsed, "**.xy.1"));
        assertEquals(List.of(List.of(0.0, 0.0), List.of(0.5469, 0.3819)), select(parsed, "**.xy"));
        assertEquals(0.5469, get(get(parsed, "2.state"), "xy.0"));
        assertEquals(0.5469, get(parsed, "2.**.0"));
        assertEquals(0.5469, get(parsed, "2.*.*.0"));
        System.out.println(format(parsed));
    }

    @Test
    public void testString() {
        assertEquals("a b\tc", parse("\"a b\tc\""));
        assertEquals("He said \"Yes\".", parse("\"He said \\\"Yes\\\".\""));
    }

    @Test
    public void testParsePath() {
        assertArrayEquals(new String[]{"."}, parsePath("/."));
    }

    @Test
    public void testPathEscape() {
        String json = "{\"*\": \"star\", \"@\": \"at\", \"/\": \"slash\", \".\": \"dot\"}";
        Object parsed = parse(json);
        assertEquals("at", get(parsed, "@"));
        assertEquals("slash", get(parsed, "/"));
        assertEquals("star", get(parsed, "/*"));
        assertEquals("dot", get(parsed, "/."));
    }

    @Test
    public void testArray() {
        String json = "[{\"myKey1\": \"school type\", \"myKey2\": \"primary\"},\n"
            + " {\"myKey1\": \"study coverage\", \"myKey2\": \"secondary\"}]";
        Object parsed = parse(json);
        for (Object element : asList(parsed))
            for (Entry<String, Object> m : asMap(element).entrySet())
                System.out.println(m.getKey() + ":" + m.getValue());
        for (Object e : select(parsed, "*.myKey1"))
            System.out.println("myKey1=" + e);
    }

    @Test
    public void testFile() throws IOException, ParseException {
        Object parsed = parse(new File("data/repos.json"));
        System.out.println(select(parsed, "*.owner.id"));
        System.out.println(NumberFormat.getInstance().parse("-1234567890123456789123456789").getClass());
        System.out.println(NumberFormat.getInstance().parse("123.33").getClass());
    }

    @Test
    public void testGenerated() throws IOException, ParseException {
        Object parsed = parse(new File("data/generated.json"));
        assertEquals(18, select(parsed, "*.friends.*.name").size());
        assertEquals(6, select(parsed, "*.name").size());
        assertEquals(-70.225222, get(parsed, "0.latitude"));
        assertEquals(57.28908, get(parsed, "1.latitude"));
    }

    @Test
    public void testSelectAncestor() {
        Object parsed = parse("{a:{b:{c:1},B:{C:2}}}");
        System.out.println(get(parsed, "a.*"));
        System.out.println(select(parsed, "a.*"));
        System.out.println(get(parsed, "*"));
    }

    @Test
    public void testBuild() {
        Object built = map(
            "i0", 1,
            "l0", list(1, 2, list(3.0, 3.1, 3.2), 4),
            "s0", "string",
            "b0", true);
        System.out.println(format(built));
    }

    @Test
    public void testArray2() {
        String j = "[\r\n"
            + "    {\r\n"
            + "        \"place_id\": \"19414019\",\r\n"
            + "        \"licence\": \"https:\\/\\/locationiq.com\\/attribution\",\r\n"
            + "        \"osm_type\": \"node\",\r\n"
            + "        \"osm_id\": \"1830231415\",\r\n"
            + "        \"boundingbox\": [\r\n"
            + "            \"34.0780031\",\r\n"
            + "            \"34.1180031\",\r\n"
            + "            \"-118.349523\",\r\n"
            + "            \"-118.309523\"\r\n"
            + "        ],\r\n"
            + "        \"lat\": \"34.0980031\",\r\n"
            + "        \"lon\": \"-118.329523\",\r\n"
            + "        \"display_name\": \"Hollywood, Los Angeles, Los Angeles County, Kalifornien, 90028, Vereinigte Staaten von Amerika\",\r\n"
            + "        \"class\": \"place\",\r\n"
            + "        \"type\": \"suburb\",\r\n"
            + "        \"importance\": 0.796974175739209,\r\n"
            + "        \"icon\": \"https:\\/\\/locationiq.org\\/static\\/images\\/mapicons\\/poi_place_village.p.20.png\"\r\n"
            + "    },\r\n"
            + "]";
        Object parsed = parse(j);
        System.out.println(parsed);
        double lat = Double.parseDouble(Json.get(parsed, "0.lat").toString());
        assertEquals(34.0980031, lat, 0.000005);
    }
}
