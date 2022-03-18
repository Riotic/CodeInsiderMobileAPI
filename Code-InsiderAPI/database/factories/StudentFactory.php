<?php

namespace Database\Factories;

use Illuminate\Support\Str;
use Illuminate\Database\Eloquent\Factories\Factory;


class StudentFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'email' => $this->faker->unique()->safeEmail(),
            'password' => '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
            'firstname' => $this->faker->firstname(),
            'name' => $this->faker->name(),
            'birthday' => $this->faker->date('D-m-y','2012','1990'),
            'localization' => $this->faker->address(),
            'phone_number' => $this->faker->phoneNumber(),
            'curriculum_year' => $this->faker->year(),
            'requested_remuneration' => $this->faker->numberBetween(689,1500),
            'skills' => $this->faker->randomElement(['Laravel C C++', 'Android IOS PHP', 'Android IOS Javascript', 'IOS Symphony PHP']),
            'contract_type' => $this->faker->randomElement(['Alternance', 'Stage']),
            'contract_time' => $this->faker->numberBetween(6,10),
            'api_token' => Str::random(100),
        ];
    }
}