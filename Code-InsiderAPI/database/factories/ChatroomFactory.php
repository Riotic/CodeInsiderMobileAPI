<?php

namespace Database\Factories;

use App\Models\Company;
use App\Models\Student;
use Illuminate\Database\Eloquent\Factories\Factory;

class ChatroomFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'student_id'=> Student::factory(),
            'company_id'=> Company::factory(),
        ];
    }
}
