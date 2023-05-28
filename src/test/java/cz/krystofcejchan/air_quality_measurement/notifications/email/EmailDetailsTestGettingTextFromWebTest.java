package cz.krystofcejchan.air_quality_measurement.notifications.email;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class EmailDetailsTestGettingTextFromWebTest {

    @Test
    void getTextFromWeb() {
        final var expected = """
                <html>
                <body>
                   <p>Dobrý den,</p>
                   <p>dnes bude v Olomouci %s</p>
                   <table style="border-collapse:collapse;border-color:#9ABAD9;border-spacing:0;border-style:solid;border-width:1px" class="tg">
                      <thead>
                         <tr><th style="background-color:#409cff;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#fff;font-family:Arial, sans-serif;font-size:14px;font-weight:bold;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal">Čas</th><th style="background-color:#409cff;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#fff;font-family:Arial, sans-serif;font-size:14px;font-weight:bold;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal">Teplota [°C]</th></tr>
                      </thead>
                      <tbody>
                         <tr><td class="rowe">6:00</td><td class="rowe">%s</td></tr>
                         <tr><td class="rowo">9:00</td><td class="rowo">%s</td></tr>
                         <tr><td class="rowe">12:00</td><td class="rowe">%s</td></tr>
                         <tr><td class="rowo">15:00</td><td class="rowo">%s</td></tr>
                         <tr><td class="rowe">18:00</td><td class="rowe">%s</td></tr>
                         <tr><td class="rowo">21:00</td><td class="rowo">%s</td></tr>
                      </tbody>
                   </table>
                   <p><br>Více informací můžete najít <a href='https://krystofcejchan.cz/arduino_aiq_quality/beta/predpoved/' rel='noopener noreferrer' target='_blank'>na webové stránce ZDE.</a></p>
                   <p style='text-align:center'><strong><span style='font-size:36px;color:#2c82c9'>UPočasí</span></strong></p>
                   <p><br></p>
                   <p><br></p>
                   <hr>
                   <p><span style='font-size:10px'>Pokus nechcete dostávat tyto upozornění, můžete tak provést <a href='%s/predplatne/zruseni/%s/%s' rel='noopener noreferrer' target='_blank'> ZDE</a>.</span></p>
                                
                </body>
                <style>
                .rowe{
                background-color:#D2E4FC;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:serif !important;font-size:15px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal;
                }
                .rowo{
                background-color:#EBF5FF;border-color:#9ABAD9;border-style:solid;border-width:0px;color:#444;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;text-align:center;vertical-align:top;word-break:normal;
                }
                </style>
                </html>""";
        final var url = "https://krystofcejchan.cz/projects/airM/weather_forecast.txt";
        try (InputStream inputStream = new URL(url).openStream()) {
            Assertions.assertEquals(expected, IOUtils.toString(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}