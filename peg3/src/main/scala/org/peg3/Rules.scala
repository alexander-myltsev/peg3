package org.peg3

import ch.epfl.directembedding.transformers.reifyAs

trait Rule

object Rules {
  @reifyAs(StringLiteral)
  def str(s: String): Rule = ???

  @reifyAs(Sequence)
  def sequence(lhs: Rule, rhs: Rule): Rule = ???
}

trait Exp[T]
case class StringLiteral(s: Exp[String]) extends Exp[Rule]
case class Sequence(lhs: Exp[Rule], rhs: Exp[Rule]) extends Exp[Rule]
case class Const[T](x: T) extends Exp[T]
