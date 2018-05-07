# Stream basics

## Basic transformations with `map`, and `flatMap` on basic collections

*1*. `map` each number to its double.        

```tut
val ns       = List(1, 2, 3, 4, 5)
val doubled1 = ns.map(n => n * 2)
val doubled2 = ns.map(_ * 2)
```

*2*. `flatMap`: for each number, produce the same number and its negative      

```tut
val mirrored = ns.flatMap(x => List(x, -x))
```

## Akka Streams