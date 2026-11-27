<?php
declare(strict_types=1);

require_once __DIR__ . '/guests.php';

function assertSame166(mixed $expected, mixed $actual, string $label): void
{
    if ($expected !== $actual) {
        fwrite(STDERR, "FAIL [{$label}]: expected " . var_export($expected, true) . ', got ' . var_export($actual, true) . PHP_EOL);
        exit(1);
    }
}

// 1. Default GET returns all guests sorted descending with accurate totals
$all = handleGuestsRequest166('GET', []);
assertSame166(200, $all['status_code'], 'default status');
assertSame166('success', $all['body']['status'], 'default body status');
assertSame166(6, $all['body']['total_semua_166'], 'default total_semua_166');
assertSame166(3, $all['body']['total_harian_166'], 'default total_harian_166');
assertSame166(6, count($all['body']['data']), 'default data count');
assertSame166(true, $all['body']['data'][0]['timestamp_166'] >= $all['body']['data'][1]['timestamp_166'], 'sorted descending');
assertSame166(true, is_int($all['body']['data'][0]['timestamp_166']), 'timestamp is int');

// 2. Filter by user_id isolates records per user
$user1 = handleGuestsRequest166('GET', ['user_id' => '1']);
assertSame166(200, $user1['status_code'], 'user_id=1 status');
assertSame166(5, $user1['body']['total_semua_166'], 'user_id=1 total_semua');
assertSame166(3, $user1['body']['total_harian_166'], 'user_id=1 total_harian');

$user2 = handleGuestsRequest166('GET', ['user_id' => '2']);
assertSame166(1, $user2['body']['total_semua_166'], 'user_id=2 total_semua');
assertSame166(0, $user2['body']['total_harian_166'], 'user_id=2 total_harian');

// 3. Search matches nama, alamat, keperluan (case-insensitive) and returns JSON array list
$search = handleGuestsRequest166('GET', ['user_id' => '1', 'q' => 'malang']);
assertSame166(200, $search['status_code'], 'search status');
assertSame166(1, count($search['body']['data']), 'search count');
assertSame166(0, array_key_first($search['body']['data']), 'search reindexed for JSON array');
assertSame166('Siti Rahmawati', $search['body']['data'][0]['nama_166'], 'search matched alamat');
assertSame166(5, $search['body']['total_semua_166'], 'totals unaffected by search');

// 4. Detail by id & not found
$detail = handleGuestsRequest166('GET', ['id' => '1']);
assertSame166(200, $detail['status_code'], 'detail status');
assertSame166('Budi Santoso', $detail['body']['data']['nama_166'], 'detail nama');

$missing = handleGuestsRequest166('GET', ['id' => '999']);
assertSame166(404, $missing['status_code'], 'missing id returns 404');

$wrongUser = handleGuestsRequest166('GET', ['id' => '6', 'user_id' => '1']);
assertSame166(404, $wrongUser['status_code'], 'other user guest returns 404');

// 5. Input validation & HTTP method guards
assertSame166(400, handleGuestsRequest166('GET', ['id' => 'abc'])['status_code'], 'invalid id returns 400');
assertSame166(400, handleGuestsRequest166('GET', ['user_id' => '0'])['status_code'], 'zero user_id returns 400');
assertSame166(400, handleGuestsRequest166('GET', ['q' => ['x']])['status_code'], 'array q returns 400');
assertSame166(405, handleGuestsRequest166('POST', [])['status_code'], 'POST returns 405');
assertSame166(204, handleGuestsRequest166('OPTIONS', [])['status_code'], 'OPTIONS returns 204');

echo "ALL TESTS PASSED (18 assertions)" . PHP_EOL;
