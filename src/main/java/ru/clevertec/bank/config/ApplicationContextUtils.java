package ru.clevertec.bank.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationContextUtils {

    @Getter
    @Setter
    private static ApplicationContext applicationContext;

}
