package com.example.demo.scheduler;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Disabled
@SpringBootTest
public class UserSchedulersTest {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testFetchUsersAndSendSaMail()
    {
        userScheduler.fetchUsersAndSendSaMail();
    }
}
