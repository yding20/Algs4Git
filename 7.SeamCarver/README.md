1. Use energyMatrix as an instance varibale, distTo, edgeTo also. But it seems not a good way. Since we need to transpose both energyMatrix and the picture.
also ,fail to consider the case where only one column. In this case only relax center.

2. Changed energyMatrix to local variable. Every time transpose, we need transpose back, i do not find the way to solve consecutive call horizontal and only call transpose twice method. All the coner case is not that correct. refer to https://github.com/mingyueanyao/algorithms-princeton-coursera/blob/master/Codes%20of%20Programming%20Assignments/seam/SeamCarver.java to see all the corner case. Have not applied all the improvment(optimization) suggested by coursera check list.  
