package cz.krystofcejchan.air_quality_measurement.notifications.email;

import com.google.common.html.HtmlEscapers;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.HtmlUtils.*;

import java.nio.charset.StandardCharsets;

class EmailDetailsTestEscapeCharactersTest {

    @Test
    void EscapeCharacters(){
        var s ="ahoj <br> čřa";
        System.out.println(encodeSpecialCharsToEntityNumber(s));
        System.out.println(HtmlEscapers.htmlEscaper().escape(s));
    }
    public static @NotNull String encodeSpecialCharsToEntityNumber(@NotNull String inputStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputStr.length(); i++) {
            char c = inputStr.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '\'' || c == '&') {
                sb.append("&#").append((int) c).append(";");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}