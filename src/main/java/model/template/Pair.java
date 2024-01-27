package model.template;

/**
 * @param first Attributes
 */
public record Pair<T, U>(T first, U second) {
    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
