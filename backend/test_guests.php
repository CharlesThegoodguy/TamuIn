<?php
declare(strict_types=1);

require_once __DIR__ . '/guests.php';

function assertSame159(mixed $expected, mixed $actual, string $label): void
{
    if ($expected !== $actual) {
        fwrite(STDERR, "FAIL [{$label}]: expected " . var_export($expected, true) . ', got ' . var_export($actual, true) . PHP_EOL);
        exit(1);
    }
}

// 1. Default GET returns all guests sorted descending with accurate totals
$all = handleGuestsRequest159('GET', []);
assertSame159(200, $all['status_code'], 'default status');
assertSame159('success', $all['body']['status'], 'default body status');
assertSame159(6, $all['body']['total_semua_159'], 'default total_semua_159');
assertSame159(3, $all['body']['total_harian_159'], 'default total_harian_159');
assertSame159(6, count($all['body']['data']), 'default data count');
assertSame159(true, $all['body']['data'][0]['timestamp_159'] >= $all['body']['data'][1]['timestamp_159'], 'sorted descending');
assertSame159(true, is_int($all['body']['data'][0]['timestamp_159']), 'timestamp is int');

// 2. Filter by user_id isolates records per user
$user1 = handleGuestsRequest159('GET', ['user_id' => '1']);
assertSame159(200, $user1['status_code'], 'user_id=1 status');
assertSame159(5, $user1['body']['total_semua_159'], 'user_id=1 total_semua');
assertSame159(3, $user1['body']['total_harian_159'], 'user_id=1 total_harian');

$user2 = handleGuestsRequest159('GET', ['user_id' => '2']);
assertSame159(1, $user2['body']['total_semua_159'], 'user_id=2 total_semua');
assertSame159(0, $user2['body']['total_harian_159'], 'user_id=2 total_harian');

// 3. Search matches nama, alamat, keperluan (case-insensitive) and returns JSON array list
$search = handleGuestsRequest159('GET', ['user_id' => '1', 'q' => 'malang']);
assertSame159(200, $search['status_code'], 'search status');
assertSame159(1, count($search['body']['data']), 'search count');
assertSame159(0, array_key_first($search['body']['data']), 'search reindexed for JSON array');
assertSame159('Siti Rahmawati', $search['body']['data'][0]['nama_159'], 'search matched alamat');
assertSame159(5, $search['body']['total_semua_159'], 'totals unaffected by search');

// 4. Detail by id & not found
$detail = handleGuestsRequest159('GET', ['id' => '1']);
assertSame159(200, $detail['status_code'], 'detail status');
assertSame159('Budi Santoso', $detail['body']['data']['nama_159'], 'detail nama');

$missing = handleGuestsRequest159('GET', ['id' => '999']);
assertSame159(404, $missing['status_code'], 'missing id returns 404');

$wrongUser = handleGuestsRequest159('GET', ['id' => '6', 'user_id' => '1']);
assertSame159(404, $wrongUser['status_code'], 'other user guest returns 404');

// 5. Input validation & HTTP method guards
assertSame159(400, handleGuestsRequest159('GET', ['id' => 'abc'])['status_code'], 'invalid id returns 400');
assertSame159(400, handleGuestsRequest159('GET', ['user_id' => '0'])['status_code'], 'zero user_id returns 400');
assertSame159(400, handleGuestsRequest159('GET', ['q' => ['x']])['status_code'], 'array q returns 400');
assertSame159(405, handleGuestsRequest159('POST', [])['status_code'], 'POST returns 405');
assertSame159(204, handleGuestsRequest159('OPTIONS', [])['status_code'], 'OPTIONS returns 204');

echo "ALL TESTS PASSED (18 assertions)" . PHP_EOL;
