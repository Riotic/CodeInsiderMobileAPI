<?php

namespace Tests\Feature\Auth;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;
use Illuminate\Support\Str;

class RegistrationTest extends TestCase
{
    use RefreshDatabase;

    public function test_new_students_can_register()
    {
        $response = $this->post('/api/registerStudent', [
            
            'email' => 'test@example.com',
            'password' => 'password',
            'firstname' => 'Test Student',
            'name' => 'Test Student',
            'birthday' => 'Test birthday',
            'localization' => 'Test localization',
            'phone_number' => 'Test phone number',
            'curriculum_year' => 'Test curriculum year',
            'requested_remuneration' => 'Test remuneration',
            'skills' => 'Test skills',
            'contract_type' => 'Test contract type',
            'contract_time' => 'Test contract time',
        ]);
        
        $response->assertStatus(201);
    }

    public function test_new_company_can_register()
    {
        $response = $this->post('/api/registerCompany', [
            'name' => 'Test User',
            'description' => 'Test description',
            'localization' => 'Test localization',
            'phone_number' => 'test phone number',
            'email' => 'test@example.com',
            'password' => 'password',
        ]);

        $response->assertStatus(201);
    }
}
