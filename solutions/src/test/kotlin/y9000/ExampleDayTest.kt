package y9000

import io.github.jadarma.aoc.test.AdventSpec
import io.kotest.core.spec.DisplayName

@DisplayName("Y9000D01 Example Day")
class ExampleDayTest : AdventSpec(ExampleDay(), {
    partOne {
        "1" shouldOutput 1
        listOf("1,2,3", "0,3,3") shouldAllOutput 6
        "word".shouldNotBeValidInput()
    }
    partTwo {
        "1" shouldOutput 1
        "1,2,3" shouldOutput 2
    }
})
