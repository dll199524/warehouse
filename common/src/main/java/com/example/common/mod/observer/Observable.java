package com.example.common.mod.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 */
public class Observable<M, T extends Observer<M>> {
    private List<T> observables;

    public Observable() {
        observables = new ArrayList<>(8);
    }

    public void register(T observer) {observables.add(observer);}
    public void unregister(T observer) {observables.remove(observer);}

    public void update(M m) {
        for (T observer : observables) {
            observer.update(m);
        }
    }

}
