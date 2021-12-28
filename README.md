# FindBinOp
General research on a concept I created called Binary Operator Network denoted BON
A Binary Operator Network is a neural network along with a set of biases which together can represent the space of all binary operations
In this case binary means 0 and 1 as well as taking 2 inputs

In mathamatical notation the set of binary numbers is {0,1} and I will denote that set as B.
The space of all binary operations will be denoted as F: BxB -> B
I will call members of F Tasks.

F was chosen because of the importants of the XOR gate which is not linearly seperable.

The domain of F is BxB which has size 4 { {0,0}, {0,1}, {1,0} {1,1} }
A member of F can be represented by its image, which takes form B^4, and can be computed using IM(BON, N) where N is the index for the task.
Because of this fact F has 16 different members which corresespond to different binary gates/operations.

Because of the nature of Neural Networks I define the value of the largest error of a task as lower case e.
I choose the value e as 0.04 to be when the BON is converged, and for all intents and purposes an outputs of >0.98 and <0.02 are 1 and 0 respectivly.

The motivation for this is to introduce a method for Bias Only Training.
Bias Only Training is desireable because Backpropagation follows the same computations structure as Forward Pass.
This would decrease the amount of computation needed because the weights no longer need to be updated and thus gradients dont need to be computed.

A networks with dimensions { N1, N2, N3, N4, ... } will have need to update N1x(N2+1) + N2x(N3+1) + N3x(N4+1)... parameter updates.
whereas Bias Only Training will only need N1 + N2 + N3 + N4... parameter updates.
