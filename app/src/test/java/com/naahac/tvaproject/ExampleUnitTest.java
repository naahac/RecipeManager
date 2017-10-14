package com.naahac.tvaproject;

import com.naahac.tvaproject.utils.RetrofitUtil;

import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testRetrofit() throws Exception {
        assertEquals(Retrofit.class, RetrofitUtil.getRetrofit(HttpLoggingInterceptor.Level.BASIC).getClass());
    }

}