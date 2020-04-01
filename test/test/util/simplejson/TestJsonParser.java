package test.util.simplejson;

import static util.simplejson.JsonParser.parse;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import util.simplejson.Json;
import util.simplejson.JsonParser.ArrayBuilder;
import util.simplejson.JsonParser.CollectionToken;
import util.simplejson.JsonParser.DefaultHandler;
import util.simplejson.JsonParser.ObjectBuilder;

public class TestJsonParser {

    static class Point {
        public double x, y;

        @Override
        public String toString() {
            return String.format("Point(%s, %s)", x, y);
        }
    }

    static class State {
        public boolean on, reachable;
        public int bri, hue, sat;
        public String effect, alert, colormode;
        public Point xy;
        public double ct;
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("State {");
            sb.append("on:").append(on);
            sb.append(" bri:").append(bri);
            sb.append(" hue:").append(hue);
            sb.append(" sat:").append(sat);
            sb.append(" effect:").append(effect);
            sb.append(" xy:").append(xy);
            sb.append(" ct:").append(ct);
            sb.append(" alert:").append(alert);
            sb.append(" colormode:").append(colormode);
            sb.append(" reachable:").append(reachable);
            sb.append("}");
            return sb.toString();
        }
    }

    @Test
    public void testJsonParser() throws IOException {
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
        try (Reader reader = new StringReader(json)) {
            Object parsed = parse(reader, new DefaultHandler() {

                @Override
                public ObjectBuilder object(CollectionToken parent) {
                    if (parent.is("state"))
                        return new ObjectBuilder() {

                            final State state = new State();

                            @Override
                            public Object get() {
                                return state;
                            }

                            @Override
                            public void set(String key, Object value) {
                                switch (key) {
                                case "on": state.on = (boolean) value; break;
                                case "bri": state.bri = (int)(double) value; break;
                                case "hue": state.hue = (int)(double) value; break;
                                case "sat": state.sat = (int)(double) value; break;
                                case "effect": state.effect = (String) value; break;
                                case "xy": state.xy = (Point) value; break;
                                case "ct": state.ct = (double) value; break;
                                case "alert": state.alert = (String) value; break;
                                case "colormode": state.colormode = (String) value; break;
                                case "reachable": state.reachable = (boolean) value; break;
                                }
                            }
                        };
                    else
                        return super.object(parent);
                }

                @Override
                public ArrayBuilder array(CollectionToken parent) {
                    System.out.println("start array parent=" + parent);
                    if (parent.is("xy"))
                        return new ArrayBuilder() {
                            final Point point = new Point();

                            @Override
                            public Object get() {
                                return point;
                            }

                            @Override
                            public void set(double index, Object value) {
                                switch ((int) index) {
                                case 0: point.x = (double) value; break;
                                case 1: point.y = (double) value; break;
                                }
                            }

                        };
                    else
                        return super.array(parent);
                }
            });
            System.out.println(Json.format(parsed));
        }
    }

}
