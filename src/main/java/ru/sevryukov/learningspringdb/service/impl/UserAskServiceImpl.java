package ru.sevryukov.learningspringdb.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.InputReader;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.UserAskService;

@Service
@RequiredArgsConstructor
public class UserAskServiceImpl implements UserAskService {

    private final InputReader inputReader;
    private final OutputService outputService;

    public String askUser(String text) {
        outputService.printOutput(text);
        var answer = inputReader.readInput();
        if (!answer.isEmpty()) {
            return answer;
        }
        return askUser(text);
    }
}
