package com.allmighty.budget.data

import java.security.MessageDigest
import java.security.SecureRandom
import android.util.Base64

object Security {
    private fun sha256(bytes: ByteArray) = MessageDigest.getInstance("SHA-256").digest(bytes)
    fun hashPassword(password: String, salt: ByteArray = SecureRandom().generateSeed(16)): String {
        val h = sha256(salt + password.toByteArray())
        return Base64.encodeToString(salt + h, Base64.NO_WRAP)
    }
    fun verifyPassword(raw: String, stored: String): Boolean {
        val all = Base64.decode(stored, Base64.NO_WRAP)
        val salt = all.copyOfRange(0,16)
        val hash = all.copyOfRange(16, all.size)
        val h = sha256(salt + raw.toByteArray())
        return h.contentEquals(hash)
    }
    fun hashPin(pin: String): String = hashPassword(pin)
    fun verifyPin(raw: String, stored: String?): Boolean = stored?.let { verifyPassword(raw, it) } ?: true
}