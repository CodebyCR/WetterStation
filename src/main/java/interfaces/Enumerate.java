package interfaces;

@FunctionalInterface
public interface Enumerate<BiConsumer> {
    void enumerate(BiConsumer enumerator);
}

