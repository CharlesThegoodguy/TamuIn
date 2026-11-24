package com.uts.tamuin159.ui.form

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.tamuin159.R
import com.uts.tamuin159.data.AppDatabase159
import com.uts.tamuin159.data.dao.GuestDao159
import com.uts.tamuin159.data.entity.GuestEntity159
import com.uts.tamuin159.databinding.ActivityGuestForm159Binding
import com.uts.tamuin159.utils.DateHelper159
import com.uts.tamuin159.utils.SessionManager159
import kotlinx.coroutines.launch

class GuestFormActivity159 : AppCompatActivity() {

    private lateinit var binding159: ActivityGuestForm159Binding
    private lateinit var sessionManager159: SessionManager159
    private lateinit var guestDao159: GuestDao159

    private var guestId159: Int = -1
    private var existingGuest159: GuestEntity159? = null

    companion object {
        const val EXTRA_GUEST_ID_159 = "extra_guest_id_159"
    }

    override fun onCreate(savedInstanceState159: Bundle?) {
        super.onCreate(savedInstanceState159)
        binding159 = ActivityGuestForm159Binding.inflate(layoutInflater)
        setContentView(binding159.root)

        sessionManager159 = SessionManager159(this)
        val database159 = AppDatabase159.getDatabase159(this)
        guestDao159 = database159.guestDao159()

        guestId159 = intent.getIntExtra(EXTRA_GUEST_ID_159, -1)

        setupFormMode159()
        setupListeners159()
    }

    private fun setupFormMode159() {
        if (guestId159 == -1) {
            // Mode Tambah
            binding159.tvFormTitle159.text = getString(R.string.title_add_guest_159)
            binding159.btnSaveGuest159.text = getString(R.string.btn_save_guest_159)
            binding159.tvInfoWaktu159.text = getString(R.string.info_timestamp_add_159)
            binding159.btnDeleteGuest159.visibility = View.GONE
        } else {
            // Mode Edit
            binding159.tvFormTitle159.text = getString(R.string.title_edit_guest_159)
            binding159.btnSaveGuest159.text = getString(R.string.btn_update_guest_159)
            binding159.btnDeleteGuest159.visibility = View.VISIBLE

            loadExistingGuestData159()
        }
    }

    private fun loadExistingGuestData159() {
        val currentUserId159 = sessionManager159.getUserId159()

        lifecycleScope.launch {
            val guest159 = guestDao159.getGuestById159(guestId159, currentUserId159)
            if (guest159 != null) {
                existingGuest159 = guest159
                binding159.etNamaTamu159.setText(guest159.nama_159)
                binding159.etAlamatTamu159.setText(guest159.alamat_159)
                binding159.etKeperluanTamu159.setText(guest159.keperluan_159)

                val formattedTime159 = DateHelper159.formatTimestamp159(guest159.timestamp_159)
                binding159.tvInfoWaktu159.text = "Tercatat pada: $formattedTime159"
            } else {
                Toast.makeText(
                    this@GuestFormActivity159,
                    "Data tamu tidak ditemukan!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun setupListeners159() {
        binding159.btnBack159.setOnClickListener {
            finish()
        }

        binding159.btnSaveGuest159.setOnClickListener {
            saveGuestData159()
        }

        binding159.btnDeleteGuest159.setOnClickListener {
            showDeleteConfirmation159()
        }
    }

    private fun saveGuestData159() {
        val namaTamu159 = binding159.etNamaTamu159.text.toString().trim()
        val alamatTamu159 = binding159.etAlamatTamu159.text.toString().trim()
        val keperluanTamu159 = binding159.etKeperluanTamu159.text.toString().trim()

        binding159.tilNamaTamu159.error = null
        binding159.tilAlamatTamu159.error = null
        binding159.tilKeperluanTamu159.error = null

        var isValid159 = true

        if (!com.uts.tamuin159.utils.ValidationHelper159.isValidGuestField159(namaTamu159)) {
            binding159.tilNamaTamu159.error = getString(R.string.err_empty_name_159)
            isValid159 = false
        }

        if (!com.uts.tamuin159.utils.ValidationHelper159.isValidGuestField159(alamatTamu159)) {
            binding159.tilAlamatTamu159.error = getString(R.string.err_empty_address_159)
            isValid159 = false
        }

        if (!com.uts.tamuin159.utils.ValidationHelper159.isValidGuestField159(keperluanTamu159)) {
            binding159.tilKeperluanTamu159.error = getString(R.string.err_empty_purpose_159)
            isValid159 = false
        }

        if (!isValid159) return

        val currentUserId159 = sessionManager159.getUserId159()

        lifecycleScope.launch {
            if (guestId159 == -1) {
                // Insert New Guest
                val newGuest159 = GuestEntity159(
                    user_id_159 = currentUserId159,
                    nama_159 = namaTamu159,
                    alamat_159 = alamatTamu159,
                    keperluan_159 = keperluanTamu159,
                    timestamp_159 = System.currentTimeMillis()
                )
                guestDao159.insertGuest159(newGuest159)

                Toast.makeText(
                    this@GuestFormActivity159,
                    getString(R.string.success_save_guest_159),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Update Existing Guest
                val currentTimestamp159 = existingGuest159?.timestamp_159 ?: System.currentTimeMillis()
                val updatedGuest159 = GuestEntity159(
                    id_159 = guestId159,
                    user_id_159 = currentUserId159,
                    nama_159 = namaTamu159,
                    alamat_159 = alamatTamu159,
                    keperluan_159 = keperluanTamu159,
                    timestamp_159 = currentTimestamp159
                )
                guestDao159.updateGuest159(updatedGuest159)

                Toast.makeText(
                    this@GuestFormActivity159,
                    getString(R.string.success_update_guest_159),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }
    }

    private fun showDeleteConfirmation159() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_delete_title_159))
            .setMessage(getString(R.string.dialog_delete_message_159))
            .setNegativeButton(getString(R.string.btn_cancel_159), null)
            .setPositiveButton(getString(R.string.btn_delete_confirm_159)) { _, _ ->
                deleteGuestData159()
            }
            .show()
    }

    private fun deleteGuestData159() {
        val currentGuest159 = existingGuest159 ?: return

        lifecycleScope.launch {
            guestDao159.deleteGuest159(currentGuest159)
            Toast.makeText(
                this@GuestFormActivity159,
                getString(R.string.success_delete_guest_159),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}
