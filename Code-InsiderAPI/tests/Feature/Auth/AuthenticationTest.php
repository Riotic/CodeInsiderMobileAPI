<?php

namespace Tests\Feature\Auth;

use App\Models\Company;
use App\Models\Student;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class AuthenticationTest extends TestCase
{
    use RefreshDatabase;

    //test des routes de connections Students
    public function test_students_can_authenticate_using_the_login_api()
    {
        $student = Student::factory()->create();

        $response = $this->post('/api/loginStudent', [
            'email' => $student->email,
            'password' => 'password',
        ]);

        $response->assertStatus(200);
    }

    public function test_students_can_not_authenticate_with_invalid_password()
    {
        $student = Student::factory()->create();

        $this->post('/api/loginStudent', [
            'email' => $student->email,
            'password' => 'wrong-password',
        ]);

        $this->assertGuest();
    }

    //test des routes de connections Company
    public function test_company_can_authenticate_using_the_login_api()
    {
        $company = Company::factory()->create();

        $response = $this->post('/api/loginCompany', [
            'email' => $company->email,
            'password' => 'password',
        ]);

        $response->assertStatus(200);
    }

    public function test_company_can_not_authenticate_with_invalid_password()
    {
        $company = Company::factory()->create();

        $this->post('/api/loginCompany', [
            'email' => $company->email,
            'password' => 'wrong-password',
        ]);

        $this->assertGuest();
    }
}
