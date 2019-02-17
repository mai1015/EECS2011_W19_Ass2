/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2
 * Section: Z
 * Student Name: Zhili Mai
 * Student eecs account:  mai1015
 * Student ID number:  215234842
 **********************************************************/

import java.util.Stack;

public class NewDeque<E> {
    /**
     * Front stack
     */
    private Stack<E> f;
    /**
     * Back stack
     */
    private Stack<E> r;

    /**
     * Creates an empty Deque.
     */
    public NewDeque() {
        f = new Stack<>();
        r = new Stack<>();
    }

    /**
     * Returns the number of elements in this Deque.
     *
     * @return  the number of elements in this Deque
     */
    public int size() {
        return f.size() + r.size();
    }

    /**
     * Tests if this Deque has no element.
     *
     * @return  {@code true} if and only if both stack
     *          no element, that is, its size is zero;
     *          {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Private stack helper method. try to get to the
     * other end of stack if a stack is empty
     *
     * @param a main stack
     * @param b other size stack
     * @return {@code null} if and only if both stack
     *         are empty, that is, both size is zero;
     *         {@code Item} other wise
     */
    private E moveStack(Stack<E> a, Stack<E> b) {
        if (!a.isEmpty()) {
            return a.pop();
        } else if (!b.isEmpty()) {
            while (b.size() > 1) {
                a.push(b.pop());
            }
            return b.pop();
        }
        return null;
    }

    /**
     * Looks at the object at the first item of this deque
     * without removing it from the deque.
     *
     * @return  {@code item}  the first item of this deque
     *          (the top item of the front <tt>Stack</tt>
     *          object).
     *          {@code null} if the deque is empty
     */
    public E first() {
        E e = moveStack(f, r);
        f.add(e);
        return e;
    }


    /**
     * Looks at the object at the last item of this deque
     * without removing it from the deque.
     *
     * @return  {@code item} the last item of this deque
     *          (the top item of the front <tt>Stack</tt>
     *          object).
     *          {@code null} if the deque is empty
     */
    public E last() {
        E e = moveStack(r, f);
        r.add(e);
        return e;
    }

    /**
     * Add an item to front of the deque.
     *
     * @param   e   the item to be add onto this deque.
     */
    public void addFirst(E e) {
        f.push(e);
    }

    /**
     * Add an item to back of the deque.
     *
     * @param   e   the item to be add onto this deque.
     */
    public void addLast(E e) {
        r.push(e);
    }

    /**
     * Removes the object at the front of this deque
     * and returns that object as the value of this function.
     *
     * @return  {@code item} The object at the front of this deque
     *          (the first item of the <tt>Deque</tt> object).
     *          {@code null} if the deque is empty
     */
    public E removeFirst() {
        return moveStack(f, r);
    }

    /**
     * Removes the object at the back of this deque
     * and returns that object as the value of this function.
     *
     * @return  {@code item} The object at the front of this deque
     *          (the first item of the <tt>Deque</tt> object).
     *          {@code null} if the deque is empty
     */
    public E removeLast() {
        return moveStack(r, f);
    }

    /**
     * Returns a string representation of this Vector, containing
     * the String representation of each element.
     */
    @Override
    public String toString() {
        return String.format("Front: %s; Back: %s", f.toString(), r.toString());
    }

    public static void main(String args[]) {
        NewDeque<Integer> deque = new NewDeque<>();
        deque.addLast(5);
        deque.addFirst(3);
        deque.addFirst(7);

        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("isEmpty(): %b\n", deque.isEmpty());
        System.out.printf("first(): %d\n", deque.first());
        System.out.printf("removeLast(): %d\n", deque.removeLast());
        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("size(): %d\n", deque.size());
        System.out.printf("removeLast(): %d\n", deque.removeLast());
        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("removeFirst(): %d\n", deque.removeFirst());
        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("isEmpty(): %b\n", deque.isEmpty());
        System.out.printf("removeFirst(): %d\n", deque.removeFirst());
        System.out.println("Current Stack: " + deque.toString());
        deque.addFirst(6);
        System.out.println("addFirst(6)");
        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("last(): %d\n", deque.last());
        deque.addFirst(8);
        System.out.println("addFirst(8)");
        System.out.println("Current Stack: " + deque.toString());
        System.out.printf("isEmpty(): %b\n", deque.isEmpty());
        System.out.printf("last(): %d\n", deque.last());
        System.out.println("Current Stack: " + deque.toString());
    }
}
