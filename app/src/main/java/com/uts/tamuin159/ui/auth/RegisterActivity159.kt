package com.uts.tamuin159.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.tamuin159.R
import com.uts.tamuin159.data.AppDatabase159
import com.uts.tamuin159.data.dao.UserDao159
import com.uts.tamuin159.data.entity.UserEntity159
import com.uts.tamuin159.databinding.ActivityRegister159Binding
import kotlinx.coroutines.launch

class RegisterActivity159 : AppCompatActivity() {

    private lateinit var binding159: ActivityRegister159Binding
    private lateinit var userDao159: UserDao159

    override fun onCreate(savedInstanceState159: Bundle?) {
        super.onCreate(savedInstanceState159)
        binding159 = ActivityRegister159Binding.inflate(layoutInflater)
        setContentView(binding159.root)

        val database159 = AppDatabase159.getDatabase159(this)
        userDao159 = database159.userDao159()

        setupListeners159()
    }

    private fun setupListeners159() {
        binding159.btnRegister159.setOnClickListener {
            performRegister159()
        }

        binding159.tvBackToLogin159.setOnClickListener {
            finish()
        }
    }

    private fun performRegister159() {
        val username159 = binding159.etRegUsername159.text.toString().trim()
        val password159 = binding159.etRegPassword159.text.toString().trim()
        val confirmPassword159 = binding159.etRegConfirmPassword159.text.toString().trim()

        binding159.tilRegUsername159.error = null
        binding159.tilRegPassword159.error = null
        binding159.tilRegConfirmPassword159.error = null

        var isValid159 = true

        if (!com.uts.tamuin159.utils.ValidationHelper159.isValidUsername159(username159)) {
            binding159.tilRegUsername159.error = getString(R.string.err_empty_username_159)
            isValid159 = false
        }

        if (password159.isEmpty()) {
            binding159.tilRegPassword159.error = getString(R.string.err_empty_password_159)
            isValid159 = false
        } else if (!com.uts.tamuin159.utils.ValidationHelper159.isValidPassword159(password159)) {
            binding159.tilRegPassword159.error = getString(R.string.err_password_too_short_159)
            isValid159 = false
        }

        if (confirmPassword159.isEmpty()) {
            binding159.tilRegConfirmPassword159.error = getString(R.string.err_empty_password_159)
            isValid159 = false
        } else if (!com.uts.tamuin159.utils.ValidationHelper159.doPasswordsMatch159(password159, confirmPassword159)) {
            binding159.tilRegConfirmPassword159.error = getString(R.string.err_passwords_mismatch_159)
            isValid159 = false
        }

        if (!isValid159) return

        lifecycleScope.launch {
            val existingUser159 = userDao159.getUserByUsername159(username159)
            if (existingUser159 != null) {
                binding159.tilRegUsername159.error = getString(R.string.err_username_exists_159)
                Toast.makeText(
                    this@RegisterActivity159,
                    getString(R.string.err_username_exists_159),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            val newUser159 = UserEntity159(
                username_159 = username159,
                password_159 = password159
            )
            userDao159.registerUser159(newUser159)

            Toast.makeText(
                this@RegisterActivity159,
                getString(R.string.success_register_159),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}
