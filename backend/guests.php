<?php
declare(strict_types=1);

date_default_timezone_set('Asia/Jakarta');

/**
 * Dummy guest data matching Android GuestEntity166 schema.
 * shortcut: hardcoded in-memory dummy array (read-only), upgrade to PDO/MySQL + auth token when switching to real persistence.
 */
function getDummyGuests166(): array
{
    return [
        [
            'id_166' => 1,
            'user_id_166' => 1,
            'nama_166' => 'Budi Santoso',
            'alamat_166' => 'PT Maju Bersama, Jakarta',
            'keperluan_166' => 'Rapat kerja sama proyek digital',
            'timestamp_166' => strtotime('today 10:45:00') * 1000,
        ],
        [
            'id_166' => 2,
            'user_id_166' => 1,
            'nama_166' => 'Siti Rahmawati',
            'alamat_166' => 'Universitas Negeri Malang',
            'keperluan_166' => 'Pengajuan surat penelitian mahasiswa',
            'timestamp_166' => strtotime('today 09:30:00') * 1000,
        ],
        [
            'id_166' => 3,
            'user_id_166' => 1,
            'nama_166' => 'Agus Pratama',
            'alamat_166' => 'Dinas Kominfo Kota Surabaya',
            'keperluan_166' => 'Koordinasi integrasi data layanan',
            'timestamp_166' => strtotime('today 08:15:00') * 1000,
        ],
        [
            'id_166' => 4,
            'user_id_166' => 1,
            'nama_166' => 'Dewi Lestari',
            'alamat_166' => 'CV Karya Mandiri, Sidoarjo',
            'keperluan_166' => 'Pengiriman dokumen penawaran vendor',
            'timestamp_166' => strtotime('-1 day 14:20:00') * 1000,
        ],
        [
            'id_166' => 5,
            'user_id_166' => 1,
            'nama_166' => 'Rizky Hidayat',
            'alamat_166' => 'Jl. Ahmad Yani No. 45, Gresik',
            'keperluan_166' => 'Konsultasi layanan administrasi',
            'timestamp_166' => strtotime('-2 days 11:10:00') * 1000,
        ],
        [
            'id_166' => 6,
            'user_id_166' => 2,
            'nama_166' => 'Nadia Putri',
            'alamat_166' => 'SMK Telkom Malang',
            'keperluan_166' => 'Koordinasi jadwal magang siswa',
            'timestamp_166' => strtotime('-1 day 09:05:00') * 1000,
        ],
    ];
}

function parsePositiveIntParam166(array $query, string $key): array
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

function handleGuestsRequest166(string $method, array $query): array
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

    [$userId, $userIdError] = parsePositiveIntParam166($query, 'user_id');
    if ($userIdError !== null) {
        return [
            'status_code' => 400,
            'body' => ['status' => 'error', 'message' => $userIdError],
        ];
    }

    [$guestId, $guestIdError] = parsePositiveIntParam166($query, 'id');
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

    $guests = getDummyGuests166();
    usort($guests, static fn(array $a, array $b): int => $b['timestamp_166'] <=> $a['timestamp_166']);

    if ($userId !== null) {
        $guests = array_values(array_filter(
            $guests,
            static fn(array $guest): bool => $guest['user_id_166'] === $userId
        ));
    }

    if ($guestId !== null) {
        foreach ($guests as $guest) {
            if ($guest['id_166'] === $guestId) {
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

    // Totals follow DashboardActivity166: scoped by user_id, unaffected by search keyword.
    $totalSemua = count($guests);
    $totalHarian = count(array_filter(
        $guests,
        static fn(array $guest): bool => $guest['timestamp_166'] >= $startOfDayMs && $guest['timestamp_166'] <= $endOfDayMs
    ));

    if ($search !== '') {
        $guests = array_values(array_filter(
            $guests,
            static fn(array $guest): bool =>
                stripos($guest['nama_166'], $search) !== false ||
                stripos($guest['alamat_166'], $search) !== false ||
                stripos($guest['keperluan_166'], $search) !== false
        ));
    }

    return [
        'status_code' => 200,
        'body' => [
            'status' => 'success',
            'total_semua_166' => $totalSemua,
            'total_harian_166' => $totalHarian,
            'data' => $guests,
        ],
    ];
}

$scriptPath = realpath($_SERVER['SCRIPT_FILENAME'] ?? '');
$indexPath = realpath(__DIR__ . DIRECTORY_SEPARATOR . 'index.php');

if (PHP_SAPI !== 'cli' || $scriptPath === __FILE__ || ($indexPath !== false && $scriptPath === $indexPath)) {
    $response = handleGuestsRequest166($_SERVER['REQUEST_METHOD'] ?? 'GET', $_GET);

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
