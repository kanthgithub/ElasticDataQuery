package com.elasticDataQuery.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderUtil {

    public static List<String> readFileTextToLines(Path filePath) throws Exception{

       return Files.readAllLines(filePath);

    }
}
