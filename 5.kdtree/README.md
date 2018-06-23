1. First submit 60%

2. Second Submmit 94% 
Solved wired size problem, when define size as a static field inside class Node,do not work well. Size is 1 before any insert???

Solved the problem that insert calls too many time rect. The last version, create rect for everynode, even if they already exsit, waste lot of time. Change to a if (next = null) create a new rect, else copy the rect and pass to next level. The grade improve a lot by this.

Nearest is still a big problem. 

3. Second Submit: 100 %
The problem with last version is Nearest search sequence. Simple write two line: 			currentnode.lb = range(currentnode.lb, stack, rect);
			currentnode.rt = range(currentnode.rt, stack, rect);
is kind of brutal force method. It will search all the point in sequence. But cannot pass the time requirement.

The basic idea here is : when we want to go next level from currentnode, we compare cmp first, if we go to one direction according to cmp, we also check rect.distanceSquaredTo to see if the other node is worth search. If it is, we will use recursive method to search, if it is not, good, we save some time compared to last version. 
