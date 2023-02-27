package utils;


import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ConcurrentList<T> extends ArrayList<T> implements java.util.List<T>, Serializable {

    private final StampedLock lock = new StampedLock();

    public ConcurrentList(int initialCapacity) {
        super(initialCapacity);
    }

    public ConcurrentList() {
        super();
    }

    public ConcurrentList(Collection<? extends T> c) {
        super(c);
    }

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


    @Override
    public void trimToSize() {
        long stamp = lock.writeLock();
        try {
            super.trimToSize();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public void ensureCapacity(int minCapacity) {
        long stamp = lock.writeLock();
        try {
            super.ensureCapacity(minCapacity);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public T remove(int index) {
        long stamp = lock.writeLock();
        try {
            return super.remove(index);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        long stamp = lock.writeLock();
        try {
            super.removeRange(fromIndex, toIndex);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

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

    @Override
    public void add(int index, T element) {
        long stamp = lock.writeLock();
        try {
            super.add(index, element);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean add(T t) {
        long stamp = lock.writeLock();
        try {
            return super.add(t);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean remove(Object o) {
        long stamp = lock.writeLock();
        try {
            return super.remove(o);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

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

    @Override
    public boolean addAll(Collection<? extends T> c) {
        long stamp = lock.writeLock();
        try {
            return super.addAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        long stamp = lock.writeLock();
        try {
            return super.addAll(index, c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        long stamp = lock.writeLock();
        try {
            return super.removeAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        long stamp = lock.writeLock();
        try {
            return super.removeIf(filter);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        long stamp = lock.writeLock();
        try {
            return super.retainAll(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        long stamp = lock.writeLock();
        try {
            super.replaceAll(operator);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        long stamp = lock.writeLock();
        try {
            super.sort(c);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public void clear() {
        long stamp = lock.writeLock();
        try {
            super.clear();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

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

    @Override
    public T set(int index, T element) {
        long stamp = lock.writeLock();
        try {
            return super.set(index, element);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

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