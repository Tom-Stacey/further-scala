package topics.typealiases

object TypeAliases {
  // The tests in TypeAliasesSpec will fail to compile until these are correctly defined.
  // Add in the type definitions to so that the tests compile.

  /* 1. Type aliases can be used to reference another type directly
   * e.g.
   * type MyType = Int
   * type MyType = Boolean
   */

  type Type1


  /* 2. Type aliases can also be used to reference a kinded type
   * e.g.
   * type MyType = Seq[Int]
   * type MyType = Future[String]
   */

  type Type2


  /* 3. Type aliases can take arguments like a function. These are called higher kinded types.
   * e.g.
   * type HigherKindedType[A] = Seq[A]
   * type HigherKindedType[A] = Future[A]
   */

  type Type3[A]


  /* 4. Type aliases can also point to other type aliases.
   * e.g.
   * type MyType1 = String
   * type MyType2 = Seq[MyType1]
   */

  type Type4a
  type Type4b[A]


  /* 5. Type aliases can also be used as arguments for higher kinded types.
   * e.g.
   * type MyType1[A] = Seq[A]
   * type MyType2 = MyType1[String]
   */

  type Type5a[A]
  type Type5b

}
