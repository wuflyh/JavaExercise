package tbd.example;

import java.io.Serializable;

/**
 * Utility pair class.
 * @param <T> Type of the two parts of the pair.
 */
public class Pair<T> implements Serializable, Comparable<Pair<T>> {

    private static final long serialVersionUID = 1L;

    public T first;
    public T second;

    /**
     * Construct pair
     *
     * @param first First object of pair.
     * @param second Second object of pair.
     */
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Override the toString method for the Pair.
     *
     * @return The stringified pair.
     */
    @Override
    public String toString() {
        return String.format("Pair<%s,%s>", first, second);
    }

    /**
     * Override the hash code method for the Pair.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    /**
     * Override equals method.
     *
     * @param obj The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Pair<?> other = (Pair<?>) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }

    /**
     * Override the compareTo method.
     *
     * @param o Object to compare to.
     * @return -1, 0, or 1, as required for the super.compareTo method.
     */
    @Override
    public int compareTo(Pair<T> o) {
        int c = compare(first, o.first);
        return (c == 0) ? compare(second, o.second) : c;
    }

    /* support function */
    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) {
        if (a == null || b == null) {
            if (a == null && b == null) {
                return 0;
            } else if (a == null) {
                return 1;
            } else {
                return -1;
            }
        }
        return ((Comparable<Object>) b).compareTo(a);
    }

}