package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {

        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"), Charset.forName("windows-1251"));
        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        Map<String, TreeSet<String>> result = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            List<String> lines = reader.lines()
                    .map(String::toLowerCase)
                    .filter(str -> str.matches("[а-яё]+") && str.length() >= 3)
                    .collect(Collectors.toList());
            for (String line : lines) {
                char[] symbols = line.toCharArray();
                Arrays.sort(symbols);
                String key = new String(symbols);
                result.computeIfAbsent(key, value -> new TreeSet<>()).add(line);
            }
        } catch (IOException e) { }
        return result.values()
                .stream()
                .filter(value -> value.size() >= 2)
                .collect(Collectors.toList());
    }
}