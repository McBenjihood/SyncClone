# Java Stream API – Quick Reference

A Stream is a sequence of elements supporting functional-style pipeline transformations. Streams do not store data; they process data from a source (collections, arrays, generator functions) without modifying the original source.

---

## 1. Key Concepts

- **Intermediate Operations** (`filter`, `map`, `flatMap`, `sorted`, `distinct`):
    - Return a new `Stream`.
    - **Lazy evaluation:** None of them execute until a terminal operation is invoked.
- **Terminal Operations** (`toList`, `collect`, `count`, `forEach`, `reduce`, `anyMatch`):
    - Return a concrete result, collection, or `void`.
    - Trigger execution of the pipeline. Once closed, the stream cannot be reused.

---

## 2. Common Intermediate Operations

### Filtering: `.filter(Predicate<T>)`
Keeps only elements matching the condition.
```java
List<Car> sedans = cars.stream()
    .filter(car -> "Sedan".equals(car.type()))
    .toList();
```

### Mapping (1-to-1): `.map(Function<T, R>)`
Transforms each element from type `T` to type `R`.
```java
List<String> makes = cars.stream()
    .map(Car::make)
    .toList();
```

### FlatMapping (1-to-Many): `.flatMap(Function<T, Stream<R>>)`
Transforms each element into a stream and flattens nested streams into a single stream.
```java
List<String> makeAndModels = cars.stream()
    .flatMap(car -> Stream.of(car.make(), car.model()))
    .toList();
```

---

## 3. Common Terminal & Collector Operations

### Converting to List
```java
// Java 16+
List<Car> list = stream.toList();

// Pre-Java 16
List<Car> list = stream.collect(Collectors.toList());
```

### Partitioning: `Collectors.partitioningBy(Predicate)`
Splits elements into two groups (`true` / `false`).
```java
Map<Boolean, List<Car>> partitioned = cars.stream()
    .collect(Collectors.partitioningBy(car -> "Sedan".equals(car.type())));

List<Car> sedans    = partitioned.get(true);
List<Car> nonSedans = partitioned.get(false);
```

### Grouping: `Collectors.groupingBy(...)`

**Simple grouping (`Map<K, List<T>>`):**
```java
Map<String, List<Car>> byType = cars.stream()
    .collect(Collectors.groupingBy(Car::type));
```

**Grouping with downstream collector (`Map<K, Map<K2, V>>`):**
```java
Map<String, Map<String, Integer>> grouped = cars.stream()
    .collect(Collectors.groupingBy(
        Car::type,
        Collectors.toMap(Car::model, Car::engineCapacity)
    ));
```

---

## 4. Parallel Streams

Executes operations concurrently across threads using the common `ForkJoinPool`.

```java
// Create directly
cars.parallelStream()
    .forEach(car -> process(car));

// Convert between parallel and sequential in pipeline
cars.stream()
    .parallel()
    .filter(car -> car.engineCapacity() > 2000)
    .sequential() // switches rest of the pipeline back to single thread
    .toList();
```

> **Rule of thumb:** Only use `.parallelStream()` for CPU-heavy tasks or very large datasets with stateless, thread-safe operations. Overhead may make small datasets slower.