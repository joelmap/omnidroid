/*******************************************************************************
 * Copyright 2009 OmniDroid - http://code.google.com/p/omnidroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package edu.nyu.cs.omnidroid.model.db;

import android.database.Cursor;
import android.test.AndroidTestCase;

/**
 * Android Unit Test for {@link RegisteredEventAttributeDbAdapter} class.
 */
public class RegisteredEventAttributeDbAdapterTest extends AndroidTestCase {

  private RegisteredEventAttributeDbAdapter dbAdapter;
  private DbHelper omnidroidDbHelper;

  // Data for testing
  private String[] attributeNames = { "Attibute1", "Attibute2", "Attibute3" };
  private Long[] eventIDs = { Long.valueOf(11), Long.valueOf(22), Long.valueOf(33) };
  private Long[] dataTypeIDs = { Long.valueOf(1), Long.valueOf(2), Long.valueOf(3) };

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    omnidroidDbHelper = new DbHelper(this.getContext());
    dbAdapter = new RegisteredEventAttributeDbAdapter(omnidroidDbHelper.getWritableDatabase());
    dbAdapter.deleteAll();
  }

  @Override
  protected void tearDown() throws Exception {
    dbAdapter.deleteAll();
    omnidroidDbHelper.close();
    super.tearDown();
  }

  public void testPrecondition() {
    Cursor cursor = dbAdapter.fetchAll();
    assertEquals(cursor.getCount(), 0);
  }

  public void testInsert() {
    long id1 = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    assertTrue(id1 != -1);

    long id2 = dbAdapter.insert(attributeNames[1], eventIDs[1], dataTypeIDs[1]);
    assertTrue(id1 != id2);
  }

  public void testInsert_illegalArgumentException() {
    Exception expected = null;
    try {
      dbAdapter.insert(null, eventIDs[0], dataTypeIDs[0]);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);

    expected = null;
    try {
      dbAdapter.insert(attributeNames[0], null, dataTypeIDs[0]);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);

    try {
      dbAdapter.insert(attributeNames[0], eventIDs[0], null);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);
  }

  public void testFetch() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    Cursor cursor = dbAdapter.fetch(id);
    // Validate the inserted record.
    assertCursorEquals(cursor, attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    // Validate the record count
    assertEquals(dbAdapter.fetchAll().getCount(), 1);
  }

  public void testFetch_notExisting() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    Cursor cursor = dbAdapter.fetch(id + 1);
    assertEquals(cursor.getCount(), 0);
  }

  public void testFetch_illegalArgumentException() {
    Exception expected = null;
    try {
      dbAdapter.fetch(null);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);
  }

  public void testDelete() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    assertTrue(dbAdapter.delete(id));
    Cursor cursor = dbAdapter.fetch(id);
    assertEquals(cursor.getCount(), 0);
  }

  public void testDelete_notExisting() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    assertTrue(dbAdapter.delete(id));
    assertFalse(dbAdapter.delete(id));
  }

  public void testDelete_illegalArgumentException() {
    Exception expected = null;
    try {
      dbAdapter.delete(null);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);
  }

  public void testUpdate() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    assertTrue(dbAdapter.update(id, attributeNames[1], eventIDs[1], dataTypeIDs[1]));
    // Validate the updated record
    Cursor cursor = dbAdapter.fetch(id);
    assertCursorEquals(cursor, attributeNames[1], eventIDs[1], dataTypeIDs[1]);
  }

  public void testUpdate_notExisting() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    dbAdapter.delete(id);
    assertFalse(dbAdapter.update(id, attributeNames[1], eventIDs[1], dataTypeIDs[1]));
  }

  public void testUpdate_withNullValues() {
    long id = dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    assertFalse(dbAdapter.update(id, null, null, null));
    // Validate the updated record
    Cursor cursor = dbAdapter.fetch(id);
    assertCursorEquals(cursor, attributeNames[0], eventIDs[0], dataTypeIDs[0]);
  }

  public void testUpdate_illegalArgumentException() {
    Exception expected = null;
    try {
      dbAdapter.update(null, attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    } catch (IllegalArgumentException e) {
      expected = e;
    }
    assertNotNull(expected);
  }

  public void testFetchAll() {
    dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[0]);
    dbAdapter.insert(attributeNames[1], eventIDs[1], dataTypeIDs[1]);
    dbAdapter.insert(attributeNames[2], eventIDs[2], dataTypeIDs[2]);

    Cursor cursor = dbAdapter.fetchAll();
    assertEquals(cursor.getCount(), 3);
  }

  public void testFetchAll_withParameters() {
    dbAdapter.insert(attributeNames[0], eventIDs[0], dataTypeIDs[1]);
    dbAdapter.insert(attributeNames[1], eventIDs[1], dataTypeIDs[1]);
    dbAdapter.insert(attributeNames[2], eventIDs[2], dataTypeIDs[2]);

    assertTrue(dbAdapter.fetchAll(null, null, null).getCount() == 3);
    assertTrue(dbAdapter.fetchAll(null, null, dataTypeIDs[1]).getCount() == 2);
    assertTrue(dbAdapter.fetchAll(null, eventIDs[1], dataTypeIDs[1]).getCount() == 1);
    assertTrue(dbAdapter.fetchAll(attributeNames[0], null, null).getCount() == 1);
    assertTrue(dbAdapter.fetchAll(attributeNames[2], eventIDs[2], dataTypeIDs[1]).getCount() == 0);
  }

  /**
   * Helper method to assert a cursor pointing to a attribute record that matches the parameters
   */
  private void assertCursorEquals(Cursor cursor, String attributeName, Long eventID, 
      Long dataTypeID) {
    
    assertEquals(cursor.getString(cursor
        .getColumnIndex(RegisteredEventAttributeDbAdapter.KEY_EVENTATTRIBUTENAME)), attributeName);
    assertEquals(cursor
        .getInt(cursor.getColumnIndex(RegisteredEventAttributeDbAdapter.KEY_EVENTID)), eventID
        .intValue());
    assertEquals(cursor.getInt(cursor
        .getColumnIndex(RegisteredEventAttributeDbAdapter.KEY_DATATYPEID)), dataTypeID.intValue());
  }

}
