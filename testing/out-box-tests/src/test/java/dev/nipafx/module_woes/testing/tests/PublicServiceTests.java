package dev.nipafx.module_woes.testing.tests;

import dev.nipafx.module_woes.testing.api.TheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicServiceTests {

	@ParameterizedTest
	@CsvSource(textBlock = """
			1, 2
			2, 4
			3, 6
			4, 8
			"""
	)
	void testPublicService(int number, String expectedResult) {
		TheService publicService = new TheService();
		String result = publicService.method(number);

		assertEquals(expectedResult, result);
	}

	@Test
	void testRunsOnModulePath() {
		assertTrue(getClass().getModule().isNamed());
	}
}
