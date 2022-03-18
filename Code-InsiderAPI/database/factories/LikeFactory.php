<?php

namespace Database\Factories;

use App\Models\Post;
use App\Models\Student;
use Illuminate\Database\Eloquent\Factories\Factory;

class LikeFactory extends Factory
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
            'post_id'=> Post::factory(),
            'liked'=> $this->faker->randomElement([0,1]),
        ];
    }
}
