package com.uts.tamuin159.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.tamuin159.R
import com.uts.tamuin159.data.AppDatabase159
import com.uts.tamuin159.data.dao.GuestDao159
import com.uts.tamuin159.data.entity.GuestEntity159
import com.uts.tamuin159.databinding.ActivityDashboard159Binding
import com.uts.tamuin159.ui.auth.LoginActivity159
import com.uts.tamuin159.ui.form.GuestFormActivity159
import com.uts.tamuin159.utils.DateHelper159
import com.uts.tamuin159.utils.SessionManager159
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardActivity159 : AppCompatActivity() {

    private lateinit var binding159: ActivityDashboard159Binding
    private lateinit var sessionManager159: SessionManager159
    private lateinit var guestDao159: GuestDao159
    private lateinit var guestAdapter159: GuestAdapter159

    private val searchQueryFlow159 = MutableStateFlow("")

    override fun onCreate(savedInstanceState159: Bundle?) {
        super.onCreate(savedInstanceState159)

        sessionManager159 = SessionManager159(this)
        if (!sessionManager159.isLoggedIn159()) {
            redirectToLogin159()
            return
        }

        binding159 = ActivityDashboard159Binding.inflate(layoutInflater)
        setContentView(binding159.root)

        val database159 = AppDatabase159.getDatabase159(this)
        guestDao159 = database159.guestDao159()

        setupViews159()
        setupRecyclerView159()
        setupListeners159()
        setupObservers159()
    }

    private fun setupViews159() {
        val username159 = sessionManager159.getUsername159()
        binding159.tvWelcomeUser159.text = "${getString(R.string.greeting_user_prefix_159)}$username159"
        binding159.tvCurrentDate159.text = DateHelper159.formatCurrentDateHeader159()
    }

    private fun setupRecyclerView159() {
        guestAdapter159 = GuestAdapter159 { selectedGuest159 ->
            openGuestForm159(selectedGuest159.id_159)
        }

        binding159.rvGuestList159.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity159)
            adapter = guestAdapter159
            setHasFixedSize(false)
        }
    }

    private fun setupListeners159() {
        // FAB Add Guest
        binding159.fabAddGuest159.setOnClickListener {
            openGuestForm159(-1)
        }

        // Logout Button
        binding159.btnLogout159.setOnClickListener {
            showLogoutDialog159()
        }

        // Search Input Watcher
        binding159.etSearchGuest159.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s159: CharSequence?, start159: Int, count159: Int, after159: Int) {}
            override fun onTextChanged(s159: CharSequence?, start159: Int, before159: Int, count159: Int) {
                val keyword159 = s159?.toString()?.trim() ?: ""
                searchQueryFlow159.value = keyword159
                binding159.ivClearSearch159.visibility = if (keyword159.isNotEmpty()) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s159: Editable?) {}
        })

        // Clear Search Button
        binding159.ivClearSearch159.setOnClickListener {
            binding159.etSearchGuest159.text.clear()
        }
    }

    private fun setupObservers159() {
        val currentUserId159 = sessionManager159.getUserId159()
        val startOfDay159 = DateHelper159.getStartOfDay159()
        val endOfDay159 = DateHelper159.getEndOfDay159()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe Daily Guest Count
                launch {
                    guestDao159.getDailyGuestCount159(currentUserId159, startOfDay159, endOfDay159)
                        .collectLatest { dailyCount159 ->
                            binding159.tvTotalHarian159.text = dailyCount159.toString()
                        }
                }

                // Observe Total Guest Count (PRD Section 6.3)
                launch {
                    guestDao159.getTotalGuestCount159(currentUserId159)
                        .collectLatest { totalCount159 ->
                            binding159.tvTotalSemua159.text = totalCount159.toString()
                        }
                }

                // Observe Guest List (reactive with Search)
                launch {
                    searchQueryFlow159.flatMapLatest { query159 ->
                        if (query159.isEmpty()) {
                            guestDao159.getGuestsByUser159(currentUserId159)
                        } else {
                            guestDao159.searchGuests159(currentUserId159, query159)
                        }
                    }.collectLatest { guestList159 ->
                        guestAdapter159.submitList(guestList159)
                        updateEmptyState159(guestList159.isEmpty(), searchQueryFlow159.value.isNotEmpty())
                    }
                }
            }
        }
    }

    private fun updateEmptyState159(isEmpty159: Boolean, isSearching159: Boolean) {
        if (isEmpty159) {
            binding159.layoutEmptyState159.visibility = View.VISIBLE
            binding159.rvGuestList159.visibility = View.GONE
            if (isSearching159) {
                binding159.tvEmptyTitle159.text = "Tidak Ada Hasil Pencarian"
                binding159.tvEmptyDesc159.text = "Tidak ditemukan data tamu yang cocok dengan kata kunci pencarian Anda."
            } else {
                binding159.tvEmptyTitle159.text = getString(R.string.empty_guest_list_title_159)
                binding159.tvEmptyDesc159.text = getString(R.string.empty_guest_list_desc_159)
            }
        } else {
            binding159.layoutEmptyState159.visibility = View.GONE
            binding159.rvGuestList159.visibility = View.VISIBLE
        }
    }

    private fun openGuestForm159(guestId159: Int) {
        val intent159 = Intent(this, GuestFormActivity159::class.java).apply {
            putExtra(GuestFormActivity159.EXTRA_GUEST_ID_159, guestId159)
        }
        startActivity(intent159)
    }

    private fun showLogoutDialog159() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_logout_title_159))
            .setMessage(getString(R.string.dialog_logout_message_159))
            .setNegativeButton(getString(R.string.btn_cancel_159), null)
            .setPositiveButton(getString(R.string.btn_logout_confirm_159)) { _, _ ->
                sessionManager159.clearSession159()
                redirectToLogin159()
            }
            .show()
    }

    private fun redirectToLogin159() {
        val intent159 = Intent(this, LoginActivity159::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent159)
        finish()
    }
}
