package com.emearchantpay.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class BackendApplicationTests {
	@Test
	void contextLoads() {

	}
	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}


}
