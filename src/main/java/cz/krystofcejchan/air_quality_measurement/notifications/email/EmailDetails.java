package cz.krystofcejchan.air_quality_measurement.notifications.email;

import cz.krystofcejchan.air_quality_measurement.enums.Production;
import cz.krystofcejchan.air_quality_measurement.forecast.ForecastDataList;
import cz.krystofcejchan.air_quality_measurement.notifications.NotificationReceiver;
import cz.krystofcejchan.air_quality_measurement.utilities.psw.Psw;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.DAY;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.TIME;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public sealed class EmailDetails permits EmailController.EmailDetailsSimple {
    /**
     * recipient's email address to an email text addressed to him
     */
    private final Map<String, String> recipientToText = new HashMap<>();
    private String subject;
    @Nullable
    private String attachment;

    @Contract(pure = true)
    public EmailDetails(String msgBody, String subject, @Nullable String attachment, @NotNull String @NotNull ... recipient) {
        try {
            this.recipientToText.putAll(Arrays.stream(recipient).distinct()
                    .collect(Collectors.toMap(
                            key -> key,
                            value -> msgBody,
                            (oldValue, newValue) -> newValue)));
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        this.subject = subject;
        this.attachment = attachment;
    }

    public EmailDetails(String msgBody, String subject, @NotNull String recipient) {
        this(msgBody, subject, null, recipient);
    }

    @Contract(pure = true)
    public EmailDetails(@NotNull EmailTemplates template, @NotNull NotificationReceiver... receivers) throws IllegalArgumentException {
        final String url = Psw.production == Production.TESTING ? "http://localhost:4200" : "https://krystofcejchan.cz/arduino_aiq_quality/beta";
        String weatherForecastText = null;
        if (template == EmailTemplates.WEATHER_FORECAST) {
            final String textUrl = "https://krystofcejchan.cz/projects/airM/weather_forecast.txt";
            try (InputStream inputStream = new URL(textUrl).openStream()) {
                weatherForecastText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (NotificationReceiver notificationReceiver : receivers) {
            this.subject = "UPočasí |\s";
            String msgBody;
            switch (template) {
                case CONFIRM -> {
                    msgBody = "<p style='text-align:center'><strong><span style='font-size:30px'><span style='background-color:#2969b0;color:#efefef;text-shadow:rgba(255,255,255,.65) 3px 2px 4px'>UPočasí</span></span></strong></p><p>Dobrý den,</p><p>k potvrzení klikněte: <a href='%s/predplatne/potvrzeni/%s/%s' rel='noopener noreferrer' target='_blank'>TADY</a></p><p><br></p><p>Pokud jste o nic nezažádali, e-mail můžete ignorovat nebo smazat.</p><p><br></p><p><br></p><p><br></p><hr><p><span style='font-size:10px'><strong>E-mail byl vygenerován automaricky - neodpovídejte na něj.</strong></span></p>"
                            .formatted(url, notificationReceiver.getId(), notificationReceiver.getRndHash());
                    this.subject += "Potvrzení";
                }
                case WEATHER_FORECAST -> {
                    TIME[] dayTimes = {TIME.AM_6, TIME.AM_9, TIME.AM_12, TIME.PM_3, TIME.PM_6, TIME.PM_9};

                    var tempAvgByTime = ForecastDataList.forecastAtHourList.stream()
                            .filter(it -> it.getDay() == DAY.TODAY && Arrays.stream(dayTimes).anyMatch(time -> time == it.getTime()))
                            .collect(Collectors.toMap(ForecastAtHour::getTime, Function.identity()));

                    var tempList = ForecastDataList.forecastAtHourList.stream()
                            .filter(day -> day.getDay() == DAY.TODAY && Arrays.stream(dayTimes)
                                    .anyMatch(t -> t.equals(day.getTime())))
                            .toList();

                    DecimalFormat decimalFormatForTemp = new DecimalFormat("#0.00'°C'");
                    IntSummaryStatistics temperatureSummary = tempList.stream().mapToInt(ForecastAtHour::getTemperatureC).summaryStatistics();
                    assert weatherForecastText != null;
                    msgBody = weatherForecastText.formatted(
                            temperatureSummary.getMin(), temperatureSummary.getMax(),
                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_6).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.AM_6).getWeatherCode()),

                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_9).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.AM_9).getWeatherCode()),

                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_12).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.AM_12).getWeatherCode()),

                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.PM_3).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.PM_3).getWeatherCode()),

                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.PM_6).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.PM_6).getWeatherCode()),

                            decimalFormatForTemp.format(tempAvgByTime.get(TIME.PM_9).getTemperatureC()),
                            weatherCodeToDescriptionInCzech(tempAvgByTime.get(TIME.PM_9).getWeatherCode()),

                            url,
                            notificationReceiver.getId(),
                            notificationReceiver.getRndHash());
                    this.subject += "Dnešní předpověď pro Olomouc";
                }
                case UNSUBSCRIBE -> {
                    msgBody = "<p style='text-align:center'><span style='font-size:30px;color:#efefef'><span style='background-color:#2969b0'>UPočasí</span></span></p><p style='text-align:left'><span style='font-size:14px;color:#2969b0'>Dobrý den, vaše předplatné bylo zrušeno.<br></span></p>";
                    this.subject += "Zrušení Vašeho předplatného";
                }
                default -> throw new IllegalArgumentException();

            }
            this.recipientToText.putIfAbsent(notificationReceiver.getEmailAddress(), msgBody);
        }

    }

    @Contract(pure = true)
    private String weatherCodeToDescriptionInCzech(int code) {
        return switch (code) {
            case 113 -> "Slunečno";
            case 116 -> "Částečně zataženo";
            case 119 -> "Zataženo";
            case 122 -> "Zamračeno";
            case 143 -> "Opar/Mlha";
            case 176 -> "Nepravidelný déšť";
            case 179 -> "Nepravidelné sněžení";
            case 182 -> "Nepravidelná plískanice";
            case 185 -> "Nepravidelné mrholení";
            case 200 -> "Náhlá bouřka";
            case 227 -> "Vítr se sněhem";
            case 230 -> "Blizard";
            case 248 -> "Mlha";
            case 260 -> "Mrznoucí mlha";
            case 263 -> "Nepravidelné slabé mrholení";
            case 266 -> "Slabé mrholení";
            case 281 -> "Mrznoucí mrholení";
            case 284 -> "Silné mrznoucí mrholení";
            case 293 -> "Občasný slabý déšť";
            case 296 -> "Slabý déšť";
            case 299 -> "Občasný mírný déšť";
            case 302 -> "Mírný déšť";
            case 305 -> "Občasný silný déšť";
            case 308 -> "Silný déšť";
            case 311 -> "Slabý mrznoucí déšť";
            case 314 -> "Mírný nebo silná mrznoucí déšť";
            case 317 -> "Mírná plískanice";
            case 320 -> "Mírná nebo silná plískanice";
            case 323 -> "Občasné slabé sněžení";
            case 326 -> "Slabé sněžení";
            case 329 -> "Občasné mírné sněžení";
            case 332 -> "Mírné sněžení";
            case 335 -> "Nepravidelné silné sněžení";
            case 338 -> "Silné sněžení";
            case 350 -> "Ledové pelety";
            case 353 -> "Lehká dešťová sprcha";
            case 356 -> "Mírná nebo silná dešťová sprcha";
            case 359 -> "Přívalová dešťová sprcha";
            case 362 -> "Slabé přeháňky plískanice";
            case 365 -> "Mírné nebo silné plískanicová sprcha";
            case 368 -> "Slabá sněžná sprcha";
            case 371 -> "Mírná nebo silná sněžná sprcha";
            case 374 -> "Slabý déšť se sněhem";
            case 377 -> "Slabý déšť se sněhem";
            case 386 -> "Slabé přeháňky ledových pelet";
            case 389 -> "Nepravidelný slabý déšť s bouřkou";
            case 392 -> "Nepravidelné slabé sněžení s bouřkou";
            case 395 -> "Mírné nebo silné sněžení s bouřkou";
            default -> "Chyba";
        };
    }


    public Map<String, String> getRecipientToText() {
        return recipientToText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public @Nullable String getAttachment() {
        return attachment;
    }

    public void setAttachment(@Nullable String attachment) {
        this.attachment = attachment;
    }
}