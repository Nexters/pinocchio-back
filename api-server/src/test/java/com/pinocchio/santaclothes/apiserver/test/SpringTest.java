package com.pinocchio.santaclothes.apiserver.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.pinocchio.santaclothes.apiserver.ApiServerApplication;

@SpringBootTest(classes = {ApiServerApplication.class})
@ActiveProfiles("test")
public abstract class SpringTest {
}
