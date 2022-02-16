package com.geekbrains.myfirsttests

import junit.framework.TestCase.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PasswordComparatorTest {
  @Test
  fun passwordComparing_PasswordsMatch_ReturnsTrue() {
    assertTrue(PasswordComparator.comparePasswords("111", "111"))
  }

  @Test
  fun passwordComparing_PasswordsMismatch_ReturnsFalse() {
    assertFalse(PasswordComparator.comparePasswords("111", "222"))
  }

  @Test
  fun passwordComparing_PasswordEquals_ReturnsTrue() {
    PasswordComparator.setPasswords("111", "111")
    assertEquals(PasswordComparator.password, PasswordComparator.passwordToCompare)
  }

  @Test
  fun passwordComparing_PasswordNotEquals_ReturnsTrue() {
    PasswordComparator.setPasswords("111", "222")
    assertNotEquals(PasswordComparator.password,
      PasswordComparator.passwordToCompare)
  }

  @Test
  fun passwordComparing_PasswordIsNull_ReturnsTrue() {
    PasswordComparator.setPasswords(null, null)
    assertNull(PasswordComparator.password, PasswordComparator.passwordToCompare)
  }

  @Test
  fun passwordComparing_PasswordsNotNull_ReturnsTrue() {
    PasswordComparator.setPasswords("pass", "word")
    assertNotNull(PasswordComparator.password, PasswordComparator.passwordToCompare)
  }

  @Test
  fun passwordComparing_PasswordsSame_ReturnsTrue() {
    PasswordComparator.setPasswords("password", "password")
    assertSame(PasswordComparator.password, PasswordComparator.passwordToCompare)
  }

  @Test
  fun passwordComparing_ArraysEquals_ReturnsTrue() {
    PasswordComparator.setArrays(arrayOf(1, 2, 3, 4), arrayOf(1, 2, 3, 4))
    assertArrayEquals(PasswordComparator.array, PasswordComparator.arrayToCompare)
  }
}