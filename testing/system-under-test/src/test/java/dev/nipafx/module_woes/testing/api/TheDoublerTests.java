package dev.nipafx.module_woes.testing.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheDoublerTests {

	@ParameterizedTest
	@CsvSource(textBlock = """
			1, 2
			2, 4
			3, 6
			4, 8
			"""
	)
	void testSomethingCool(int number, int expectedResult) {
		var doubler = new TheDoubler();
		int result = doubler.somethingCool(number);

		assertEquals(expectedResult, result);
	}

	@Test
	void testRunsOnModulePath() {
		assertTrue(getClass().getModule().isNamed());
	}

}
