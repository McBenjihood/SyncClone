# Java Lambda Expressions – Quick Reference

A lambda expression is a compact way to implement a **functional interface** without creating a concrete or anonymous class. It treats executable code/behavior as data that can be stored in a variable or passed as a parameter.

---

## 1. Key Concept: Functional Interface (SAM)

A lambda can **only** be used where a **Functional Interface** (Single Abstract Method) is expected.

```java
@FunctionalInterface
public interface Printable {
    String print(String suffix); // Exactly one abstract method
}
```
> Can still have `default` or `static` methods, but only **one** abstract method.

---

## 2. Syntax Anatomy

A lambda strips away boilerplate (`public`, method name, return type) and connects parameters to the body using `->`:

```text
(parameters) -> { body }
```

### Parameter Rules
- **No parameters:** Parentheses required:  
  `() -> System.out.println("Hello")`
- **One parameter:** Parentheses optional; types are inferred:  
  `s -> System.out.println(s)` or `(s) -> System.out.println(s)`
- **Multiple parameters:** Parentheses and commas required:  
  `(p, s) -> p + s`

### Body & Return Rules
- **Single expression (implicit return):** Omit `{}` and `return`:  
  `s -> "Meow " + s`
- **Multi-line / block body:** Requires `{}` and explicit `return` / semicolons:
  ```java
  s -> {
      System.out.println("Printing...");
      return "Meow " + s;
  }
  ```

---

## 3. Usage Examples

### Assigned to a Variable
```java
// Stored as an instance of the functional interface
Printable myPrintable = s -> "Meow " + s;

// Invoked via the interface method
String result = myPrintable.print("!"); // "Meow !"
```

### Passed Directly as a Method Parameter
```java
// Method expecting a functional interface
void printThing(Printable printable) {
    System.out.println(printable.print("!"));
}

// 1. Multi-line / void action
printThing(s -> {
    System.out.println("Inside lambda");
    return "Meow " + s;
});

// 2. Single-line expression
printThing(s -> "Meow " + s);
```

---

## 4. Quick Comparison: Class vs. Anonymous vs. Lambda

| Implementation Style | Boilerplate | Readability |
| :--- | :--- | :--- |
| **Class (`implements`)** | High (separate class file/block) | Verbose for simple operations |
| **Anonymous Class** | Medium (`new Printable() { ... }`) | Cluttered syntax |
| **Lambda** | Minimal (`s -> "Meow " + s`) | Clean, concise, inlined |