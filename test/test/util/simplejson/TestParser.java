package test.util.simplejson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.simplejson.Json.format;
import static util.simplejson.Json.get;
import static util.simplejson.Json.list;
import static util.simplejson.Json.map;
import static util.simplejson.Json.parse;
import static util.simplejson.Json.select;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.junit.Test;

public class TestParser {

    @Test
    public void testNumber() {
        assertEquals(123.45, parse("  123.45 "));
        assertEquals(123.45, parse(" +123.45 "));
        assertEquals(-123.45, parse(" -123.45 "));
        assertEquals(123.45, parse("  12.345e1 "));
        assertEquals(123.45, parse(" +12.345e1 "));
        assertEquals(-123.45, parse(" -12.345e1 "));
        assertEquals(123.45, parse("  12.345e+1 "));
        assertEquals(123.45, parse("  1234.5e-1 "));
        assertEquals(123.45, parse("  12345e-2"));
        assertEquals(1.2345678901234567890, parse("  1.2345678901234567890"));
    }

    @Test
    public void testBoolean() {
        assertEquals(true, parse("\n\ntrue\n\n"));
        assertEquals(false, parse("\n\nfalse\n\n"));
    }

    @Test
    public void testIdentifier() {
        assertEquals("TRUE", parse("TRUE"));
        assertEquals("𩸽", parse("𩸽"));
    }

    @Test
    public void testString() {
        assertEquals("TRUE", parse("  \"TRUE\"  "));
        assertEquals("KEY", parse("\n\t\r\n  \"KEY\"  "));
        assertEquals("𩸽", parse("  \"𩸽\""));
        assertEquals(" \n\r\t\f\b ", parse("  \" \\n\\r\\t\\f\\b \""));
    }

    @Test
    public void testNull() {
        assertEquals(null, parse("  null"));
    }

    @Test
    public void testList() {
        assertEquals(list(), parse("  [  ]"));
        assertEquals(list(1.0, 2.0), parse("  [  1  , 2  ]  "));
    }

    @Test(expected = RuntimeException.class)
    public void testListNotEnclosed() {
        assertEquals(list(), parse("  ["));
    }

    @Test
    public void testMap() {
        assertEquals(map(), parse("  {  }"));
        assertEquals(map("key", "value"), parse(" {\"key\" : \"value\"}"));
        assertEquals(map("key", "value", "key2", "value2"), parse(" {\"key\" : \"value\" , \"key2\" : \"value2\" }"));
    }

    @Test(expected = RuntimeException.class)
    public void testMapNotClosed() {
        parse("{");
    }

    @Test(expected = RuntimeException.class)
    public void testMapNotstringKey() {
        parse("{  1 : 2 }");
    }

    @Test(expected = RuntimeException.class)
    public void testMapInvalidSeparator() {
        parse("{  \"key\" = 2 }");
    }

    @Test(expected = RuntimeException.class)
    public void testUnknownChar() {
        parse("?");
    }

    @Test(expected = RuntimeException.class)
    public void testUnterminatedString() {
        parse("\"abc");
    }

    @Test(expected = RuntimeException.class)
    public void testEscapeUnterminatedString() {
        parse("\"abc\\");
    }

    @Test(expected = RuntimeException.class)
    public void testNewLineString() {
        parse("\"abc\ndef\"");
    }

    @Test(expected = RuntimeException.class)
    public void testCRString() {
        parse("\"abc\rdef\"");
    }

    @Test
    public void testParseReader() throws IOException {
        try (Reader reader = new StringReader("123")) {
            assertEquals(123.0, parse(reader));
        }
    }

    @Test
    public void testParseFile() throws IOException {
        Object parsed = parse(new File("data/generated.json"));
        assertEquals(true, get(parsed, "0.isActive"));
    }

    @Test
    public void testParseFileEncoded() throws IOException {
        Object parsed = parse(new File("data/generated.json"), StandardCharsets.UTF_8);
        assertEquals(true, get(parsed, "0.isActive"));
    }

    @Test
    public void testParsePath() throws IOException {
        Object parsed = parse(Paths.get("data/generated.json"));
        assertEquals(true, get(parsed, "0.isActive"));
    }

    @Test
    public void testParsePathEncoding() throws IOException {
        Object parsed = parse(Paths.get("data/generated.json"), StandardCharsets.UTF_8);
        assertEquals(true, get(parsed, "0.isActive"));
    }

    @Test
    public void testUnicodeEscape() throws IOException {
        Object parsed = parse(new File("data/afghanistan.json"));
        assertTrue(format(parsed).contains("アフガニスタン"));
    }

    @Test
    public void testUnicodeEscapeInvalid() throws IOException {
        Object parsed = parse("\"abcd\\u123x\"");
        assertEquals("abcdu123x", parsed);
    }

    @Test
    public void testSample() {
        String json = ""
            + "{"
            + "    \"response\": 200,"
            + "    \"responseMsg\": \"Allright\","
            + "    \"location\": ["
            + "      {"
            + "        \"stateId\": 1,"
            + "        \"stateName\": \"West Bengal\","
            + "        \"district\": ["
            + "          {"
            + "            \"districtId\": 15,"
            + "            \"districtName\": \"abc\","
            + "            \"village\": ["
            + "              {"
            + "                \"villageId\": 121,"
            + "                \"villageName\": \"ABC\""
            + "              },"
            + "              {"
            + "                \"villageId\": 90,"
            + "                \"villageName\": \"XYZ\""
            + "              }"
            + "            ]"
            + "          },"
            + "          {"
            + "            \"districtId\": 11,"
            + "            \"districtName\": \"xyz\","
            + "            \"village\": ["
            + "              {"
            + "                \"villageId\": 58,"
            + "                \"villageName\": \"PQR\""
            + "              }"
            + "            ]"
            + "          }"
            + "        ]"
            + "      }"
            + "    ]"
            + "  }";
        Object parsed = parse(json);
        System.out.println(parsed);
        System.out.println(format(select(parsed, "**.village")));
    }

}
