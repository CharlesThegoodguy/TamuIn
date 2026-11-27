package com.uts.tamuin166.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.tamuin166.R
import com.uts.tamuin166.data.AppDatabase166
import com.uts.tamuin166.data.dao.UserDao166
import com.uts.tamuin166.databinding.ActivityLogin166Binding
import com.uts.tamuin166.ui.dashboard.DashboardActivity166
import com.uts.tamuin166.utils.SessionManager166
import kotlinx.coroutines.launch

class LoginActivity166 : AppCompatActivity() {

    private lateinit var binding166: ActivityLogin166Binding
    private lateinit var sessionManager166: SessionManager166
    private lateinit var userDao166: UserDao166

    override fun onCreate(savedInstanceState166: Bundle?) {
        super.onCreate(savedInstanceState166)

        sessionManager166 = SessionManager166(this)
        if (sessionManager166.isLoggedIn166()) {
            navigateToDashboard166()
            return
        }

        binding166 = ActivityLogin166Binding.inflate(layoutInflater)
        setContentView(binding166.root)

        val database166 = AppDatabase166.getDatabase166(this)
        userDao166 = database166.userDao166()

        setupListeners166()
    }

    private fun setupListeners166() {
        binding166.btnLogin166.setOnClickListener {
            performLogin166()
        }

        binding166.tvGoToRegister166.setOnClickListener {
            val intent166 = Intent(this, RegisterActivity166::class.java)
            startActivity(intent166)
        }
    }

    private fun performLogin166() {
        val username166 = binding166.etUsername166.text.toString().trim()
        val password166 = binding166.etPassword166.text.toString().trim()

        binding166.tilUsername166.error = null
        binding166.tilPassword166.error = null

        var isValid166 = true

        if (!com.uts.tamuin166.utils.ValidationHelper166.isValidUsername166(username166)) {
            binding166.tilUsername166.error = getString(R.string.err_empty_username_166)
            isValid166 = false
        }

        if (password166.isEmpty()) {
            binding166.tilPassword166.error = getString(R.string.err_empty_password_166)
            isValid166 = false
        }

        if (!isValid166) return

        lifecycleScope.launch {
            val user166 = userDao166.loginUser166(username166, password166)
            if (user166 != null) {
                sessionManager166.saveSession166(user166.id_166, user166.username_166)
                Toast.makeText(
                    this@LoginActivity166,
                    "Selamat datang, ${user166.username_166}!",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToDashboard166()
            } else {
                Toast.makeText(
                    this@LoginActivity166,
                    getString(R.string.err_login_failed_166),
                    Toast.LENGTH_SHORT
                ).show()
                binding166.tilPassword166.error = getString(R.string.err_login_failed_166)
            }
        }
    }

    private fun navigateToDashboard166() {
        val intent166 = Intent(this, DashboardActivity166::class.java)
        startActivity(intent166)
        finish()
    }
}
