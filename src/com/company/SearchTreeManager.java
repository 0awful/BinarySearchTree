package com.company;


/*

    Hard problems of a binary search tree:
      How do I print in a manner that makes sense?
                root
             node  node
           node   node node
        Or even
            node node node root node node node
        Both of these are really hard to do

        Why?

        If you do a "While root.left != null you can dive all the way down to your left most node,
            but you get them in the wrong order
            And you don't dive all the way down the right side of their items
            So recursion is probably the right answer

      How do I reassign the root?

        When you have one item the root is always perfect
        When you have two items the root is always perfect
        How do you change the root when it changes

        Being doubly linked, not triply linked the items are only related to their children,
        the children do not know the paraents. This is bad because of garbage collection
        Preliminary studies into garbage collection have told me that garbage collection gets rid of unreachable code

        if we have the tree below
              7
           4   8
         2  5   9
        1 3  6  10
        Where 7 is the root and the tree is very malformed we would like to make a change to a root of 5.
        Doing so niavely poses the risk of temporarily not having a reference to 7, which makes 7,8,9,10 unreachable
        This would then be collected. This does not even include the problem that losing this reference promises that you
        lose the entire tree.

        Moving to a root of 5

        we would like to have 2 or 3 and 8 become the left and right of 5.

        this allows for better future expansion.

                               5
                         3           8
                       2   4       7   9
                     1           6      10

        But how the fuck do it reason about this in code? Do I declaritively decide that the root is the middliest number?
        with 5 as the root
        2 or 3 is the middle of the numbers on the left
        8 is the middle of the number on the right
        Does this mean I am continuously running median calculations?
        That sounds like a bad way to solve the problem.
        So do I store the number of values below a node?
        That sounds like a worse way to solve the problem.
        So do I flatten the tree into an array then recreate it?
        That sounds like the wrong way to solve the problem.

 */

public class SearchTreeManager {
    private NodeItem root;
    private int size;

    public SearchTreeManager(NodeItem node){
        this.root = node;
        this.size = 1;
    }

    public SearchTreeManager(Comparable value){
        this.root = new NodeItem(value);
        this.size = 1;
    }

    public SearchTreeManager(Comparable[] array) {
        // assuming a sorted array, this adds every median recursively until every value is added to the array.


        // find median
        int median = findMedian(0, array.length-1);
        this.root = new NodeItem(array[median]);
        this.size = 1;
        // So from this we recursively add the median number of the array and then split the array into the two sides
        // of the median, which then add their median numbers and repeat

        addMedian(median, array.length-1, array);
        addMedian(0, median, array);

        // from this we take a sorted array and create a new ideally sorted tree.
        // which means we should check for sorted, but that's not really a part of this project
        // sorted checks are trivial.
    }

    private int findMedian(int initialIndex, int lastIndex){
        // we use this as a unified utility function
        return (lastIndex + initialIndex)/2;
    }

    private void addMedian(int initialIndex, int lastIndex, Comparable[] array){

        // here's the recursive add median function, with this we get the ability to turn our flatten function into a
        // new optimized search tree.

        // calculate the median
        int median = findMedian(initialIndex, lastIndex);

        // append that value
        this.append(array[median]);

        // next we check if we need to keep going to the left
        // if this evaluates to true we do
        if (median - initialIndex > 1) {
            // and we make this call
            addMedian(initialIndex, median, array);

        } else if (initialIndex == 0) {
            // if it evaluates to false, we have one case where we need to do this append
            this.append(array[initialIndex]);
        }


        // next we check if we need to keep going to the right
        // if this evaluates to true then we do
        if (lastIndex - median > 1) {
            // so make this call
            addMedian(median, lastIndex, array);
        } else if (lastIndex == array.length-1){
            // if we don't need to keep going, we handle the one edge case and do this append
            this.append(array[lastIndex]);
        }

    }

    public void append(NodeItem node){
        this.root.append(node);
        this.size ++;
    }

    public void append(Comparable value){
        this.root.append(new NodeItem(value));
        this.size ++;
    }

    public NodeItem search(Comparable value){
        // returns the node item with this value.

        return this.root.recursiveSearch(value);
    }

    public Comparable[] flatten(){
        // HOLY FUCK I THOUGHT TOSTRING WAS HARD!!
        Comparable[] array = new Comparable[this.size];

        this.root.recursiveFlattenIntoArray(array, 0);

        return array;
    }

    @Override
    public String toString() {
        // this was a really hard problem.
        // I don't like how it doesn't give information as to relations between the items
        // I would like the output to be a graph of the tree structure.
        // but that is a really hard problem to deal with for recursion. Because it implies that somehow the first node
        // knows how many items the furthest left item is and is able to apply to itself that amount of offset.
        // Some smarty has probably solved this problem. I'm really interested in how.

        return this.root.recursivePrint();
    }
}
