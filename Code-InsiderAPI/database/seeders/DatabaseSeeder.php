<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        \App\Models\Like::factory(10)->create();
        \App\Models\Email::factory(5)->create();
        \App\Models\ChatsStudent::factory(5)->create();
        \App\Models\ChatsCompany::factory(5)->create();
    }
}
