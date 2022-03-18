<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateStudentsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('students', function (Blueprint $table) {
            $table->id();
            $table->string('email')->unique();
            $table->string('password');
            $table->string('firstname');
            $table->string('name');
            $table->string('birthday');
            $table->string('localization');
            $table->string('phone_number');
            $table->string('curriculum_year');
            $table->string('requested_remuneration');
            $table->text('skills');
            $table->string('contract_type');
            $table->string('contract_time');
            $table->string('api_token', 100)->nullable()->unique()->default(NULL);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('students');
    }
}
