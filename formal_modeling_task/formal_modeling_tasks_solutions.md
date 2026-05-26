## Task 1 – Modeling a University

### a) Class Diagram

```text
+----------------+
|   University   |
+----------------+
| name           |
+----------------+

        1
        |
        | registers
        |
        *
+----------------+
|    Student     |
+----------------+
| legal: Bool    |
| major: Major   |
| sid: StudentID |
+----------------+
        ^
        |
  -------------------
  |                 |
+-----------+   +-----------+
|Undergrad  |   | Graduate  |
+-----------+   +-----------+

+----------------+
|     Major      |
+----------------+

+----------------+
|   StudentID    |
+----------------+
```

### Important Modeling Decisions

1. `Student` is an abstract superclass.
2. `Undergrad` and `Graduate` are disjoint subclasses.
3. Every student:
   - has exactly one major,
   - has exactly one student ID,
   - may be registered at exactly one university.
4. The boolean flag `legal` encodes whether the student is registered.
5. Two students are classmates iff:
   - they are registered at the same university,
   - they have the same major,
   - they are both graduates or both undergraduates,
   - and they are not the same student.

## b) Alloy Model

```alloy
abstract sig Student {
    sid: one StudentID,
    major: one Major,
    university: lone University,
    legal: one Bool
}

sig Undergrad, Graduate extends Student {}

sig University {}

sig Major {}

sig StudentID {}

abstract sig Bool {}
one sig True, False extends Bool {}

fact LegalRegistration {
    all s: Student |
        (s.university != none) iff (s.legal = True)
}

fact UniqueStudentIDs {
    all disj s1, s2: Student |
        s1.sid != s2.sid
}

fact NoUnusedIDs {
    all id: StudentID |
        some s: Student | s.sid = id
}

pred classmates[s1, s2: Student] {
    s1 != s2
    s1.major = s2.major
    s1.university = s2.university

    (s1 in Undergrad and s2 in Undergrad)
    or
    (s1 in Graduate and s2 in Graduate)
}

fact NoMixedClassmates {
    all u: Undergrad, g: Graduate |
        not classmates[u, g]
}

run {} for exactly 2 University,
             exactly 3 Major,
             exactly 3 Student,
             exactly 3 StudentID
```

### Expected Visualization Properties

When executed in Alloy, the generated instance should satisfy:

- exactly 2 universities,
- exactly 3 majors,
- exactly 3 students,
- exactly 3 student IDs,
- all IDs assigned,
- no student simultaneously graduate and undergraduate,
- classmates only among students with same major and same university.

# Task 2 – UZH Bus

## Given Alloy Model

```alloy
sig UZHBusStation {
    next: set UZHBusStation
}

one sig ZurichCity, Irchel, Oerlikon, Schlieren in UZHBusStation {}

sig UZHBus {
    station: lone UZHBusStation
}

fact {
    no (Oerlikon - Schlieren)
    all s: UZHBusStation | UZHBusStation in s.^next and some s.next
    all b1, b2: UZHBus | b1.station != b2.station
}

pred show {}
```

## a) Instance for `run show for 2 but 1 UZHBusStation`

The scope command means:

- at most 2 atoms overall,
- but exactly 1 `UZHBusStation`.

Because the model declares:

```alloy
one sig ZurichCity, Irchel, Oerlikon, Schlieren in UZHBusStation {}
```

there must exist:

- ZurichCity
- Irchel
- Oerlikon
- Schlieren

all as distinct stations.

However, the scope forces exactly one `UZHBusStation`, which contradicts the declaration of four singleton stations.

Therefore:

## Result

```text
No instance exists.
```

The Alloy analyzer would report the model as UNSAT (unsatisfiable).

## b) Corrected Alloy Model

### Required Specifications

The corrected model must satisfy:

1. Exactly 4 stations.
2. Stations form one directed circle.
3. No station between Oerlikon and Schlieren.
4. At most one bus per station.
5. At least one bus.

## Correct Alloy Solution

```alloy
sig UZHBusStation {
    next: one UZHBusStation
}

one sig ZurichCity, Irchel, Oerlikon, Schlieren extends UZHBusStation {}

sig UZHBus {
    station: one UZHBusStation
}

fact {
    -- exactly one directed circle
    all s: UZHBusStation |
        UZHBusStation = s.^next

    -- Oerlikon directly connected to Schlieren
    Oerlikon.next = Schlieren

    -- at most one bus per station
    all disj b1, b2: UZHBus |
        b1.station != b2.station

    -- at least one bus
    some UZHBus
}

pred show {}

run show
```

## Explanation of the Fixes

### 1. `next: one UZHBusStation`

Changed from:

```alloy
next: set UZHBusStation
```

This guarantees every station has exactly one outgoing edge, which is necessary for a directed circle.

### 2. `extends` instead of `in`

Changed:

```alloy
one sig ZurichCity, Irchel, Oerlikon, Schlieren extends UZHBusStation {}
```

This ensures there are exactly four stations and no additional unnamed stations.

### 3. Directed Circle Constraint

```alloy
all s: UZHBusStation |
    UZHBusStation = s.^next
```

This guarantees every station can reach all others via `next`, forming a single strongly connected directed cycle.

### 4. Direct Connection Between Oerlikon and Schlieren

The original line:

```alloy
no (Oerlikon - Schlieren)
```

was incorrect and meaningless for the intended property.

It was replaced with:

```alloy
Oerlikon.next = Schlieren
```

which guarantees no intermediate station between them.

### 5. At Most One Bus Per Station

```alloy
all disj b1, b2: UZHBus |
    b1.station != b2.station
```

Adding `disj` avoids comparing a bus with itself.

### 6. At Least One Bus

```alloy
some UZHBus
```

ensures at least one bus exists.

