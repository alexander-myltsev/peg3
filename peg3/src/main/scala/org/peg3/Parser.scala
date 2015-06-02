package org.peg3

import ch.epfl.directembedding.{ DETransformer, DslConfig }

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

package object parser {
  def dsl[T](block: T): T = macro implementations.liftRepNoDebug[T]

  def dslDebug[T](block: T): T = macro implementations.liftRepDebug[T]

  val Q = new java.util.concurrent.LinkedBlockingQueue[Exp[_]]()

  object Config extends ExampleConfig

  object ExampleConfig extends ExampleConfig

  trait VirtualizationOverrides {
  }

  /**
   * Configuration module for example DSL
   */
  trait ExampleConfig extends DslConfig with VirtualizationOverrides // with ExampleTypeOverrides
  {

    type Literal[T] = Const[T]
    type Rep[T] = Exp[T]

    def compile[T](e: Exp[T]): T = {
      Q.add(e)
      ???
    }

    def lift[T](e: T): Const[T] = Const(e)
  }

  object implementations {
    def liftRepDebug[T](c: Context)(block: c.Expr[T]): c.Expr[T] =
      liftRep(true)(c)(block)

    def liftRepNoDebug[T](c: Context)(block: c.Expr[T]): c.Expr[T] =
      liftRep(false)(c)(block)

    def liftRep[T](debug: Boolean)(c: Context)(block: c.Expr[T]): c.Expr[T] = {
      DETransformer[c.type, T, ExampleConfig](c)(
        "peg3.dsl",
        Config,
        Map.empty,
        Map.empty,
        Set.empty,
        None,
        // Note the explicit `apply`, this is necessary.
        None,
        if (debug) 2 else 0).apply(block)
    }
  }
}
