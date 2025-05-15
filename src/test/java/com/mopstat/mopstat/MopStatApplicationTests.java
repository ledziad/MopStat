package com.mopstat.mopstat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)  // wyłącza JwtFilter i CSRF w testach
class MopStatApplicationTests {
	@Test
	void contextLoads() {}
}
