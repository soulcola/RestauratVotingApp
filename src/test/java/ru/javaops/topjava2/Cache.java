package ru.javaops.topjava2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class Cache {
    @Autowired
    private ApplicationContext context;

    @Test
    void checkCache(){
        Assertions.assertNotNull(context.getBean("cacheManager"));
    }
}
