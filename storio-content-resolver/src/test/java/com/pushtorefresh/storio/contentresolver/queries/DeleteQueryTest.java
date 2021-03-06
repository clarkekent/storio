package com.pushtorefresh.storio.contentresolver.queries;

import android.net.Uri;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DeleteQueryTest {

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldNotAllowNullUriObject() {
        DeleteQuery.builder()
                .uri((Uri) null) // LOL, via overload we disabled null uri without specifying Type!
                .build();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class) // Uri#parse() not mocked
    public void shouldNotAllowNullUriString() {
        DeleteQuery.builder()
                .uri((String) null)
                .build();
    }

    @Test
    public void whereClauseShouldNotBeNull() {
        DeleteQuery deleteQuery = DeleteQuery.builder()
                .uri(mock(Uri.class))
                .where(null)
                .build();

        assertEquals("", deleteQuery.where());
    }

    @Test
    public void whereArgsShouldNotBeNull() {
        DeleteQuery deleteQuery = DeleteQuery.builder()
                .uri(mock(Uri.class))
                .where("c1 = s")
                .build();

        assertNotNull(deleteQuery.whereArgs());
        assertTrue(deleteQuery.whereArgs().isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfWhereArgsSpecifiedWithoutWhereClause() {
        DeleteQuery.builder()
                .uri(mock(Uri.class))
                .whereArgs("someArg") // Without WHERE clause!
                .build();
    }

    @Test
    public void buildWithNormalValues() {
        final Uri uri = mock(Uri.class);
        final String where = "test_where";
        final Object[] whereArgs = {"arg1", "arg2", "arg3"};

        final DeleteQuery deleteQuery = DeleteQuery.builder()
                .uri(uri)
                .where(where)
                .whereArgs(whereArgs)
                .build();

        assertEquals(uri, deleteQuery.uri());
        assertEquals(where, deleteQuery.where());
        assertEquals(Arrays.asList(whereArgs), deleteQuery.whereArgs());
    }
}
