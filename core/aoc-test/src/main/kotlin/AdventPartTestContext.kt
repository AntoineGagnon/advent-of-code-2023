// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test

import io.github.jadarma.aoc.AdventDay
import io.github.jadarma.aoc.test.internal.wrapWithNewLinesIfMultiline
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe

/**
 * A context within which an [AdventDay] part may be tested. depending on who builds this context.
 * See [AdventSpec.partOne] and [AdventSpec.partTwo].
 * Within it, you may use [shouldOutput] as a shorthand to validate an input and output pair.
 */
public class AdventPartTestContext(private val implementation: (String) -> Any) {

    /** Asserts that when the advent day is given this string as input, it outputs the [expected] answer. */
    public infix fun String.shouldOutput(expected: Any) {
        withClue("Expected answer $expected for input ${wrapWithNewLinesIfMultiline()}") {
            val answer = shouldNotThrowAny { implementation(this).toString() }
            answer shouldBe expected.toString()
        }
    }

    /** Asserts that when the advent day is given any of these string as input, it outputs the [expected] answer. */
    public infix fun Iterable<String>.shouldAllOutput(expected: Any) {
        forEach { it shouldOutput expected }
    }

    /** Asserts that the given input is recognized as invalid, and an [IllegalArgumentException] is thrown. */
    public fun String.shouldNotBeValidInput() {
        withClue("Expected to get an IllegalArgumentException due to invalid input: ${wrapWithNewLinesIfMultiline()}") {
            shouldThrowExactly<IllegalArgumentException> { implementation(this) }
        }
    }

    /** Asserts that all the given inputs are recognized as invalid, and an [IllegalArgumentException] is thrown. */
    public fun Iterable<String>.shouldNotBeValidInputs() {
        forEach { it.shouldNotBeValidInput() }
    }
}
