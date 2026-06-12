package com.turkcell.lyraapp.data.auth

import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String): Result<Unit> {
        delay(1_000)
        return if (phoneNumber.isNotBlank() && password.length >= 6) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Hatalı numara veya şifre"))
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
    ): Result<Unit> {
        delay(1_500)
        return if (firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Kayıt sırasında bir hata oluştu"))
        }
    }
}
