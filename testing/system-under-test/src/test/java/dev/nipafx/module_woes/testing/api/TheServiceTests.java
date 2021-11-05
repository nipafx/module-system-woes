package dev.nipafx.module_woes.testing.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheServiceTests {

	@ParameterizedTest
	@CsvSource(textBlock = """
			1, 2
			2, 4
			3, 6
			4, 8
			"""
	)
	void testPublicService(int number, String expectedResult) {
		var service = new TheService();
		String result = service.method(number);

		assertEquals(expectedResult, result);
	}

	@Test
	void testRunsOnModulePath() {
		assertTrue(getClass().getModule().isNamed());
	}

}
