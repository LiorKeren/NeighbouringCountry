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

    private BehaviorSubject<String> itemEditTextOnTextChangedSubject;

    private BehaviorSubject<Class> startActivitySubject;

    //Subscribe a string
    public BehaviorSubject<Class> getStartActivitySubject() {
        return startActivitySubject;
    }

    //Subscribe a position when item click
    public BehaviorSubject<Country> getListItemClickSubject() {
        return behaviorSubject;
    }

    //Subscribe a string
    public BehaviorSubject<String> getOnTextChangedSubject() {
        return itemEditTextOnTextChangedSubject;
    }

    private BehaviorSubject<Object> behaviorSubjectObject;

    public BehaviorSubject<Object> getObjectSubject() {
        return behaviorSubjectObject;
    }


    @Inject
    public RxDataPass() {
        behaviorSubject
                = BehaviorSubject.create();
        itemEditTextOnTextChangedSubject
                = BehaviorSubject.create();
        behaviorSubjectObject
                = BehaviorSubject.create();
        startActivitySubject
                = BehaviorSubject.create();
    }
}
