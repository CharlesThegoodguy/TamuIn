package com.uts.tamuin166.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.tamuin166.R
import com.uts.tamuin166.data.AppDatabase166
import com.uts.tamuin166.data.dao.UserDao166
import com.uts.tamuin166.data.entity.UserEntity166
import com.uts.tamuin166.databinding.ActivityRegister166Binding
import kotlinx.coroutines.launch

class RegisterActivity166 : AppCompatActivity() {

    private lateinit var binding166: ActivityRegister166Binding
    private lateinit var userDao166: UserDao166

    override fun onCreate(savedInstanceState166: Bundle?) {
        super.onCreate(savedInstanceState166)
        binding166 = ActivityRegister166Binding.inflate(layoutInflater)
        setContentView(binding166.root)

        val database166 = AppDatabase166.getDatabase166(this)
        userDao166 = database166.userDao166()

        setupListeners166()
    }

    private fun setupListeners166() {
        binding166.btnRegister166.setOnClickListener {
            performRegister166()
        }

        binding166.tvBackToLogin166.setOnClickListener {
            finish()
        }
    }

    private fun performRegister166() {
        val username166 = binding166.etRegUsername166.text.toString().trim()
        val password166 = binding166.etRegPassword166.text.toString().trim()
        val confirmPassword166 = binding166.etRegConfirmPassword166.text.toString().trim()

        binding166.tilRegUsername166.error = null
        binding166.tilRegPassword166.error = null
        binding166.tilRegConfirmPassword166.error = null

        var isValid166 = true

        if (!com.uts.tamuin166.utils.ValidationHelper166.isValidUsername166(username166)) {
            binding166.tilRegUsername166.error = getString(R.string.err_empty_username_166)
            isValid166 = false
        }

        if (password166.isEmpty()) {
            binding166.tilRegPassword166.error = getString(R.string.err_empty_password_166)
            isValid166 = false
        } else if (!com.uts.tamuin166.utils.ValidationHelper166.isValidPassword166(password166)) {
            binding166.tilRegPassword166.error = getString(R.string.err_password_too_short_166)
            isValid166 = false
        }

        if (confirmPassword166.isEmpty()) {
            binding166.tilRegConfirmPassword166.error = getString(R.string.err_empty_password_166)
            isValid166 = false
        } else if (!com.uts.tamuin166.utils.ValidationHelper166.doPasswordsMatch166(password166, confirmPassword166)) {
            binding166.tilRegConfirmPassword166.error = getString(R.string.err_passwords_mismatch_166)
            isValid166 = false
        }

        if (!isValid166) return

        lifecycleScope.launch {
            val existingUser166 = userDao166.getUserByUsername166(username166)
            if (existingUser166 != null) {
                binding166.tilRegUsername166.error = getString(R.string.err_username_exists_166)
                Toast.makeText(
                    this@RegisterActivity166,
                    getString(R.string.err_username_exists_166),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            val newUser166 = UserEntity166(
                username_166 = username166,
                password_166 = password166
            )
            userDao166.registerUser166(newUser166)

            Toast.makeText(
                this@RegisterActivity166,
                getString(R.string.success_register_166),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}
