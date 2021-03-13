package vn.com.taptap.store.arithmetic;

import java.util.List;

@FunctionalInterface
public interface SearchFunction<I, K, R> {
    void search(Integer pageId, List<I> data, K key, R result);
}