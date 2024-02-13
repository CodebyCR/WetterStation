package model;

import interfaces.Enumerate;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Matrix <T> extends ArrayList<ArrayList<T>>
                        implements Enumerate<BiConsumer<Integer, ArrayList<T>>> {
    public Matrix() {
        super();
    }

    /**
     * Enumerate the matrix
     * @param enumerator - provides the index and the row for each matrix entry
     */
    @Override
    public void enumerate(BiConsumer<Integer, ArrayList<T>> enumerator) {
        this.forEach( (row) -> {
            final int index = this.indexOf(row);
            enumerator.accept(index, row);
        });
    }

    /**
     * Get the value at the specified row and column
     * @param row - row index
     * @param column - column index
     * @return the value at the specified row and column
     */
    public T getValueAt(final int row, final int column){
        return this.get(row).get(column);
    }

}
