package luxe.chaos.helper.unsafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/11/30 20:07 <br />
 */
public class SimpleJson {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJson.class);
    private Map<String, String> theMap = new HashMap<>();

    public Optional<String> getString(String name) {
        return Optional.ofNullable(this.theMap.get(name));
    }

    public Optional<Integer> getInt(String name) {
        String value = this.theMap.get(name);
        if (Objects.isNull(value)) {
            return Optional.empty();
        }

        return Optional.of(Integer.parseInt(value, 10));
    }

    public Optional<Long> getLong(String name) {
        String value = this.theMap.get(name);
        if (Objects.isNull(value)) {
            return Optional.empty();
        }

        return Optional.of(Long.parseLong(value, 10));
    }

    public static SimpleJson valueOf(String input) {
        SimpleJson json = new SimpleJson();
        json.parse(input);
        return json;
    }


    private int parseNumber(String input, int begin, String name, StringBuilder value) {

        int index = begin;
        int len = input.length();
        for (; index < len; index++) {
            char c = input.charAt(index);
            if (c == ',' || c == ']' || c == '}') {
                break;
            }
            value.append(c);
        }

        this.theMap.put(name, value.toString());
        value.delete(0, value.length());
        return index;
    }

    private int parseString(String input, int begin, String name, StringBuilder value) {

        int index = begin;
        int len = input.length();
        boolean run = false;

        for (; index < len; index++) {
            char c = input.charAt(index);
            if (!run && c == '"') {
                run = true;
                continue;
            } else if (run && c == '"') {
                break;
            } else {
                value.append(c);
                if (c == '\\') {
                    value.append(++index);
                }
            }

        }

        this.theMap.put(name, value.toString());
        value.delete(0, value.length());
        return index;
    }

    private int parseObject(String input, int begin, String name, StringBuilder value) {

        int index = begin;
        int len = input.length();
        int stat = 0;
        for (; index < len; index++) {
            char c = input.charAt(index);
            value.append(c);
            if (c == '\\') {
                value.append(input.charAt(++index));
            }
            if (c == '{' || c == '[') {
                stat++;
            }
            if (c == '}' || c == ']') {
                stat--;
                if (stat == 0) {
                    break;
                }
            }
        }


        this.theMap.put(name, value.toString());
        value.delete(0, value.length());

        return index;
    }

    private void parse(String input) {
        int stat = 0;
        int index = 0;
        int len = input.length();
        StringBuilder name = new StringBuilder(len / 2 + 1);
        StringBuilder value = new StringBuilder(len / 2 + 1);
        for (; index < len; ) {
            char c = input.charAt(index);
            if (c == '{' || c == ',') {
                stat += 1;
                index = this.parse(input, ++index, name, value);
            } else if (c == '}') {
                stat -= 1;
                if (stat == 0) {
                    break;
                }
            } else {
                index += 1;
            }
        }
    }

    private int parse(String input, int begin, StringBuilder name,StringBuilder value ) {

        int index = begin;
        int len = input.length();

        int stat = 0;


        for (; index < len; index++) {
            char c = input.charAt(index);

            if (stat == 0 && c == '"') {
                stat = 1;
                continue;
            }
            if (stat == 1) {
                if (c == '\\') {
                    name.append(c).append(input.charAt(++index));
                } else if (c == '"') {
                    stat = 2;
                    continue;
                } else {
                    name.append(c);
                }
            }

            if (stat == 2) {
                if (c != ':' && !Character.isSpaceChar(c)) {
                    stat = 3;
                    break;
                }
            }
        }
        if (stat == 3) {
            char c = input.charAt(index);
            if (Character.isDigit(c)) {
                index = this.parseNumber(input, index, name.toString(), value);
            } else if (c == '"') {
                index = this.parseString(input, index, name.toString(), value);
            } else {
                index = this.parseObject(input, index, name.toString(), value);
            }
        }

        name.delete(0, name.length());
        value.delete(0, value.length());
        return index;
    }

    @Override
    public String toString() {
        return "Jsons{" +
                "theMap=" + theMap +
                '}';
    }

    public static void main(String[] args) {


    }

    public Set<Map.Entry<String, String>> entrySet() {
        return this.theMap.entrySet();
    }
}
