package com.funa2chile.app

import com.funa2chile.app.domain.validation.RutValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class RutValidatorTest {
    @Test fun rutValidoConFormato() = assertNull(RutValidator.validate("11.111.111-1"))
    @Test fun rutValidoSinFormato() = assertNull(RutValidator.validate("111111111"))
    @Test fun dvIncorrecto() = assertNotNull(RutValidator.validate("11.111.111-2"))
    @Test fun vacio() = assertNotNull(RutValidator.validate(""))
    @Test fun formatea() = assertEquals("11.111.111-1", RutValidator.formatear("111111111"))
    @Test fun enmascara() = assertEquals("11.111.***-1", RutValidator.enmascarar("111111111"))
}
