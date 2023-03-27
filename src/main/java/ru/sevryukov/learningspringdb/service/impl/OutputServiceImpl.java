package ru.sevryukov.learningspringdb.service.impl;

import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.OutputService;

@Service
public class OutputServiceImpl implements OutputService {

    @Override
    public void printOutput(String text) {
        System.out.println(text);
    }
}
