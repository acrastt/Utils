package utils;


import java.io.Serializable;
import java.util.*;
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
 * @version 1.0
 * @see java.util.ArrayList
 */
public class ConcurrentList<T> extends ArrayList<T> implements java.util.List<T>, Serializable {

    private final StampedLock lock = new StampedLock();

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
     * Check if "o" has the same value as this ConcurrentList.
     *
     * @param o the object to be compared for equality with this list
     * @return true if the specified object is equal to this list. Otherwise false
     */
    @Override
    public boolean equals(Object o) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.equals(o);
        } else {
            stamp = lock.readLock();
            try {
                return super.equals(o);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the hash code for this ConcurrentList.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.hashCode();
        } else {
            stamp = lock.readLock();
            try {
                return super.hashCode();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }


    /**
     * Trims this ConcurrentList so that it contains no null elements(In its base list).
     */
    @Override
    public void trimToSize() {
        long stamp = lock.writeLock();
        try {
            super.trimToSize();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Ensures that this ConcurrentList can hold at least the specified
     * number of elements without increasing the base list
     *
     * @param minCapacity the desired minimum capacity
     */
    @Override
    public void ensureCapacity(int minCapacity) {
        long stamp = lock.writeLock();
        try {
            super.ensureCapacity(minCapacity);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Removes the item with the specified index from this ConcurrentList.
     *
     * @param index the index of the element to be removed
     * @return the removed element
     */
    @Override
    public T remove(int index) {
        long stamp = lock.writeLock();
        try {
            return super.remove(index);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Removes items from "fromList"(inclusive) to "toList"(exclusive) from this ConcurrentList.
     *
     * @param fromIndex index of first element to be removed
     * @param toIndex   index after last element to be removed
     */
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        long stamp = lock.writeLock();
        try {
            super.removeRange(fromIndex, toIndex);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * @return returns the String representation of this ConcurrentList
     */
    @Override
    public String toString() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.toString();
        } else {
            stamp = lock.readLock();
            try {
                return super.toString();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }


    /**
     * Returns the number of elements in this ConcurrentList.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.size();
        } else {
            stamp = lock.readLock();
            try {
                return super.size();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }


    /**
     * Checks if this ConcurrentList is empty.
     *
     * @return true if this ConcurrentList is empty, otherwise false
     */
    @Override
    public boolean isEmpty() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.isEmpty();
        } else {
            stamp = lock.readLock();
            try {
                return super.isEmpty();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Checks if this ConcurrentList contains the specified element.
     *
     * @param o the element to be checked for containment in this list
     * @return true if this ConcurrentList contains the specified element, otherwise false
     */
    @Override
    public boolean contains(Object o) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.contains(o);
        } else {
            stamp = lock.readLock();
            try {
                return super.contains(o);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the Iterator for this ConcurrentList.
     *
     * @return the iterator
     * @see java.util.Iterator
     */
    @Override
    public Iterator<T> iterator() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.iterator();
        } else {
            stamp = lock.readLock();
            try {
                return super.iterator();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Make action to all elements in this ConcurrentList based on the given action.
     *
     * @param action the action that will be performed on each element
     * @see java.util.function.Consumer
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            super.forEach(action);
        } else {
            stamp = lock.readLock();
            try {
                super.forEach(action);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns this ConcurrentList in Object[] form.
     *
     * @return the list in Object[] form
     */
    @Override
    public Object[] toArray() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.toArray();
        } else {
            stamp = lock.readLock();
            try {
                return super.toArray();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns this ConcurrentList in V[] form.
     *
     * @param <V> the type of the array elements
     * @param a   the array to be returned(Will make a copy), if length of a is
     *            smaller than the size of this ConcurrentList, a new array will be created
     * @return the collection in V[] form
     */
    @Override
    public <V> V[] toArray(V[] a) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.toArray(a);
        } else {
            stamp = lock.readLock();
            try {
                return super.toArray(a);
            } finally {
                lock.unlockRead(stamp);
            }
        }
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
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.toArray(generator);
        } else {
            stamp = lock.readLock();
            try {
                return super.toArray(generator);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Adds element at the index provided, elements after index will be pushed forward.
     *
     * @param index   the index
     * @param element the element to be added
     */
    @Override
    public void add(int index, T element) {
        long stamp = lock.writeLock();
        try {
            super.add(index, element);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Add item t to the end of this ConcurrentList.
     *
     * @param t the element to be added
     * @return true
     */
    @Override
    public boolean add(T t) {
        long stamp = lock.writeLock();
        try {
            return super.add(t);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Remove the first occurrence o from this ConcurrentList.
     *
     * @param o the element to be removed
     * @return true if the list contained the specified element, otherwise false
     */
    @Override
    public boolean remove(Object o) {
        long stamp = lock.writeLock();
        try {
            return super.remove(o);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Check if this ConcurrentList contains all items from the specified collection.
     *
     * @param c the collection to check
     * @return true if this ConcurrentList contains all items from the specified collection, false otherwise
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.containsAll(c);
        } else {
            stamp = lock.readLock();
            try {
                return super.containsAll(c);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Adds all items from the specified collection.
     *
     * @param c the collection to add
     * @return true if this ConcurrentList changed because of the call
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        long stamp = lock.writeLock();
        try {
            return super.addAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
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
        long stamp = lock.writeLock();
        try {
            return super.addAll(index, c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Remove all items specified in the collection.
     *
     * @param c the collection
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        long stamp = lock.writeLock();
        try {
            return super.removeAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Remove if the specified filter returns true for each element.
     *
     * @param filter the filter
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        long stamp = lock.writeLock();
        try {
            return super.removeIf(filter);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Removes all elements from this ConcurrentList that is not in the specified collection.
     *
     * @param c the collection
     * @return true if this ConcurrentList changed because of the call, otherwise false
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        long stamp = lock.writeLock();
        try {
            return super.retainAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Replace all with the result of the operator for each item.
     *
     * @param operator the operator
     */
    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        long stamp = lock.writeLock();
        try {
            super.replaceAll(operator);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Sorts this ConcurrentList with
     * <a href="https://www.geeksforgeeks.org/timsort/#:~:text=TimSort%20is%20a%20sorting%20algorithm,
     * a%20merge%20of%20merge%20sort">TimSort</a>.
     *
     * @param c the comparator to compare elements
     */
    @Override
    public void sort(Comparator<? super T> c) {
        long stamp = lock.writeLock();
        try {
            super.sort(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Removes all elements from this ConcurrentList.
     */
    @Override
    public void clear() {
        long stamp = lock.writeLock();
        try {
            super.clear();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Get an item at index.
     *
     * @param index the index
     * @return the item at index
     */
    @Override
    public T get(int index) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.get(index);
        } else {
            stamp = lock.readLock();
            try {
                return super.get(index);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Set the item at index to element.
     *
     * @param index   the index
     * @param element the element
     * @return the item before setting the element at index
     */
    @Override
    public T set(int index, T element) {
        long stamp = lock.writeLock();
        try {
            return super.set(index, element);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Returns the index of the first occurrence of o.
     *
     * @param o the object to search for
     * @return the index of the first occurrence of o, -1 if o is not found
     */
    @Override
    public int indexOf(Object o) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.indexOf(o);
        } else {
            stamp = lock.readLock();
            try {
                return super.indexOf(o);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Searches for the last occurrence of int.
     *
     * @param o the object to search for
     * @return the index of the last occurrence of o, -1 if o is not found
     */
    @Override
    public int lastIndexOf(Object o) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.lastIndexOf(o);
        } else {
            stamp = lock.readLock();
            try {
                return super.lastIndexOf(o);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the list iterator of this ConcurrentList.
     *
     * @return the list iterator
     * @see java.util.ListIterator
     */
    @Override
    public ListIterator<T> listIterator() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.listIterator();
        } else {
            stamp = lock.readLock();
            try {
                return super.listIterator();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the list iterator of this ConcurrentList starting at index(inclusive).
     *
     * @param index the index of the first element to be returned
     * @return the list iterator
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.listIterator(index);
        } else {
            stamp = lock.readLock();
            try {
                return super.listIterator(index);
            } finally {
                lock.unlockRead(stamp);
            }
        }
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
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.subList(fromIndex, toIndex);
        } else {
            stamp = lock.readLock();
            try {
                return super.subList(fromIndex, toIndex);
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the spliterator of this ConcurrentList.
     *
     * @return the spliterator
     * @see java.util.Spliterator
     */
    @Override
    public Spliterator<T> spliterator() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.spliterator();
        } else {
            stamp = lock.readLock();
            try {
                return super.spliterator();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the stream of this ConcurrentList.
     *
     * @return the stream
     * @see java.util.stream.Stream
     */
    @Override
    public Stream<T> stream() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.stream();
        } else {
            stamp = lock.readLock();
            try {
                return super.stream();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Returns the parallel stream of this ConcurrentList.
     *
     * @return the parallel stream
     * @see java.util.stream.Stream
     */
    @Override
    public Stream<T> parallelStream() {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return super.parallelStream();
        } else {
            stamp = lock.readLock();
            try {
                return super.parallelStream();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }
}