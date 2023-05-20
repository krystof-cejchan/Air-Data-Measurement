package cz.krystofcejchan.air_quality_measurement.utilities;

import org.jetbrains.annotations.NotNull;

public class HtmlCharacters {
    public static @NotNull String toHtmlEntities(@NotNull String original) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '\'' || c == '&')
                sb.append("&#").append((int) c).append(";");
            else
                sb.append(c);

        }
        return sb.toString();
    }
}
