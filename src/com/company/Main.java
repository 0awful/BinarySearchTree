package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SearchTreeManager tree = new SearchTreeManager(2);
        System.out.println(tree);
        tree.append(1);
        System.out.println(tree);
        tree.append(0);
        System.out.println(tree);
        tree.append(2);
        tree.append(1);
        tree.append(12);
        tree.append(3);
        tree.append(4);
        tree.append(6);
        System.out.println(tree);
        tree.append(4);
        System.out.println(tree);
        NodeItem node = tree.search(3);
        System.out.println(node);
        Comparable[] arr = tree.flatten();
        for (int i = 0; i< arr.length; i++){
            System.out.println(arr[i]);
        }

        SearchTreeManager newTree = new SearchTreeManager(arr);
        System.out.println(newTree);
    }

    /*

    Notes:

    despite not wanting to, I took the easy way out as far as redefining the root of the tree. I take a sorted array and
    use that to create a new optimized search tree. Assuming an optimized tree has the median value as the root of all
    intersections

    I was able to solve
        creation of a tree
        appending to the tree
        printing from the tree
        and creation from an array via recursive median calls
            (like the recursive call in the partitioning method of quicksort)

    I needed stack overflow to figure out how to use in-order tree traversal

    I did however create 200 lines of comments before this happened. Each detailing successive failures. Interestingly
    I had a similar framework and return value to the working solution, but it seemed unlikely that I would have arrived
    at the solution independent of external participation

    Things I'll look into for next week:
        How to properly mutate trees to have optimized roots (as in do it without creating an array)
        How to print nicely (breadth first search)
        How to merge two trees in an intelegent manner (is there a smart way of doing this? I'm skeptical
            Its not like unordered linked lists where you just attach them. )
        How often should you reoptimize the list?


     */
}
