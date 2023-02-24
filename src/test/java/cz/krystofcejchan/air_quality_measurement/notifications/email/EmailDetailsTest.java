package cz.krystofcejchan.air_quality_measurement.notifications.email;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

class EmailDetailsTest {

    @Test
    void getTextFromFile() throws IOException {
        Resource resource = new ClassPathResource("email_template_texts/confirm_email_text.txt");
        File file = resource.getFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        var array = bufferedReader.lines().collect(Collectors.joining());
        Resource resource1 = new ClassPathResource("email_template_texts/confirm_email_text.html");
        File file1 = resource1.getFile();
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));

        var array1 = bufferedReader1.lines().collect(Collectors.joining());
        System.out.println(array);
        System.out.println(array1);
    }
}