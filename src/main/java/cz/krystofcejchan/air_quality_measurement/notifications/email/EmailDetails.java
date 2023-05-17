package cz.krystofcejchan.air_quality_measurement.notifications.email;

import cz.krystofcejchan.air_quality_measurement.enums.Production;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.forecast.ForecastDataList;
import cz.krystofcejchan.air_quality_measurement.notifications.NotificationReceiver;
import cz.krystofcejchan.air_quality_measurement.utilities.psw.Psw;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.DAY;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.TIME;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    @Nullable
    private String attachment;

    @Contract(pure = true)
    public EmailDetails(@NotNull String recipient, String msgBody, String subject, @Nullable String attachment) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
        this.attachment = attachment;
    }

    @Contract(pure = true)
    public EmailDetails() {
    }


    @Contract(pure = true)
    public EmailDetails(@NotNull NotificationReceiver receiver, @NotNull EmailTemplates template) throws IllegalArgumentException {
        final String url = Psw.production == Production.TESTING ? "http://localhost:4200" : "https://krystofcejchan.cz/arduino_aiq_quality/beta";
        this.recipient = receiver.getEmailAddress();
        this.subject = "UPočasí |\s";

        switch (template) {
            case CONFIRM -> {
                this.msgBody = "<p style='text-align:center'><strong><span style='font-size:30px'><span style='background-color:#2969b0;color:#efefef;text-shadow:rgba(255,255,255,.65) 3px 2px 4px'>UPočasí</span></span></strong></p><p>Dobrý den,</p><p>k potvrzení klikněte: <a href='%s/predplatne/potvrzeni/%s/%s' rel='noopener noreferrer' target='_blank'>TADY</a></p><p><br></p><p>Pokud jste o nic nezažádali, e-mail můžete ignorovat nebo smazat.</p><p><br></p><p><br></p><p><br></p><hr><p><span style='font-size:10px'><strong>E-mail byl vygenerován automaricky - neodpovídejte na něj.</strong></span></p>"
                        .formatted(url, receiver.getId(), receiver.getRndHash());
                this.subject += "Potvrzení";
            }
            case WEATHER_FORECAST -> {
                TIME[] dayTimes = {TIME.AM_6, TIME.AM_9, TIME.AM_12, TIME.PM_3, TIME.PM_6, TIME.PM_9};

                var tempAvgByTime = ForecastDataList.forecastAtHourList.parallelStream()
                        .filter(it -> it.getDay() == DAY.TODAY && Arrays.stream(dayTimes).anyMatch(time -> time == it.getTime()))
                        .collect(Collectors.toMap(ForecastAtHour::getTime, ForecastAtHour::getTemperatureC));

                var tempList = ForecastDataList.forecastAtHourList.stream().filter(day -> day.getDay() == DAY.TODAY)
                        .toList();

                DecimalFormat decimalFormatForTemp = new DecimalFormat("#0.00");
                //<p style='text-align:center'><span style='font-size:30px;color:#2c82c9'><strong><br></strong></span></p><p>Dobr&yacute; den,</p><p>dnes bude v Olomouci %s s teplotou %s&deg;C.</p><p><br>V&iacute;ce informac&iacute; m&uring;&zcaron;ete naj&iacute;t <a href='https://krystofcejchan.cz/arduino_aiq_quality/beta/predpoved/' rel='noopener noreferrer' target='_blank'>na webov&eacute; str&aacute;nce ZDE.</a></p><p style='text-align:center'><strong><span style='font-size:36px;color:#2c82c9'>UPo&ccaron;as&iacute;</span></strong></p><p><br></p><p><br></p><hr><p><span style='font-size:10px'>Pokus nechcete dost&aacute;vat tyto upozorn&ecaron;n&iacute;, m&uring;&zcaron;ete tak prov&eacute;zt <a href='%s/predplatne/zruseni/%s/%s' rel='noopener noreferrer' target='_blank'> ZDE</a>.</span></p>
                this.msgBody =
                        String.format("<div style='font-family: Times, \"Times New Roman\", Georgia, serif;'><p style='text-align:center'><span style='font-size:30px;color:#2c82c9'><strong><br></strong></span></p><p>Dobrý den,</p><p>dnes bude v Olomouci %s</p><table style=\"border-collapse:collapse;border-color:#9ABAD9;border-spacing:0;border-style:solid;border-width:1px\" class=\"tg\"><thead><tr><th style=\"background-color:#409cff;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#fff;font-family:Arial, sans-serif;font-size:14px;font-weight:bold;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">Čas</th><th style=\"background-color:#409cff;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#fff;font-family:Arial, sans-serif;font-size:14px;font-weight:bold;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">Teplota [°C]</th></tr></thead><tbody><tr><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:serif !important;font-size:15px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">6:00</td><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr><tr><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">9:00</td><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr><tr><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">12:00</td><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr><tr><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">15:00</td><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr><tr><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">18:00</td><td style=\"background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr><tr><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">21:00</td><td style=\"background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal\">%s</td></tr></tbody></table><p><br>Více informací můžete najít <a href='https://krystofcejchan.cz/arduino_aiq_quality/beta/predpoved/' rel='noopener noreferrer' target='_blank'>na webové stránce ZDE.</a></p><p style='text-align:center'><strong><span style='font-size:36px;color:#2c82c9'>UPočasí</span></strong></p><p><br></p><p><br></p><hr><p><span style='font-size:10px'>Pokus nechcete dostávat tyto upozornění, můžete tak provést <a href='%s/predplatne/zruseni/%s/%s' rel='noopener noreferrer' target='_blank'> ZDE</a>.</span></p></div>",
                                weatherCodeToDescriptionInCzech(tempList.stream()
                                        .filter(it -> it.getTime() == TIME.AM_12)
                                        .limit(1)
                                        .findAny()
                                        .orElseThrow(DataNotFoundException::new)
                                        .getWeatherCode()),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_6)),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_9)),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_12)),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.PM_3)),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.PM_6)),
                                decimalFormatForTemp.format(tempAvgByTime.get(TIME.AM_9)),
                                url,
                                receiver.getId(),
                                receiver.getRndHash());
                //this.msgBody = new String(this.msgBody.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8);
                this.subject += "Dnešní předpověď pro Olomouc";
            }
            case UNSUBSCRIBE -> {
                this.msgBody = "<p style='text-align:center'><span style='font-size:30px;color:#efefef'><span style='background-color:#2969b0'>UPočasí</span></span></p><p style='text-align:left'><span style='font-size:14px;color:#2969b0'>Dobrý den, vaše předplatné bylo zrušeno.<br></span></p>";
                this.subject += "Zrušení Vašeho předplatného";
            }
            default -> throw new IllegalArgumentException();

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

    /*
    <p style='text-align:center'><strong><span style='font-size:30px'><span style='background-color:#2969b0;color:#efefef;text-shadow:rgba(255,255,255,.65) 3px 2px 4px'>UPočas&iacute;</span></span></strong></p><p>Dobr&yacute; den,</p><p>k potvrzen&iacute; klikněte zde:<a href='https://google.cz' rel='noopener noreferrer' target='_blank'>TADY</a></p><p><br></p><p>Pokud jste o nic nezaž&aacute;dali, e-mail můžete ignorovat nebo smazat.</p><p><br></p><p><br></p><p><br></p><hr><p><span style='font-size:10px'><strong>E-mail byl vygenerov&aacute;n automaricky - neodpov&iacute;dejte na něj.</strong></span></p>
     */

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
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