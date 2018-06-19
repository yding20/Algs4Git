1. First submit
ASSESSMENT SUMMARY

Compilation:  PASSED
API:          PASSED

Findbugs:     FAILED (4 warnings)
PMD:          PASSED
Checkstyle:   FAILED (0 errors, 42 warnings)

Correctness:  28/49 tests passed
Memory:       22/22 tests passed
Timing:       116/125 tests passed

Aggregate score: 72.85%
[Compilation: 5%, API: 5%, Findbugs: 0%, PMD: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]


2. Second Submit

Fixed one bug inside neighbor()

Fix equal()

Fix immutable of the Board.class by moving the calculation process of Hamming and Manhattan inside the contructor of the class. 

ASSESSMENT SUMMARY

Compilation:  PASSED
API:          PASSED

Findbugs:     FAILED (2 warnings)
PMD:          PASSED
Checkstyle:   FAILED (0 errors, 49 warnings)

Correctness:  39/49 tests passed
Memory:       22/22 tests passed
Timing:       116/125 tests passed

Aggregate score: 86.32%
[Compilation: 5%, API: 5%, Findbugs: 0%, PMD: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]

3.Third Submit
Correctness:  49/49 tests passed
Memory:       22/22 tests passed
Timing:       117/125 tests passed

Aggregate score: 98.72%
[Compilation: 5%, API: 5%, Findbugs: 0%, PMD: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]

The main modification is change from import java.util.Stack to import edu.princeton.cs.algs4.Stack, it seems the sequence of iterator is different.

At last, there is still some memroy test unpassed.
