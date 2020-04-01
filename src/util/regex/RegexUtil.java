package util.regex;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static String replaceAll(CharSequence cseq, String regex, Function<MatchResult, String> replacement) {
        Matcher matcher = Pattern.compile(regex).matcher(cseq);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String replaced = replacement.apply(matcher.toMatchResult());
            matcher.appendReplacement(sb, replaced);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static int toInt(String s) {
        return Integer.parseInt(s);
    }

}
