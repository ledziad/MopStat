package com.mopstat.mopstat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		excludeAutoConfiguration = {
				SecurityAutoConfiguration.class
		}
)
class MopStatApplicationTests {

	@Test
	void contextLoads() {}
}

