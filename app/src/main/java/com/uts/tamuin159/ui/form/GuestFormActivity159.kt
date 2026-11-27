package com.uts.tamuin166.ui.form

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.tamuin166.R
import com.uts.tamuin166.data.AppDatabase166
import com.uts.tamuin166.data.dao.GuestDao166
import com.uts.tamuin166.data.entity.GuestEntity166
import com.uts.tamuin166.databinding.ActivityGuestForm166Binding
import com.uts.tamuin166.utils.DateHelper166
import com.uts.tamuin166.utils.SessionManager166
import kotlinx.coroutines.launch

class GuestFormActivity166 : AppCompatActivity() {

    private lateinit var binding166: ActivityGuestForm166Binding
    private lateinit var sessionManager166: SessionManager166
    private lateinit var guestDao166: GuestDao166

    private var guestId166: Int = -1
    private var existingGuest166: GuestEntity166? = null

    companion object {
        const val EXTRA_GUEST_ID_166 = "extra_guest_id_166"
    }

    override fun onCreate(savedInstanceState166: Bundle?) {
        super.onCreate(savedInstanceState166)
        binding166 = ActivityGuestForm166Binding.inflate(layoutInflater)
        setContentView(binding166.root)

        sessionManager166 = SessionManager166(this)
        val database166 = AppDatabase166.getDatabase166(this)
        guestDao166 = database166.guestDao166()

        guestId166 = intent.getIntExtra(EXTRA_GUEST_ID_166, -1)

        setupFormMode166()
        setupListeners166()
    }

    private fun setupFormMode166() {
        if (guestId166 == -1) {
            // Mode Tambah
            binding166.tvFormTitle166.text = getString(R.string.title_add_guest_166)
            binding166.btnSaveGuest166.text = getString(R.string.btn_save_guest_166)
            binding166.tvInfoWaktu166.text = getString(R.string.info_timestamp_add_166)
            binding166.btnDeleteGuest166.visibility = View.GONE
        } else {
            // Mode Edit
            binding166.tvFormTitle166.text = getString(R.string.title_edit_guest_166)
            binding166.btnSaveGuest166.text = getString(R.string.btn_update_guest_166)
            binding166.btnDeleteGuest166.visibility = View.VISIBLE

            loadExistingGuestData166()
        }
    }

    private fun loadExistingGuestData166() {
        val currentUserId166 = sessionManager166.getUserId166()

        lifecycleScope.launch {
            val guest166 = guestDao166.getGuestById166(guestId166, currentUserId166)
            if (guest166 != null) {
                existingGuest166 = guest166
                binding166.etNamaTamu166.setText(guest166.nama_166)
                binding166.etAlamatTamu166.setText(guest166.alamat_166)
                binding166.etKeperluanTamu166.setText(guest166.keperluan_166)

                val formattedTime166 = DateHelper166.formatTimestamp166(guest166.timestamp_166)
                binding166.tvInfoWaktu166.text = "Tercatat pada: $formattedTime166"
            } else {
                Toast.makeText(
                    this@GuestFormActivity166,
                    "Data tamu tidak ditemukan!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun setupListeners166() {
        binding166.btnBack166.setOnClickListener {
            finish()
        }

        binding166.btnSaveGuest166.setOnClickListener {
            saveGuestData166()
        }

        binding166.btnDeleteGuest166.setOnClickListener {
            showDeleteConfirmation166()
        }
    }

    private fun saveGuestData166() {
        val namaTamu166 = binding166.etNamaTamu166.text.toString().trim()
        val alamatTamu166 = binding166.etAlamatTamu166.text.toString().trim()
        val keperluanTamu166 = binding166.etKeperluanTamu166.text.toString().trim()

        binding166.tilNamaTamu166.error = null
        binding166.tilAlamatTamu166.error = null
        binding166.tilKeperluanTamu166.error = null

        var isValid166 = true

        if (!com.uts.tamuin166.utils.ValidationHelper166.isValidGuestField166(namaTamu166)) {
            binding166.tilNamaTamu166.error = getString(R.string.err_empty_name_166)
            isValid166 = false
        }

        if (!com.uts.tamuin166.utils.ValidationHelper166.isValidGuestField166(alamatTamu166)) {
            binding166.tilAlamatTamu166.error = getString(R.string.err_empty_address_166)
            isValid166 = false
        }

        if (!com.uts.tamuin166.utils.ValidationHelper166.isValidGuestField166(keperluanTamu166)) {
            binding166.tilKeperluanTamu166.error = getString(R.string.err_empty_purpose_166)
            isValid166 = false
        }

        if (!isValid166) return

        val currentUserId166 = sessionManager166.getUserId166()

        lifecycleScope.launch {
            if (guestId166 == -1) {
                // Insert New Guest
                val newGuest166 = GuestEntity166(
                    user_id_166 = currentUserId166,
                    nama_166 = namaTamu166,
                    alamat_166 = alamatTamu166,
                    keperluan_166 = keperluanTamu166,
                    timestamp_166 = System.currentTimeMillis()
                )
                guestDao166.insertGuest166(newGuest166)

                Toast.makeText(
                    this@GuestFormActivity166,
                    getString(R.string.success_save_guest_166),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Update Existing Guest
                val currentTimestamp166 = existingGuest166?.timestamp_166 ?: System.currentTimeMillis()
                val updatedGuest166 = GuestEntity166(
                    id_166 = guestId166,
                    user_id_166 = currentUserId166,
                    nama_166 = namaTamu166,
                    alamat_166 = alamatTamu166,
                    keperluan_166 = keperluanTamu166,
                    timestamp_166 = currentTimestamp166
                )
                guestDao166.updateGuest166(updatedGuest166)

                Toast.makeText(
                    this@GuestFormActivity166,
                    getString(R.string.success_update_guest_166),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }
    }

    private fun showDeleteConfirmation166() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_delete_title_166))
            .setMessage(getString(R.string.dialog_delete_message_166))
            .setNegativeButton(getString(R.string.btn_cancel_166), null)
            .setPositiveButton(getString(R.string.btn_delete_confirm_166)) { _, _ ->
                deleteGuestData166()
            }
            .show()
    }

    private fun deleteGuestData166() {
        val currentGuest166 = existingGuest166 ?: return

        lifecycleScope.launch {
            guestDao166.deleteGuest166(currentGuest166)
            Toast.makeText(
                this@GuestFormActivity166,
                getString(R.string.success_delete_guest_166),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}
