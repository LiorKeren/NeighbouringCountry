package com.liorkerenn.user.myapplication.viewmodel;

import com.liorkerenn.user.myapplication.helpers.Const;
import com.liorkerenn.user.myapplication.injection.DataApi;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.schedulers.Schedulers;
import static org.mockito.Mockito.when;

/**
 * A class for testing API calls
 * */
@RunWith(MockitoJUnitRunner.Silent.class)
public class DataApiTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Spy
    @InjectMocks
    DataApi dataApi;

    @Before
    public void setUp() throws Exception {
       MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    /*
    * Test Countries Api Call
    * */
    @Test
    public void getCurrenciesFromApi() {
        Map<String, String> queries = new HashMap<>();

        queries.put("base", Const.dataSetUrl.COUNTRIES_API_END_POINT);

        when(dataApi.getAPIService()
                .getAllCountries()
                .subscribeOn(Schedulers.io())
                .repeatWhen(completed -> completed.delay(1, TimeUnit.SECONDS))
                .observeOn(Schedulers.io())
        ) .thenCallRealMethod().getMock();

    }

}