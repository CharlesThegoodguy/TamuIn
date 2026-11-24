<?php
declare(strict_types=1);

date_default_timezone_set('Asia/Jakarta');

/**
 * Dummy guest data matching Android GuestEntity159 schema.
 * shortcut: hardcoded in-memory dummy array (read-only), upgrade to PDO/MySQL + auth token when switching to real persistence.
 */
function getDummyGuests159(): array
{
    return [
        [
            'id_159' => 1,
            'user_id_159' => 1,
            'nama_159' => 'Budi Santoso',
            'alamat_159' => 'PT Maju Bersama, Jakarta',
            'keperluan_159' => 'Rapat kerja sama proyek digital',
            'timestamp_159' => strtotime('today 10:45:00') * 1000,
        ],
        [
            'id_159' => 2,
            'user_id_159' => 1,
            'nama_159' => 'Siti Rahmawati',
            'alamat_159' => 'Universitas Negeri Malang',
            'keperluan_159' => 'Pengajuan surat penelitian mahasiswa',
            'timestamp_159' => strtotime('today 09:30:00') * 1000,
        ],
        [
            'id_159' => 3,
            'user_id_159' => 1,
            'nama_159' => 'Agus Pratama',
            'alamat_159' => 'Dinas Kominfo Kota Surabaya',
            'keperluan_159' => 'Koordinasi integrasi data layanan',
            'timestamp_159' => strtotime('today 08:15:00') * 1000,
        ],
        [
            'id_159' => 4,
            'user_id_159' => 1,
            'nama_159' => 'Dewi Lestari',
            'alamat_159' => 'CV Karya Mandiri, Sidoarjo',
            'keperluan_159' => 'Pengiriman dokumen penawaran vendor',
            'timestamp_159' => strtotime('-1 day 14:20:00') * 1000,
        ],
        [
            'id_159' => 5,
            'user_id_159' => 1,
            'nama_159' => 'Rizky Hidayat',
            'alamat_159' => 'Jl. Ahmad Yani No. 45, Gresik',
            'keperluan_159' => 'Konsultasi layanan administrasi',
            'timestamp_159' => strtotime('-2 days 11:10:00') * 1000,
        ],
        [
            'id_159' => 6,
            'user_id_159' => 2,
            'nama_159' => 'Nadia Putri',
            'alamat_159' => 'SMK Telkom Malang',
            'keperluan_159' => 'Koordinasi jadwal magang siswa',
            'timestamp_159' => strtotime('-1 day 09:05:00') * 1000,
        ],
    ];
}

function parsePositiveIntParam159(array $query, string $key): array
{
    if (!array_key_exists($key, $query) || $query[$key] === '') {
        return [null, null];
    }

    $raw = $query[$key];
    if (!is_int($raw) && !is_string($raw)) {
        return [null, "Parameter {$key} harus berupa angka positif"];
    }

    $parsed = filter_var($raw, FILTER_VALIDATE_INT);
    if ($parsed === false || $parsed <= 0) {
        return [null, "Parameter {$key} harus berupa angka positif"];
    }

    return [$parsed, null];
}

function handleGuestsRequest159(string $method, array $query): array
{
    $upperMethod = strtoupper($method);
    if ($upperMethod === 'OPTIONS') {
        return ['status_code' => 204, 'body' => null];
    }

    if ($upperMethod !== 'GET') {
        return [
            'status_code' => 405,
            'body' => [
                'status' => 'error',
                'message' => 'Method tidak diizinkan, gunakan GET',
            ],
        ];
    }

    [$userId, $userIdError] = parsePositiveIntParam159($query, 'user_id');
    if ($userIdError !== null) {
        return [
            'status_code' => 400,
            'body' => ['status' => 'error', 'message' => $userIdError],
        ];
    }

    [$guestId, $guestIdError] = parsePositiveIntParam159($query, 'id');
    if ($guestIdError !== null) {
        return [
            'status_code' => 400,
            'body' => ['status' => 'error', 'message' => $guestIdError],
        ];
    }

    $search = '';
    if (array_key_exists('q', $query) && $query['q'] !== '') {
        if (!is_string($query['q'])) {
            return [
                'status_code' => 400,
                'body' => ['status' => 'error', 'message' => 'Parameter q harus berupa teks'],
            ];
        }
        $search = trim($query['q']);
    }

    $guests = getDummyGuests159();
    usort($guests, static fn(array $a, array $b): int => $b['timestamp_159'] <=> $a['timestamp_159']);

    if ($userId !== null) {
        $guests = array_values(array_filter(
            $guests,
            static fn(array $guest): bool => $guest['user_id_159'] === $userId
        ));
    }

    if ($guestId !== null) {
        foreach ($guests as $guest) {
            if ($guest['id_159'] === $guestId) {
                return [
                    'status_code' => 200,
                    'body' => [
                        'status' => 'success',
                        'data' => $guest,
                    ],
                ];
            }
        }

        return [
            'status_code' => 404,
            'body' => [
                'status' => 'error',
                'message' => 'Data tamu tidak ditemukan',
            ],
        ];
    }

    $startOfDayMs = strtotime('today 00:00:00') * 1000;
    $endOfDayMs = (strtotime('today 23:59:59') * 1000) + 999;

    // Totals follow DashboardActivity159: scoped by user_id, unaffected by search keyword.
    $totalSemua = count($guests);
    $totalHarian = count(array_filter(
        $guests,
        static fn(array $guest): bool => $guest['timestamp_159'] >= $startOfDayMs && $guest['timestamp_159'] <= $endOfDayMs
    ));

    if ($search !== '') {
        $guests = array_values(array_filter(
            $guests,
            static fn(array $guest): bool =>
                stripos($guest['nama_159'], $search) !== false ||
                stripos($guest['alamat_159'], $search) !== false ||
                stripos($guest['keperluan_159'], $search) !== false
        ));
    }

    return [
        'status_code' => 200,
        'body' => [
            'status' => 'success',
            'total_semua_159' => $totalSemua,
            'total_harian_159' => $totalHarian,
            'data' => $guests,
        ],
    ];
}

$scriptPath = realpath($_SERVER['SCRIPT_FILENAME'] ?? '');
$indexPath = realpath(__DIR__ . DIRECTORY_SEPARATOR . 'index.php');

if (PHP_SAPI !== 'cli' || $scriptPath === __FILE__ || ($indexPath !== false && $scriptPath === $indexPath)) {
    $response = handleGuestsRequest159($_SERVER['REQUEST_METHOD'] ?? 'GET', $_GET);

    if (PHP_SAPI !== 'cli') {
        header('Content-Type: application/json; charset=utf-8');
        header('Access-Control-Allow-Origin: *');
        header('Access-Control-Allow-Methods: GET, OPTIONS');
        header('Access-Control-Allow-Headers: Content-Type');
        if ($response['status_code'] === 405) {
            header('Allow: GET, OPTIONS');
        }
        http_response_code($response['status_code']);
    }

    if ($response['body'] !== null) {
        echo json_encode($response['body'], JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES | JSON_PRETTY_PRINT) . PHP_EOL;
    }
}
