# Product Requirements Document (PRD) - Aplikasi Buku Tamu Digital

| Informasi Dokumen | Detail |
| :--- | :--- |
| **Nama Proyek** | Aplikasi Buku Tamu Digital (Kelompok 3) |
| **Mata Kuliah** | Pengembangan Aplikasi Bergerak (UTS) |
| **Batas Pengumpulan** | 9 November 2026 |
| **Platform** | Android (Native - Kotlin) |
| **Status Dokumen** | Final MVP Specification |
| **Aturan Penamaan (NIM)** | Menggunakan akhiran `_159` (Ganti `159` dengan 3 digit NIM terakhir Anda) |

---

## 1. Ringkasan Eksekutif (Executive Summary)

**Buku Tamu Digital** adalah aplikasi perangkat bergerak berbasis Android yang dirancang untuk mencatat, mengelola, dan merekapitulasi data kunjungan tamu secara luring (*offline*). Aplikasi ini menggunakan penyimpanan lokal **Room Persistence Library (SQLite)** dan manajemen sesi **SharedPreferences** tanpa memerlukan koneksi internet atau API eksternal.

Sistem ini menerapkan konsep **isolasi data multi-pengguna**, di mana setiap pengguna (petugas penerima tamu/admin) memiliki akun privat dan hanya dapat melihat serta mengelola daftar tamu yang dicatat melalui akunnya sendiri.

---

## 2. Tujuan Proyek & Keselarasan Rubrik Penilaian

Pengembangan Minimum Viable Product (MVP) ini ditujukan untuk memenuhi 100% kriteria penilaian utama ditambah 10% poin maksimal dari fitur bonus:

| Komponen Penilaian | Bobot | Implementasi pada Aplikasi |
| :--- | :--- | :--- |
| **Autentikasi & Session** | 30% | Registrasi & Login menggunakan query Room Database, penyimpanan sesi `user_id_159` di `SharedPreferences`, serta fitur Logout. |
| **Room Database (CRUD)** | 30% | Implementasi `UserEntity159`, `GuestEntity159`, DAO, dan `AppDatabase159`. Operasi Create, Read, Update, Delete berjalan penuh dengan waktu otomatis `System.currentTimeMillis()` dan rekap harian. |
| **UI & RecyclerView** | 15% | Struktur navigasi terarah, penggunaan `RecyclerView` untuk daftar tamu, serta Activity/Fragment terpisah untuk formulir input/edit. |
| **Data Integrity & Validation** | 15% | Pemisahan data antar pengguna menggunakan *foreign key* `user_id_159`. Validasi input kosong, kata sandi minimal 6 karakter, dan *username* unik. |
| **Fitur Bonus** | Maks 10% | Implementasi **ViewBinding** di seluruh halaman dan fitur **Search Bar** untuk mencari tamu secara *real-time*. |

---

## 3. Spesifikasi Teknis & Konvensi Kode

### 3.1. Teknologi yang Digunakan (Tech Stack)
* **IDE**: Android Studio
* **Bahasa Pemrograman**: Kotlin
* **Arsitektur Lokal Database**: Android Jetpack Room Persistence Library (SQLite)
* **Manajemen Sesi**: Android `SharedPreferences`
* **UI & Pemrosesan Asinkron**: Material Design Components, `RecyclerView`, `ViewBinding`, Kotlin Coroutines (`lifecycleScope`) & `Flow` / `LiveData`.

### 3.2. Aturan Wajib Penamaan Variabel & Tabel (3 Digit NIM)
Sesuai ketentuan pengerjaan UTS, **seluruh variabel dan nama tabel hasil modifikasi (non-Kotlin core) wajib menyertakan 3 digit NIM** sebagai pembeda:
* **Nama Tabel**: `tabel_user_159`, `tabel_guest_159`
* **Nama Kolom & Variabel Entitas**: `id_159`, `username_159`, `password_159`, `user_id_159`, `nama_159`, `alamat_159`, `keperluan_159`, `timestamp_159`
* **Nama Variabel Lokal/Fungsi**: `daftarTamu159`, `totalHarian159`, `simpanTamu159()`, dll.

---

## 4. Kebutuhan Fungsional (Functional Requirements) & User Stories

### 4.1. Modul Autentikasi & Manajemen Sesi
* **FR-AUTH-01 (Registrasi Akun)**: Pengguna baru dapat mendaftarkan akun dengan mengisi `username_159` dan `password_159`.
* **FR-AUTH-02 (Validasi Registrasi)**: Sistem menolak registrasi jika ada kolom kosong, jika `username_159` sudah terdaftar di database, atau jika `password_159` kurang dari 6 karakter.
* **FR-AUTH-03 (Login Pengguna)**: Pengguna dapat masuk menggunakan kredensial yang terdaftar. Sistem memverifikasi kecocokan data melalui query ke Room Database.
* **FR-AUTH-04 (Penyimpanan Sesi)**: Saat login berhasil, sistem menyimpan `id_159` (sebagai `user_id_159`) dan `username_159` ke dalam `SharedPreferences` agar pengguna tidak perlu login ulang saat aplikasi ditutup dan dibuka kembali.
* **FR-AUTH-05 (Logout)**: Pengguna dapat keluar dari akun melalui tombol Logout di Dashboard, yang akan menghapus data sesi di `SharedPreferences` dan mengarahkan kembali ke layar Login.

### 4.2. Modul Dashboard & Rekapitulasi Tamu Harian
* **FR-DASH-01 (Informasi Pengguna Aktif)**: Menampilkan sapaan atau identitas `username_159` yang sedang login.
* **FR-DASH-02 (Rekapitulasi Jumlah Tamu Harian)**: Menampilkan total jumlah tamu yang berkunjung pada hari ini (pukul `00:00:00` hingga `23:59:59` di hari berjalan) khusus untuk `user_id_159` yang sedang aktif.
* **FR-DASH-03 (Isolasi Data)**: Pengguna hanya dapat melihat rekapitulasi dan daftar tamu yang memiliki `user_id_159` sesuai dengan sesi loginnya.

### 4.3. Modul Daftar Data & Pencarian (RecyclerView & Bonus Search)
* **FR-LIST-01 (Tampilan Daftar Tamu)**: Menampilkan daftar seluruh tamu milik pengguna aktif menggunakan komponen `RecyclerView`, diurutkan dari waktu kunjungan terbaru (`timestamp_159 DESC`).
* **FR-LIST-02 (Format Waktu Terbaca)**: Nilai `timestamp_159` (tipe `Long`) dikonversi menjadi format tanggal dan waktu yang mudah dibaca pada kartu item (contoh: `09 Nov 2026, 14:30 WIB`).
* **FR-LIST-03 (Pencarian Tamu - Bonus)**: Pengguna dapat mengetik kata kunci pada *Search Bar* untuk memfilter daftar tamu secara langsung berdasarkan `nama_159`, `alamat_159`, atau `keperluan_159`.

### 4.4. Modul CRUD Buku Tamu (Tambah, Edit, Hapus)
* **FR-CRUD-01 (Tambah Tamu Baru)**: Pengguna mengisi formulir yang terdiri dari `nama_159`, `alamat_159`, dan `keperluan_159`.
* **FR-CRUD-02 (Pencatatan Waktu Otomatis)**: Saat data tamu baru disimpan, sistem secara otomatis mengisi nilai `timestamp_159` menggunakan fungsi `System.currentTimeMillis()` tanpa perlu diinput manual oleh pengguna.
* **FR-CRUD-03 (Edit Data Tamu)**: Saat salah satu item tamu pada `RecyclerView` ditekan, pengguna diarahkan ke formulir Edit yang sudah terisi data sebelumnya. Pengguna dapat memperbarui `nama_159`, `alamat_159`, dan `keperluan_159`.
* **FR-CRUD-04 (Hapus Data Tamu)**: Pengguna dapat menghapus data tamu melalui tombol Hapus (baik di dalam halaman Edit maupun pada item daftar) disertai dialog konfirmasi penghapusan.

---

## 5. Desain Database (Room Persistence Library)

### 5.1. Skema Tabel 1: `tabel_user_159` (Entity: `UserEntity159`)

| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id_159` | `Int` | `PRIMARY KEY (autoGenerate = true)` | ID unik untuk setiap pengguna. |
| `username_159` | `String` | `NOT NULL`, `UNIQUE INDEX` | Nama pengguna unik untuk autentikasi. |
| `password_159` | `String` | `NOT NULL` | Kata sandi pengguna (minimal 6 karakter). |

### 5.2. Skema Tabel 2: `tabel_guest_159` (Entity: `GuestEntity159`)

| Nama Kolom | Tipe Data | Constraint | Deskripsi |
| :--- | :--- | :--- | :--- |
| `id_159` | `Int` | `PRIMARY KEY (autoGenerate = true)` | ID unik untuk setiap data kunjungan tamu. |
| `user_id_159` | `Int` | `FOREIGN KEY (tabel_user_159.id_159)` | Kunci tamu merujuk ke pemilik akun (`ON DELETE CASCADE`). |
| `nama_159` | `String` | `NOT NULL` | Nama lengkap tamu yang berkunjung. |
| `alamat_159` | `String` | `NOT NULL` | Alamat domisili atau instansi asal tamu. |
| `keperluan_159` | `String` | `NOT NULL` | Tujuan atau keperluan kunjungan. |
| `timestamp_159` | `Long` | `NOT NULL` | Waktu pencatatan otomatis (`System.currentTimeMillis()`). |

### 5.3. Rancangan Query DAO (Data Access Object)

#### A. `UserDao159`
```kotlin
@Dao
interface UserDao159 {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser159(user159: UserEntity159): Long

    @Query("SELECT * FROM tabel_user_159 WHERE username_159 = :username159 LIMIT 1")
    suspend fun checkUsernameExists159(username159: String): UserEntity159?

    @Query("SELECT * FROM tabel_user_159 WHERE username_159 = :username159 AND password_159 = :password159 LIMIT 1")
    suspend fun loginUser159(username159: String, password159: String): UserEntity159?
}
```

#### B. `GuestDao159`
```kotlin
@Dao
interface GuestDao159 {
    @Insert
    suspend fun insertGuest159(guest159: GuestEntity159)

    @Update
    suspend fun updateGuest159(guest159: GuestEntity159)

    @Delete
    suspend fun deleteGuest159(guest159: GuestEntity159)

    @Query("SELECT * FROM tabel_guest_159 WHERE user_id_159 = :userId159 ORDER BY timestamp_159 DESC")
    fun getAllGuestsByUser159(userId159: Int): Flow<List<GuestEntity159>>

    @Query("SELECT COUNT(*) FROM tabel_guest_159 WHERE user_id_159 = :userId159 AND timestamp_159 BETWEEN :startOfDay159 AND :endOfDay159")
    fun getDailyGuestCount159(userId159: Int, startOfDay159: Long, endOfDay159: Long): Flow<Int>

    @Query("""
        SELECT * FROM tabel_guest_159 
        WHERE user_id_159 = :userId159 
        AND (nama_159 LIKE '%' || :keyword159 || '%' 
             OR alamat_159 LIKE '%' || :keyword159 || '%' 
             OR keperluan_159 LIKE '%' || :keyword159 || '%') 
        ORDER BY timestamp_159 DESC
    """)
    fun searchGuestsByUser159(userId159: Int, keyword159: String): Flow<List<GuestEntity159>>
}
```

---

## 6. Struktur Navigasi & Spesifikasi Antarmuka (UI/UX)

Alur navigasi utama mengikuti ketentuan:
**Login -> Dashboard -> List Data (RecyclerView) -> Form Tambah -> Edit/Hapus**

### 6.1. Layar 1: Halaman Login (`LoginActivity159`)
* **Kondisi Awal**: Mengecek `SessionManager159` (`SharedPreferences`). Jika `user_id_159 != -1`, langsung berpindah ke `DashboardActivity159` dan menutup `LoginActivity159`.
* **Komponen UI**:
  * `TextInputEditText` untuk `etUsername159`
  * `TextInputEditText` untuk `etPassword159` (inputType `textPassword`)
  * `MaterialButton` `btnLogin159` ("Masuk")
  * `TextView` `tvGoToRegister159` ("Belum punya akun? Daftar di sini")

### 6.2. Layar 2: Halaman Registrasi (`RegisterActivity159`)
* **Komponen UI**:
  * `TextInputEditText` untuk `etRegUsername159`
  * `TextInputEditText` untuk `etRegPassword159`
  * `TextInputEditText` untuk `etRegConfirmPassword159`
  * `MaterialButton` `btnRegister159` ("Daftar Akun")
  * `TextView` `tvBackToLogin159` ("Sudah punya akun? Masuk")
* **Aksi**: Jika validasi lolos dan data tersimpan di Room, tampilkan `Toast` "Registrasi berhasil, silakan login" dan arahkan kembali ke `LoginActivity159`.

### 6.3. Layar 3: Dashboard & Daftar Tamu (`DashboardActivity159`)
Halaman ini menggabungkan ringkasan Dashboard dengan Daftar Data (`RecyclerView`) untuk pengalaman pengguna yang efisien (atau dapat dipisah menjadi Fragment/Activity sesuai kebutuhan).
* **Komponen UI Bagian Atas (Header & Kartu Rekap)**:
  * `TextView` `tvWelcomeUser159` ("Halo, [username_159]")
  * `ImageButton` / `MaterialButton` `btnLogout159` ("Keluar")
  * `CardView` Rekap Harian yang menampilkan:
    * Label: "Total Tamu Hari Ini"
    * Tanggal Hari Ini (contoh: "Senin, 9 Nov 2026")
    * `TextView` `tvTotalHarian159` (Angka besar jumlah tamu hari ini, misal: `12 Orang`)
  * `CardView` / Informasi tambahan: Total keseluruhan tamu (`tvTotalSemua159`).
* **Komponen UI Bagian Tengah (Pencarian & Daftar Tamu)**:
  * `SearchView` / `EditText` `svCariTamu159` ("Cari nama, alamat, atau keperluan...")
  * `RecyclerView` `rvGuestList159` (Menampilkan daftar `item_guest_159.xml`)
  * `TextView` `tvEmptyState159` ("Belum ada data tamu tercatat" - tampil jika list kosong)
* **Komponen UI Bagian Bawah**:
  * `FloatingActionButton` `fabAddGuest159` ("+ Tambah Tamu")

### 6.4. Layar 4: Formulir Tambah / Edit Tamu (`GuestFormActivity159`)
Halaman ini menangani mode **Tambah** (jika dipanggil dari `fabAddGuest159`) dan mode **Edit/Hapus** (jika dipanggil dari klik item `RecyclerView`).
* **Komponen UI**:
  * `Toolbar` dengan judul dinamis ("Tambah Tamu Baru" atau "Edit Data Tamu")
  * `TextInputEditText` `etNamaTamu159` (Nama Lengkap Tamu)
  * `TextInputEditText` `etAlamatTamu159` (Alamat / Instansi)
  * `TextInputEditText` `etKeperluanTamu159` (Keperluan Kunjungan - *multiline*)
  * `TextView` `tvInfoWaktu159` (Pada mode Tambah: "Waktu kunjungan dicatat otomatis saat disimpan". Pada mode Edit: Menampilkan waktu kunjungan asli yang telah tercatat).
  * `MaterialButton` `btnSaveGuest159` ("Simpan Data")
  * `MaterialButton` `btnDeleteGuest159` ("Hapus Tamu" - hanya tampil/aktif saat berada di **Mode Edit**).

---

## 7. Logika Bisnis & Aturan Validasi

### 7.1. Tabel Aturan Validasi Input
| Form | Field | Aturan Validasi | Pesan Error |
| :--- | :--- | :--- | :--- |
| **Register** | Username | Tidak boleh kosong & belum ada di DB | "Username wajib diisi" / "Username sudah digunakan" |
| **Register** | Password | Tidak boleh kosong & panjang $\ge 6$ karakter | "Password minimal 6 karakter" |
| **Login** | Username & Password | Tidak boleh kosong & cocok di DB | "Username atau password salah" |
| **Form Tamu** | Nama Tamu | Tidak boleh kosong (`isNotBlank()`) | "Nama tamu tidak boleh kosong" |
| **Form Tamu** | Alamat | Tidak boleh kosong (`isNotBlank()`) | "Alamat/Instansi wajib diisi" |
| **Form Tamu** | Keperluan | Tidak boleh kosong (`isNotBlank()`) | "Keperluan kunjungan wajib diisi" |

### 7.2. Logika Perhitungan Waktu & Rekap Harian
1. **Pencatatan Waktu Otomatis**:
   * Saat membuat objek `GuestEntity159` baru:
     `val waktuSekarang159 = System.currentTimeMillis()`
   * Saat melakukan **Edit** data tamu, nilai `timestamp_159` lama tetap dipertahankan agar riwayat waktu kedatangan asli tidak berubah (atau dapat diperbarui sesuai kebijakan kelompok).
2. **Rentang Waktu Rekapitulasi Harian**:
   Untuk menghitung jumlah tamu pada hari berjalan di `getDailyGuestCount159(userId159, startOfDay159, endOfDay159)`:
   * `startOfDay159`: Objek `Calendar` disetel ke hari ini pada jam `00`, menit `00`, detik `00`, milidetik `000` (`timeInMillis`).
   * `endOfDay159`: Objek `Calendar` disetel ke hari ini pada jam `23`, menit `59`, detik `59`, milidetik `999` (`timeInMillis`).

---

## 8. Rekomendasi Struktur Paket (Package Structure)

```text
com.kelompok3.bukutamudigital159/
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   ├── UserEntity159.kt
│   │   │   └── GuestEntity159.kt
│   │   ├── dao/
│   │   │   ├── UserDao159.kt
│   │   │   └── GuestDao159.kt
│   │   └── AppDatabase159.kt
│   └── pref/
│       └── SessionManager159.kt
├── ui/
│   ├── auth/
│   │   ├── LoginActivity159.kt
│   │   └── RegisterActivity159.kt
│   ├── dashboard/
│   │   ├── DashboardActivity159.kt
│   │   └── GuestAdapter159.kt
│   └── form/
│       └── GuestFormActivity159.kt
└── utils/
    └── DateHelper159.kt
```

---

## 9. Kriteria Penerimaan & Skenario Pengujian (QA Checklist)

Sebelum dikumpulkan pada **9 November 2026**, pastikan seluruh skenario pengujian berikut berstatus **LULUS (PASS)**:

- [ ] **TC-01 (Validasi Password & Username Unik)**: Mencoba daftar dengan password 4 karakter (harus ditolak). Mencoba daftar dengan username yang sama dua kali (percobaan kedua harus ditolak).
- [ ] **TC-02 (Persistensi Sesi SharedPreferences)**: Login ke aplikasi, tutup paksa aplikasi (*swipe from recent apps*), lalu buka kembali. Aplikasi harus langsung membuka halaman Dashboard tanpa meminta login ulang.
- [ ] **TC-03 (Tambah Tamu & Timestamp Otomatis)**: Menambahkan 1 data tamu baru. Data langsung muncul di `RecyclerView` lengkap dengan jam dan tanggal kunjungan saat ini tanpa input waktu manual.
- [ ] **TC-04 (Rekap Harian Real-time)**: Saat menambahkan tamu baru, angka pada kartu "Total Tamu Hari Ini" di Dashboard langsung bertambah $+1$. Saat tamu tersebut dihapus, angka berkurang $-1$.
- [ ] **TC-05 (Update & Delete Data)**: Menekan salah satu tamu di list, mengubah keperluannya, lalu simpan (data di list harus berubah). Menekan tombol hapus pada tamu (data harus hilang dari list dan database).
- [ ] **TC-06 (Isolasi Data Antar User)**:
  1. Login sebagai **User A**, tambahkan 2 tamu.
  2. Logout, lalu daftar dan login sebagai **User B**.
  3. Pastikan daftar tamu **User B** kosong (`0` tamu hari ini) dan data milik **User A** tidak terlihat sama sekali.
- [ ] **TC-07 (Fitur Bonus Search & ViewBinding)**: Mengetik nama tamu di kolom pencarian memfilter `RecyclerView` dengan benar, dan seluruh `Activity` maupun `Adapter` telah menggunakan `ViewBinding` (tanpa `findViewById`).
- [ ] **TC-08 (Kepatuhan Aturan 3 Digit NIM)**: Memeriksa seluruh file `.kt` untuk memastikan nama tabel dan variabel hasil modifikasi telah menggunakan akhiran `_159`.