package org.demo.utils;

public sealed interface Optional2<T> permits Optional2.None, Optional2.Some {

    static <R> Optional2<R> of(R entity) {
        return new Some<>(entity);
    }

    static <R> Optional2<R> empty() {
        return new None<>();
    }

    T get();

    boolean isEmpty();

    final class Some<T> implements Optional2<T> {

        private final T entity;

        Some(T entity) {
            this.entity = entity;
        }

        @Override
        public T get() {
            return entity;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    final class None<T> implements Optional2<T> {

        @Override
        public T get() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

}
