package com.liorkerenn.user.myapplication.rx;

import com.liorkerenn.user.myapplication.model.Country;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Class that pass data between components
 * */
@Singleton
public class RxDataPass {


    private BehaviorSubject<Country> behaviorSubject;

    private BehaviorSubject<Class> startActivitySubject;

    //Subscribe a string
    public BehaviorSubject<Class> getStartActivitySubject() {
        return startActivitySubject;
    }

    //Subscribe a position when item click
    public BehaviorSubject<Country> getListItemClickSubject() {
        return behaviorSubject;
    }

    @Inject
    public RxDataPass() {
        behaviorSubject
                = BehaviorSubject.create();
        startActivitySubject
                = BehaviorSubject.create();
    }
}
