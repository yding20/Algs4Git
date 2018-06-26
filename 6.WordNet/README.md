1. First Commit, only the WordNet: the implementation is wrong, we need take in to account that one key can appear in different synset, for example :

62,Aberdeen,a town in western Washington  
63,Aberdeen,a town in northeastern South Dakota  
64,Aberdeen,a town in northeastern Maryland  
65,Aberdeen,a city in northeastern Scotland on the North Sea 

When we calculate the distance, we calcuate the min distance among all the synsets contain the noun, this is  the reason the instructor let us implement public int length(Iterable<Integer> v, Iterable<Integer> w) in SAP.java, So we need a data structure that regard the noun as the key and corresponding synset as a interable onject(ArrayList or Stack), so the plan is 1.create a graph 2. provide the synset corresponding to the key(noun), since we might need the number of vertice when create a graph, we may try 2 first. 
