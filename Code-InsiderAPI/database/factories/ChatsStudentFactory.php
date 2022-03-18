<?php

namespace Database\Factories;

use App\Models\Student;
use App\Models\Chatroom;
use Illuminate\Database\Eloquent\Factories\Factory;

class ChatsStudentFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'message_student' => $this->faker->randomElement(['Bonjour, bien sur, ca serait pour quel jour?', 'Bonjour et merci pour votre réponse',]),
            'student_id'=> Student::factory(),
            'chatroom_id' => Chatroom::factory(),
        ];
    }
}
