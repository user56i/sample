package com.nixsolutions.cupboard;

import android.content.ContentResolver;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContentResolver;

import org.mockito.Mockito;

public class MockTest extends AndroidTestCase {


    public void testMocking(){
        Context mock = Mockito.mock(Context.class);

        Mockito.when(mock.getContentResolver()).thenReturn(new MockContentResolver());
        ContentResolver resolver = mock.getContentResolver();

        Mockito.verify(mock).getContentResolver();

    }
}
