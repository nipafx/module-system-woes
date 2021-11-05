package dev.nipafx.module_woes.testing.internal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheStringerTests {

	@ParameterizedTest
	@CsvSource(textBlock = """
			1, 1
			2, 2
			3, 3
			4, 4
			"""
	)
	void testDoIt(int number, String expectedResult) {
		var stringer = new TheStringer();
		String result = stringer.doIt(number);

		assertEquals(expectedResult, result);
	}

	@Test
	void testRunsOnModulePath() {
		assertTrue(getClass().getModule().isNamed());
	}

}
