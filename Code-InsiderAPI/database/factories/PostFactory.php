<?php

namespace Database\Factories;

use App\Models\Company;
use Illuminate\Database\Eloquent\Factories\Factory;

class PostFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'company_id'=> Company::factory(),
            'job_name' => $this->faker->jobTitle(),
            'contract_type' => $this->faker->randomElement(['Alternance', 'Stage',]),
            'description' => $this->faker->text(50),
        ];
    }
}
