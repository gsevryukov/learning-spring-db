package ru.sevryukov.learningspringdb.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
@RequiredArgsConstructor
public class InputReaderImpl implements InputReader {

    @Override
    public String readInput() {
        try {
            var br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine().trim();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return "";
    }
}
