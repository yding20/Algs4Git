1. First submit 60%

2. Second Submmit 94% 
Solved wired size problem, when define size as a static field inside class Node,do not work well. Size is 1 before any insert???

Solved the problem that insert calls too many time rect. The last version, create rect for everynode, even if they already exsit, waste lot of time. Change to a if (next = null) create a new rect, else copy the rect and pass to next level. The grade improve a lot by this.

Nearest is still a big problem.
