package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.AuthorAddService;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.UserAskService;

@Service
@RequiredArgsConstructor
public class AuthorAddServiceImpl implements AuthorAddService {

    private final UserAskService userAskService;
    private final AuthorService authorService;

    private static final String MSG_TEMPLATE = "Enter author %s name...";

    @Override
    public void addAuthor() {
        var firstName = userAskService.askUser(String.format(MSG_TEMPLATE, "first"));
        var lastName = userAskService.askUser(String.format(MSG_TEMPLATE, "last"));
        authorService.addAuthor(firstName, lastName);
    }
}
