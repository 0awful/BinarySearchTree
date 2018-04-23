package com.company;

public class NodeItem {
    public Comparable value;
    public NodeItem left;
    public NodeItem right;

    public NodeItem(Comparable value) {
        this.value = value;
    }

    public void setLeft(NodeItem left) {
        this.left = left;
    }

    public void setRight(NodeItem right){
        this.right = right;
    }

    public void append(NodeItem node){


        if (node.value.compareTo(this.value) > 0){

            if (this.right != null) {
                this.right.append(node);
            } else {
                this.setRight(node);
            }

        } else if (node.value.compareTo(this.value) < 0 ){

            if (this.left != null) {
                this.left.append(node);
            } else {
                this.setLeft(node);
            }

        } else if (node.value.compareTo(this.value) == 0) {

            if (this.left == null) {
                this.setLeft(node);
            } else if (this.right == null) {
                this.setRight(node);
            } else {
                this.right.append(node);
            }

        }



    }

    public NodeItem recursiveSearch(Comparable value) {
        if (value.compareTo(this.value) > 0 && this.right != null) {
            return this.right.recursiveSearch(value);
        } else if (value.compareTo(this.value) < 0 && this.left != null) {
            return this.left.recursiveSearch(value);
        }
        return this;
    }


    @Override
    public String toString() {
        return this.value.toString();
    }

    public int recursiveFlattenIntoArray(Comparable[] array, int index){
        // HOLY FUCK I THOUGHT TOSTRING WAS HARD...
        // Niavely I assumed I could use the same methodology as toString in this area. To an extent that might be true
        // Obviously we still have four cases.
        // Case one: this node has two children
        // Case two: this node has one child, it is on the right
        // Case three: this node has one child, it is on the left
        // Case four: this node has no children

        // in the case of toString the case 4 situation brought everything together. It is the only case that returns an
        // absolute value. In this case we are trying to find the relative value of these nodes within the context of one
        // another. The manager has no idea where the root lies in this example. Which means we are trying to assemble
        // from the outside in. Perhaps it would make sense to track the left and rightmost nodes because of this.

        // the problem is the evaluation of index locations. We have two easy cases, 0 and Length-1
        // The rest are a bother.
        // if the left of a node is the leftmost node. this node is is index 1 right?
        // actually no, because the leftmost node can have a right node.

        // {leftmost node, it's child/parent, it's parent's right node or its parent....}
        // This problem sucks.

        // Perhaps I should be tracking parents. Because in this case a parent node would be very very helpful.

//        int i = 0;
//        NodeItem node = this;
//        while (node.left != null) {
//            node = node.left;
//        }
//        array[i] = node.value;
//        i++;
//        if (node.right != null) {
//            array[i] = node.right.value;
//            i++;
//        }
//
        // so looking at this I know this is probably the wrong way to solve the problem, but it lets me know I need to
        // track an index. Perhaps if I find a way to write recursivePrint while tracking an index I can come to a better
        // conclusion.

        // this means that the returnValue should actually be an index! because if we have a returned index the functions
        // higher up the stack can know where they assign their value.

        // we also benefit from reference types because that allows us to pass updates without having to worry about
        // returning them.

        // looking at case 4, we can find either the rightmost or leftmost value and assign the index of 0 or length-1
        // then return the index of 0 ++ or length-1 --, which allows for other higher stack items to place their items
        // in the apropriate spot


        // but fuck what about the case leftmost item with a right node or rightmost item with a left node.

        // I already hate this data structure

        // does this solution require the existence of a parent connection?? We could also track right and leftmost and
        // build from one of those nodes. Starting from the root is really really hard.

        // Another point, are we assuming an optimized tree or not. If we are we can assume root == median else we can't
        // if we assume root == median, then we can actually reason about the initial index, if we are passing that

        // Another note. Is it even a reasonable problem to convert a tree into an array, rather than into a linked list?
        // Am I being dumb doing it this way?
//        if (this.left == null && this.right == null) {
//            array[index] = this.value;
//            return index;
//        } else if (this.right == null) {
//            int newIndex = this.left.recursiveFlattenIntoArray(array,index - 1 );
//            array[newIndex] = this.value;
//            return newIndex;
//        } else if (this.left == null) {
//            int newIndex = this.right.recursiveFlattenIntoArray(array,index + 1);
//            array[newIndex] = this.value;
//            return newIndex;
//        }
//        array[index] = this.value;
//        this.right.recursiveFlattenIntoArray(array, index + 1);
//        this.left.recursiveFlattenIntoArray(array,index - 1);
//
//        return index;

        // Index out of bounds...

        // Well I've discovered another wrong way to do this. Why is this so hard!

        // After spending from 11 am to 3pm on this problem I turned to stack overflow

        // Stack overflow provides the following recursive solution:
//        int fill_array(Node root, int [] array, int pos) {
//            if (root.left != null) {
//                pos = fill_array(root.left, array, pos);
//            }
//            array[pos++] = root.element;
//            if (root.right != null) {
//                pos = fill_array(root.right, array, pos);
//            }
//            return pos; // return the last position filled in by this invocation
//        }
        // https://stackoverflow.com/questions/16180569/turning-a-binary-tree-to-a-sorted-array
        // it holds a reference to https://en.wikipedia.org/wiki/Tree_traversal#In-order

        // So how this works is you seek out the leftmost value. You put the value of that node in the array
        // then you check if it has a right value, if it does you traverse that path.
        // you traverse the path to the right of the leftmost value as long as you can, increasing the position value as
        // you do. (tracking an index was a part of the solution)
        //

        // as the values return you move up the tree back to the root and back down again filling out the array
        // Here's a couple pieces of weirdness I don't like/get
        // why the fuck would you call array[pos++] first things first that is needlessly confusing.
        // Why don't you just perform one operation at a time? Much clearer. Stupid senior devs.

        // next I had some confusion about why it only waits for the left to return before it adds it's value to the
        // array. This makes sense though, because after your left has returned its your turn to add a value. If you don't
        // have a left value, you are the leftmost of your branch and you need to return.

        // Next I had some confusion about why it returns its position so late
        // obviously it had to happen last, because execution halts on return, but why is the value changing when it's
        // right is called

        // imagine

        //     5
        //  2
        // 1 3
        //    4

        // when 2 returns it needs to return the position changes that occured by the actions of 3 and 4. so it makes
        // sense that it waits for the value of right to return before it returns and it needs the pos to be equal to
        // what the rightmost value equals in order for it to work.

        // given all that I'll now go and solve the problem within my context

        if (this.left != null) {
            index = this.left.recursiveFlattenIntoArray(array, index);
        }
        array[index] = this.value;
        index ++;
        if (this.right != null) {
            index = this.right.recursiveFlattenIntoArray(array, index);
        }
        return index;
    }

    public String recursivePrint() {
        if (this.left != null && this.right != null) {
            return this.left.recursivePrint() + " " + this.toString() + " " + this.right.recursivePrint();
        } else if (this.right != null) {
            return this.toString() + " " + this.right.recursivePrint();
        } else if (this.left != null) {
            return this.left.recursivePrint() + " " + this.toString();
        }
        return this.toString();
    }
}
