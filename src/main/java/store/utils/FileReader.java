package store.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class FileReader {

    public BufferedReader readResourceFile(String filePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("리소스 파일을 찾을 수 없습니다.");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public void loadData(String filePath, Consumer<String> lineProcessor) {
        try (BufferedReader reader = readResourceFile(filePath)) {
            reader.readLine();
            reader.lines().forEach(lineProcessor);
        } catch (IOException e) {
            System.out.println("리소스 파일을 읽는 도중 오류가 발생했습니다.");
        }
    }
}
