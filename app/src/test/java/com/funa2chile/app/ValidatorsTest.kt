package com.funa2chile.app

import com.funa2chile.app.domain.validation.ConfirmPasswordValidator
import com.funa2chile.app.domain.validation.EmailValidator
import com.funa2chile.app.domain.validation.NombreValidator
import com.funa2chile.app.domain.validation.PasswordValidator
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class ValidatorsTest {
    @Test fun correoValido() = assertNull(EmailValidator.validate("ana@correo.cl"))
    @Test fun correoInvalido() = assertNotNull(EmailValidator.validate("ana@correo"))
    @Test fun passwordSinMayuscula() = assertNotNull(PasswordValidator.validate("abcdefg1"))
    @Test fun passwordSinNumero() = assertNotNull(PasswordValidator.validate("Abcdefgh"))
    @Test fun passwordValida() = assertNull(PasswordValidator.validate("Abcdefg1"))
    @Test fun nombreConNumeros() = assertNotNull(NombreValidator.validate("Ana123"))
    @Test fun nombreValido() = assertNull(NombreValidator.validate("Ana Pérez"))
    @Test fun confirmacionDistinta() = assertNotNull(ConfirmPasswordValidator.validate("Abcdefg1", "Abcdefg2"))
    @Test fun confirmacionIgual() = assertNull(ConfirmPasswordValidator.validate("Abcdefg1", "Abcdefg1"))
}
