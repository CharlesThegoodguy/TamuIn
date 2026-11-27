package com.uts.tamuin166.ui.dashboard

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
import com.uts.tamuin166.R
import com.uts.tamuin166.data.AppDatabase166
import com.uts.tamuin166.data.dao.GuestDao166
import com.uts.tamuin166.data.entity.GuestEntity166
import com.uts.tamuin166.databinding.ActivityDashboard166Binding
import com.uts.tamuin166.ui.auth.LoginActivity166
import com.uts.tamuin166.ui.form.GuestFormActivity166
import com.uts.tamuin166.utils.DateHelper166
import com.uts.tamuin166.utils.SessionManager166
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardActivity166 : AppCompatActivity() {

    private lateinit var binding166: ActivityDashboard166Binding
    private lateinit var sessionManager166: SessionManager166
    private lateinit var guestDao166: GuestDao166
    private lateinit var guestAdapter166: GuestAdapter166

    private val searchQueryFlow166 = MutableStateFlow("")

    override fun onCreate(savedInstanceState166: Bundle?) {
        super.onCreate(savedInstanceState166)

        sessionManager166 = SessionManager166(this)
        if (!sessionManager166.isLoggedIn166()) {
            redirectToLogin166()
            return
        }

        binding166 = ActivityDashboard166Binding.inflate(layoutInflater)
        setContentView(binding166.root)

        val database166 = AppDatabase166.getDatabase166(this)
        guestDao166 = database166.guestDao166()

        setupViews166()
        setupRecyclerView166()
        setupListeners166()
        setupObservers166()
    }

    private fun setupViews166() {
        val username166 = sessionManager166.getUsername166()
        binding166.tvWelcomeUser166.text = "${getString(R.string.greeting_user_prefix_166)}$username166"
        binding166.tvCurrentDate166.text = DateHelper166.formatCurrentDateHeader166()
    }

    private fun setupRecyclerView166() {
        guestAdapter166 = GuestAdapter166 { selectedGuest166 ->
            openGuestForm166(selectedGuest166.id_166)
        }

        binding166.rvGuestList166.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity166)
            adapter = guestAdapter166
            setHasFixedSize(false)
        }
    }

    private fun setupListeners166() {
        // FAB Add Guest
        binding166.fabAddGuest166.setOnClickListener {
            openGuestForm166(-1)
        }

        // Logout Button
        binding166.btnLogout166.setOnClickListener {
            showLogoutDialog166()
        }

        // Search Input Watcher
        binding166.etSearchGuest166.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s166: CharSequence?, start166: Int, count166: Int, after166: Int) {}
            override fun onTextChanged(s166: CharSequence?, start166: Int, before166: Int, count166: Int) {
                val keyword166 = s166?.toString()?.trim() ?: ""
                searchQueryFlow166.value = keyword166
                binding166.ivClearSearch166.visibility = if (keyword166.isNotEmpty()) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s166: Editable?) {}
        })

        // Clear Search Button
        binding166.ivClearSearch166.setOnClickListener {
            binding166.etSearchGuest166.text.clear()
        }
    }

    private fun setupObservers166() {
        val currentUserId166 = sessionManager166.getUserId166()
        val startOfDay166 = DateHelper166.getStartOfDay166()
        val endOfDay166 = DateHelper166.getEndOfDay166()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe Daily Guest Count
                launch {
                    guestDao166.getDailyGuestCount166(currentUserId166, startOfDay166, endOfDay166)
                        .collectLatest { dailyCount166 ->
                            binding166.tvTotalHarian166.text = dailyCount166.toString()
                        }
                }

                // Observe Total Guest Count (PRD Section 6.3)
                launch {
                    guestDao166.getTotalGuestCount166(currentUserId166)
                        .collectLatest { totalCount166 ->
                            binding166.tvTotalSemua166.text = totalCount166.toString()
                        }
                }

                // Observe Guest List (reactive with Search)
                launch {
                    searchQueryFlow166.flatMapLatest { query166 ->
                        if (query166.isEmpty()) {
                            guestDao166.getGuestsByUser166(currentUserId166)
                        } else {
                            guestDao166.searchGuests166(currentUserId166, query166)
                        }
                    }.collectLatest { guestList166 ->
                        guestAdapter166.submitList(guestList166)
                        updateEmptyState166(guestList166.isEmpty(), searchQueryFlow166.value.isNotEmpty())
                    }
                }
            }
        }
    }

    private fun updateEmptyState166(isEmpty166: Boolean, isSearching166: Boolean) {
        if (isEmpty166) {
            binding166.layoutEmptyState166.visibility = View.VISIBLE
            binding166.rvGuestList166.visibility = View.GONE
            if (isSearching166) {
                binding166.tvEmptyTitle166.text = "Tidak Ada Hasil Pencarian"
                binding166.tvEmptyDesc166.text = "Tidak ditemukan data tamu yang cocok dengan kata kunci pencarian Anda."
            } else {
                binding166.tvEmptyTitle166.text = getString(R.string.empty_guest_list_title_166)
                binding166.tvEmptyDesc166.text = getString(R.string.empty_guest_list_desc_166)
            }
        } else {
            binding166.layoutEmptyState166.visibility = View.GONE
            binding166.rvGuestList166.visibility = View.VISIBLE
        }
    }

    private fun openGuestForm166(guestId166: Int) {
        val intent166 = Intent(this, GuestFormActivity166::class.java).apply {
            putExtra(GuestFormActivity166.EXTRA_GUEST_ID_166, guestId166)
        }
        startActivity(intent166)
    }

    private fun showLogoutDialog166() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_logout_title_166))
            .setMessage(getString(R.string.dialog_logout_message_166))
            .setNegativeButton(getString(R.string.btn_cancel_166), null)
            .setPositiveButton(getString(R.string.btn_logout_confirm_166)) { _, _ ->
                sessionManager166.clearSession166()
                redirectToLogin166()
            }
            .show()
    }

    private fun redirectToLogin166() {
        val intent166 = Intent(this, LoginActivity166::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent166)
        finish()
    }
}
