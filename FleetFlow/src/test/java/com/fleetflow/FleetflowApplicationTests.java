package com.fleetflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FleetflowApplicationTests {

	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

}