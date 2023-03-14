package org.example.acrastt.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * This is a thread-safe implementation of the {@link java.util.ArrayList} interface.
 *
 * @param <T> The type of the elements in this collection.
 * @author Bohan Du
 * @version 1.0.0
 * @see java.util.ArrayList
 * @since 1.0.0
 */
public class ConcurrentList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 5560587813010361548L;

    private static final String UNREACHABLE = "Reached an unreachable state while trying to ";

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentList.class);
    /**
     * A stamped lock that is used to synchronize access to the list.
     */
    private static final StampedLock lock = new StampedLock();

    /**
     * Creates a new ConcurrentList.
     *
     * @param initialCapacity The initial size/capacity of the list.
     */
    public ConcurrentList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a new ConcurrentList.
     */
    public ConcurrentList() {
        super();
    }

    /**
     * Creates a new ConcurrentList with elements of the specified collection.
     *
     * @param c The specified Collection.
     */
    public ConcurrentList(Collection<? extends T> c) {
        super(c);
    }

    /**
     * Runs a method with optimistic reading technique.
     *
     * @param c   the method to execute
     * @param <V> the type of the result
     * @return the result of the method
     */
    private <V> V optimisticRead(Callable<V> c) {
        // Gets the optimistic read lock
        long stamp = lock.tryOptimisticRead();
        // If there is no one writing, call the method. Otherwise, use normal read lock
        if (lock.validate(stamp)) {
            try {
                return c.call();
            } catch (Exception e) {
                LOG.error("Exception when trying to read optimistically", e);
            }
        } else {
            return read(c);
        }
        // This shouldn't happen
        throw new IllegalStateException(UNREACHABLE + "read optimistically");
    }

    /**
     * Runs a method with read lock technique.
     *
     * @param c   the method to execute
     * @param <V> the type of the result
     * @return the result of the method
     */
    private <V> V read(Callable<V> c) {
        // Gets the read lock
        long stamp = lock.readLock();
        // Calls the method and releases the lock
        try {
            return c.call();
        } catch (Exception e) {
            LOG.error("Exception while trying to read", e);
        } finally {
            lock.unlockRead(stamp);
        }
        // This shouldn't happen
        throw new IllegalStateException(UNREACHABLE + "read");
    }

    /**
     * Runs a method with write lock technique.
     *
     * @param c   the method to execute
     * @param <V> the type of the result
     * @return the result of the method
     */
    private <V> V write(Callable<V> c) {
        // Gets the write lock
        long stamp = lock.writeLock();
        // Calls the method and releases the lock
        try {
            return c.call();
        } catch (Exception e) {
            LOG.error("Exception while writing concurrently", e);
        } finally {
            lock.unlockWrite(stamp);
        }
        // This shouldn't happen
        throw new IllegalStateException(UNREACHABLE + "write");
    }

    /**
     * Runs a method without a result with write lock technique.
     *
     * @param r the method to execute
     */
    private void write(Runnable r) {
        // Gets the write lock
        long stamp = lock.writeLock();
        // Runs the method and releases the lock
        try {
            r.run();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Check if "o" has the same value as this ConcurrentList.
     *
     * @param o the object to be compared for equality with this list
     * @return true if the specified object is equal to this list. Otherwise false
     */
    @Override
    public boolean equals(Object o) {
        return optimisticRead(() -> super.equals(o));
    }

    /**
     * Returns the hash code for this ConcurrentList.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return optimisticRead(super::hashCode);
    }


    /**
     * Trims this ConcurrentList so that it contains no null elements(In its base list).
     */
    @Override
    public void trimToSize() {
        write(super::trimToSize);
    }

    /**
     * Ensures that this ConcurrentList can hold at least the specified number of elements without
     * increasing the base list
     *
     * @param minCapacity the desired minimum capacity
     */
    @Override
    public void ensureCapacity(int minCapacity) {
        write(() -> super.ensureCapacity(minCapacity));
    }

    /**
     * Removes the item with the specified index from this ConcurrentList.
     *
     * @param index the index of the element to be removed
     * @return the removed element
     */
    @Override
    public T remove(int index) {
        return write(() -> super.remove(index));
    }

    /**
     * Removes items from "fromList"(inclusive) to "toList"(exclusive) from this ConcurrentList.
     *
     * @param fromIndex index of the first element to be removed
     * @param toIndex   index after last element to be removed
     */
    @Override
    public void removeRange(int fromIndex, int toIndex) {
        write(() -> super.removeRange(fromIndex, toIndex));
    }

    /**
     * @return returns the String representation of this ConcurrentList
     */
    @Override
    public String toString() {
        return optimisticRead(super::toString);
    }


    /**
     * Returns the number of elements in this ConcurrentList.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return optimisticRead(super::size);
    }


    /**
     * Checks if this ConcurrentList is empty.
     *
     * @return true if this ConcurrentList is empty, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return optimisticRead(super::isEmpty);
    }

    /**
     * Checks if this ConcurrentList contains the specified element.
     *
     * @param o the element to be checked for containment in this list
     * @return true if this ConcurrentList contains the specified element, otherwise false
     */
    @Override
    public boolean contains(Object o) {
        return optimisticRead(() -> super.contains(o));
    }

    /**
     * Returns the Iterator for this ConcurrentList.
     *
     * @return the iterator
     * @see java.util.Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return optimisticRead(super::iterator);
    }

    /**
     * Make action to all elements in this ConcurrentList based on the given action.
     *
     * @param action the action that will be performed on each element
     * @see java.util.function.Consumer
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        write(() -> super.forEach(action));
    }

    /**
     * Returns this ConcurrentList in Object[] form.
     *
     * @return the list in Object[] form
     */
    @Override
    public Object[] toArray() {
        return optimisticRead(super::toArray);
    }

    /**
     * Returns this ConcurrentList in V[] form.
     *
     * @param <V> the type of the array elements
     * @param a   the array to be returned(Will make a copy), if the length of a is smaller than the
     *            size of this ConcurrentList, a new array will be created
     * @return the collection in V[] form
     */
    @Override
    public <V> V[] toArray(V[] a) {
        return optimisticRead(() -> super.toArray(a));
    }

    /**
     * Returns the V[] form of this ConcurrentList allocated using provided function.
     *
     * @param <V>       the type of the array elements
     * @param generator the function
     * @return the ConcurrentList in V[] form
     */
    @Override
    public <V> V[] toArray(IntFunction<V[]> generator) {
        return optimisticRead(() -> super.toArray(generator));
    }

    /**
     * Adds an element at the index provided, elements after the index will be pushed forward.
     *
     * @param index   the index
     * @param element the element to be added
     */
    @Override
    public void add(int index, T element) {
        write(() -> super.add(index, element));
    }

    /**
     * Add item t to the end of this ConcurrentList.
     *
     * @param t the element to be added
     * @return true
     */
    @Override
    public boolean add(T t) {
        return write(() -> super.add(t));
    }

    /**
     * Remove the first occurrence o from this ConcurrentList.
     *
     * @param o the element to be removed
     * @return true if the list contained the specified element, otherwise false
     */
    @Override
    public boolean remove(Object o) {
        return write(() -> super.remove(o));
    }

    /**
     * Check if this ConcurrentList contains all items from the specified collection.
     *
     * @param c the collection to check
     * @return true if this ConcurrentList contains all items from the specified collection, otherwise false
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return optimisticRead(() -> super.containsAll(c));
    }

    /**
     * Adds all items from the specified collection.
     *
     * @param c the collection to add
     * @return true if this ConcurrentList changed because of the call
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return write(() -> super.addAll(c));
    }

    /**
     * Adds all items from the specified collection at index.
     *
     * @param index the index the collection is to be added at
     * @param c     the collection to be added
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return write(() -> super.addAll(index, c));
    }

    /**
     * Remove all items specified in the collection.
     *
     * @param c the collection
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return write(() -> super.removeAll(c));
    }

    /**
     * Remove if the specified filter returns true for each element.
     *
     * @param filter the filter
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return write(() -> super.removeIf(filter));
    }

    /**
     * Removes all elements from this ConcurrentList that is not in the specified collection.
     *
     * @param c the collection
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return write(() -> super.retainAll(c));
    }

    /**
     * Replace all with the result of the operator for each item.
     *
     * @param operator the operator
     */
    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        write(() -> super.replaceAll(operator));
    }

    /**
     * Sorts this ConcurrentList with TimSort.
     *
     * @param c the comparator to compare elements
     */
    @Override
    public void sort(Comparator<? super T> c) {
        write(() -> super.sort(c));
    }

    /**
     * Removes all elements from this ConcurrentList.
     */
    @Override
    public void clear() {
        write(super::clear);
    }

    /**
     * Get an item at index.
     *
     * @param index the index
     * @return the item at index
     */
    @Override
    public T get(int index) {
        return optimisticRead(() -> super.get(index));
    }

    /**
     * Set the item at index to an element.
     *
     * @param index   the index
     * @param element the element
     * @return the item before setting the element at index
     */
    @Override
    public T set(int index, T element) {
        return write(() -> super.set(index, element));
    }

    /**
     * Returns the index of o's first occurrence.
     *
     * @param o the object to search for
     * @return the index of the first occurrence of o, -1 if o is not found
     */
    @Override
    public int indexOf(Object o) {
        return optimisticRead(() -> super.indexOf(o));
    }

    /**
     * Searches for the last occurrence of int.
     *
     * @param o the object to search for
     * @return the index of the last occurrence of o, -1 if o is not found
     */
    @Override
    public int lastIndexOf(Object o) {
        return optimisticRead(() -> super.lastIndexOf(o));
    }

    /**
     * Returns the list iterator of this ConcurrentList.
     *
     * @return the list iterator
     * @see java.util.ListIterator
     */
    @Override
    public ListIterator<T> listIterator() {
        return optimisticRead(super::listIterator);
    }

    /**
     * Returns the list iterator of this ConcurrentList starting at index(inclusive).
     *
     * @param index the index of the first element to be returned
     * @return the list iterator
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return optimisticRead(() -> super.listIterator(index));
    }

    /**
     * Returns the smaller list from fromIndex(inclusive) to toIndex(exclusive).
     *
     * @param fromIndex the first index to be returned
     * @param toIndex   the index after the last element to be returned
     * @return the smaller list
     */
    @Override
    public java.util.List<T> subList(int fromIndex, int toIndex) {
        return read(() -> super.subList(fromIndex, toIndex));
    }

    /**
     * Returns the spliterator of this ConcurrentList.
     *
     * @return the spliterator
     * @see java.util.Spliterator
     */
    @Override
    public Spliterator<T> spliterator() {
        return optimisticRead(super::spliterator);
    }

    /**
     * Returns the stream of this ConcurrentList.
     *
     * @return the stream
     * @see java.util.stream.Stream
     */
    @Override
    public Stream<T> stream() {
        return optimisticRead(super::stream);
    }

    /**
     * Returns the parallel stream of this ConcurrentList.
     *
     * @return the parallel stream
     * @see java.util.stream.Stream
     */
    @Override
    public Stream<T> parallelStream() {
        return read(super::parallelStream);
    }
}
