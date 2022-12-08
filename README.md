# Advent Of Code - Kotlin

My solutions to [Advent of Code](https://adventofcode.com/), using idiomatic Kotlin and my very own helper framework
to make everything clean and easy to use.

_"Anything that's worth doing, is worth overdoing."_

---

## Usage Guide

### Use as Template
This project already comes with my own solutions.
Fortunately, you can very easily remove them and implement them yourself.

Either clone the [clean-template](https://github.com/Jadarma/advent-of-code-kotlin/tree/clean-template) branch, or just
delete my implementations and inputs:

```shell
rm -r ./solutions/src/main/kotlin/y*
rm -r ./solutions/src/test/kotlin/y*
rm -r ./solutions/src/test/resources/aoc/y*
```

More about what these paths mean will be explained shortly.

### Project Structure
To add your solutions you only need to add the input and write the implementation in the `:solutions` subproject.
The `:core` subproject(s) only contain the _"framework"_ and test helpers.
You don't need to touch anything here unless you want to add new features.

Here is a sample tree of the `:solution` subproject source set.
```
src
 ├── main
 │  └── kotlin
 │     └── y2015
 │        └── Y2015D01.kt
 └── test
    ├── kotlin
    │  └── y2015
    │     └── Y2015D01Test.kt
    └── resources
       └── aoc
          └── y2015
             └── d01
                ├── input.txt
                ├── solution_part1.txt
                └── solution_part2.txt
```

There are a few simple key points here:
- Solutions go into `src/main/kotlin`.
  While it is not a requirement to split them into packages by year or have a specific naming convention for the class
  names, doing so helps with organising.
- Unit tests go into `src/test/kotlin` and should have the same structure as the `main` source set.
- Input data goes into `src/test/resources`.
  The format for these ***is enforced*** and needs to be followed:
  - The directory for each day has the base path: `/aoc/y[year]/d[zeroPaddedDay]`
  - In that directory, the input and the solutions go in the respective text files: `input.txt`, 
    `solution_part1.txt`, and `solution_part2.txt`.
  - If a solution is not yet known, it can be skipped.
  - On problems without a part 2 _(e.g.: Y2015D25)_ you can add `NoSolution` as the file content.

### Implementing Solutions

All solutions should implement the base `AdventDay` class, which requires the `year` and `day`.

After which you only need to override the `partOne` and `partTwo` functions, which supply you with the (entire) input.

If you find the challenge involves heavy computation, such as brute forcing hashes, you can also implement the marker
interface `ExpensiveAdventDay`, which will come in handy when running tests.

A minimal example:
```kotlin
import io.github.jadarma.aoc.AdventDay

class Y2015D01 : AdventDay(2015, 1) {
    override fun partOne(input: String) = "Won't spoil it here"
    override fun partTwo(input: String) = "But you get the idea."
}
```

**TIP**: The given `input` is the entire contents of the file, but you sometimes need to read it line by line.
For this, don't use `input.split('\n')`, you have the more idiomatic `input.lines()`.
Even better,  use `input.lineSequence()` for more efficiency when many stream operators are needed.

### Running and Testing Solutions

The setup relies on using unit tests to run and validate your implementations, keeping your `main` source-set to a 
minimum, and uses the [Kotest](https://kotest.io) framework.
To make things as easy as possible, a specialized spec named `AdventSpec` is introduced.

#### Writing the Test

First, set up the input and/or solutions as [described](#project-structure).
Then, create a test class that extends `AdventSpec` and pass your implementation, along with the configuration lambda.
If you are new to Kotest, it's important to note the curly brackets are in the constructor, and not the class body.

Just for the sake of making it pretty, you can also give it a custom display name:

```kotlin
import io.github.jadarma.aoc.AdventSpec
import io.kotest.core.spec.DisplayName

@DisplayName("Y2015D01 Not Quite Lisp")
class Y2015D01Test : AdventSpec(Y2015D01(), {})
```

You are now ready to write your tests!
While the `AdventSpec` is just an extension of Kotest's
[FunSpec](https://kotest.io/docs/framework/testing-styles.html#fun-spec), it provides some special DSL functions to
minimize boilerplate when solving the AoC problems.
Of course, you may still write normal `context` and `test` functions alongside them, and can access your implementation
via the `day` property.

These extra DSL features are `partOne` and `partTwo`.
They automatically try to run and validate the `AdventDay` against the data in the test resources.
Furthermore, it provides a context in which you may more easily assert input-output pairs for the given part.

These are a great place to put the examples given in the problem description.
For example, let's say we were implementing [Y2015D01](https://adventofcode.com/2015/day/1).
Our test would look like this:

```kotlin
import io.github.jadarma.aoc.AdventSpec
import io.kotest.core.spec.DisplayName

@DisplayName("Y2015D01 Not Quite Lisp")
class Y2015D01Test : AdventSpec(Y2015D01(), {
    partOne {
        listOf("(())", "()()") shouldAllOutput 0
        listOf("(((", "(()(()(", "))(((((") shouldAllOutput 3
        listOf("())", "))(") shouldAllOutput -1
        listOf(")))", ")())())") shouldAllOutput -3
    }
})
```

If you do not have proper examples, or do not wish to include them, simply don't pass the lambda: `partOne()`.

The concept is identical for `partTwo`.

**TIP:** Although the actual Advent of Code inputs are always valid, if you want to go the extra mile and also handle
input validation, you may use the `shouldNotBeValidInput` validator, which would fail your test if it does not throw an
`IllegalArgumentException`.

#### Running tests

It is highly recommended you use the [Kotest Plugin](https://plugins.jetbrains.com/plugin/14080-kotest), and run each
test case individually, directly from the IDE.

**Getting the Output**

For each implemented part, you should see a context with its name.
Inside it, the _Outputs a solution_ test will contain your output for the `input.txt` from the test resrouces.
If it doesn't exist yet, the test is disabled.
Otherwise, clicking it should show you the answer and the time it took to process it:

```text
Your (Unverified) answer was: 123
Your time was: 1.211606ms
```

Notice the _(Unverified)_ tag.
This is because although we have an input, we do not know the solution yet.
If we take our answer, submit it on the AoC website and receive our star, we can then add it in the `solutionPartX.txt`,
and all subsequent runs will also validate the answer against the known solution, making it easy to refactor.

**Temporarily Skipping Parts**

You may find yourself that for some more expensive days, you don't want to keep running the
code for part one while you're still trying to implement the second one. You can skip the execution of either parts
without commenting your code, by prepending them with an `'x'`: `xpartOne` and `xpartTwo`.

**Running from CLI**

If you wish to run the tests from the terminal, you can do so as well.
Examples of Gradle commands for running tests:

```shell
# Run all tests
./gradlew :solutions:test

# Run a single test (provide path to test class)
./gradlew :solutions:test --tests 'y2015.Y2015D01Test'

# Run all tests for a given year (provide path to package)
./gradlew :solutions:test --tests 'y2015.*'
```

**TIP:** When running tests in bulk, but you want to skip expensive days, you can use Kotest's
[tag](https://kotest.io/docs/framework/tags.html#running-with-tags) feature.
Remember to mark your `AdventDay`s as such to make use of this feature.

```shell
# Run non-expensive tests for a given year
./gradlew :solutions:test --tests 'y2015.*' -Dkotest.tags='!ExpensiveDay'
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) for details.
