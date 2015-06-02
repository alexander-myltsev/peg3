package org.peg3

import org.peg3.Rules._
import org.peg3.parser.{ Q, dsl }
import org.scalatest.{ FlatSpec, ShouldMatchers }

trait ExampleTester extends FlatSpec {
  def runTest[T](body: => T): Exp[T] = {
    intercept[scala.NotImplementedError] {
      body
    }
    Q.poll().asInstanceOf[Exp[T]]
  }
}

class ParserTest extends FlatSpec with ShouldMatchers with ExampleTester {
  "dsl" should "work with str" in {
    runTest(dsl {
      str("ab")
    }) should be(StringLiteral(Const("ab")))
  }

  "dsl" should "work with sequence" in {
    runTest(dsl {
      sequence(str("ab"), str("cd"))
    }) should be(Sequence(StringLiteral(Const("ab")), StringLiteral(Const("cd"))))
  }
}
