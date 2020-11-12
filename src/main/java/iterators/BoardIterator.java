package iterators;

import gameobjects.Bubble;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BoardIterator implements Iterator<Bubble> {

    private transient List<Bubble> bubbles;
    private transient int position = 0;


    public BoardIterator(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return bubbles.size() > position;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Bubble next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        position++;
        return bubbles.get(position - 1);
    }

    /**
     * Removes from the underlying collection the last element returned
     * by this iterator (optional operation).  This method can be called
     * only once per call to {@link #next}.  The behavior of an iterator
     * is unspecified if the underlying collection is modified while the
     * iteration is in progress in any way other than by calling this
     * method.
     *
     * @throws UnsupportedOperationException if the {@code remove}
     *                                       operation is not supported by this iterator
     * @throws IllegalStateException         if the {@code next} method has not
     *                                       yet been called, or the {@code remove} method has
     *                                       already been called after the last call to the
     *                                       {@code next} method
     * @implSpec The default implementation throws an instance of
     * {@link UnsupportedOperationException} and performs no other action.
     */
    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalThreadStateException();
        } else {
            bubbles.remove(position - 1);
        }
    }
}
