package com.example.common.mod.observer;


public class DatabaseManager {

    private Observable<Member, Observer<Member>> observables;
    private DatabaseManager() {
        observables = new Observable<>();
    }

    public static DatabaseManager getInstance() {
        return DatabaseHolder.instance;
    }

    public void insert(Member member) {
        /**
         * insert
         */
        observables.update(member);
    }

    public void register(Observer<Member> observer) {observables.register(observer);}
    public void unregister(Observer<Member> observer) {observables.unregister(observer);}



    static class DatabaseHolder {
        private static final DatabaseManager instance = new DatabaseManager();
    }

}
