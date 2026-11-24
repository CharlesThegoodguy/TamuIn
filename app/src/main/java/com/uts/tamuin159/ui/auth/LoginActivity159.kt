package com.uts.tamuin159.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.tamuin159.R
import com.uts.tamuin159.data.AppDatabase159
import com.uts.tamuin159.data.dao.UserDao159
import com.uts.tamuin159.databinding.ActivityLogin159Binding
import com.uts.tamuin159.ui.dashboard.DashboardActivity159
import com.uts.tamuin159.utils.SessionManager159
import kotlinx.coroutines.launch

class LoginActivity159 : AppCompatActivity() {

    private lateinit var binding159: ActivityLogin159Binding
    private lateinit var sessionManager159: SessionManager159
    private lateinit var userDao159: UserDao159

    override fun onCreate(savedInstanceState159: Bundle?) {
        super.onCreate(savedInstanceState159)

        sessionManager159 = SessionManager159(this)
        if (sessionManager159.isLoggedIn159()) {
            navigateToDashboard159()
            return
        }

        binding159 = ActivityLogin159Binding.inflate(layoutInflater)
        setContentView(binding159.root)

        val database159 = AppDatabase159.getDatabase159(this)
        userDao159 = database159.userDao159()

        setupListeners159()
    }

    private fun setupListeners159() {
        binding159.btnLogin159.setOnClickListener {
            performLogin159()
        }

        binding159.tvGoToRegister159.setOnClickListener {
            val intent159 = Intent(this, RegisterActivity159::class.java)
            startActivity(intent159)
        }
    }

    private fun performLogin159() {
        val username159 = binding159.etUsername159.text.toString().trim()
        val password159 = binding159.etPassword159.text.toString().trim()

        binding159.tilUsername159.error = null
        binding159.tilPassword159.error = null

        var isValid159 = true

        if (!com.uts.tamuin159.utils.ValidationHelper159.isValidUsername159(username159)) {
            binding159.tilUsername159.error = getString(R.string.err_empty_username_159)
            isValid159 = false
        }

        if (password159.isEmpty()) {
            binding159.tilPassword159.error = getString(R.string.err_empty_password_159)
            isValid159 = false
        }

        if (!isValid159) return

        lifecycleScope.launch {
            val user159 = userDao159.loginUser159(username159, password159)
            if (user159 != null) {
                sessionManager159.saveSession159(user159.id_159, user159.username_159)
                Toast.makeText(
                    this@LoginActivity159,
                    "Selamat datang, ${user159.username_159}!",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToDashboard159()
            } else {
                Toast.makeText(
                    this@LoginActivity159,
                    getString(R.string.err_login_failed_159),
                    Toast.LENGTH_SHORT
                ).show()
                binding159.tilPassword159.error = getString(R.string.err_login_failed_159)
            }
        }
    }

    private fun navigateToDashboard159() {
        val intent159 = Intent(this, DashboardActivity159::class.java)
        startActivity(intent159)
        finish()
    }
}
