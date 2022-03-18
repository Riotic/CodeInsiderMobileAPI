<?php

namespace Database\Factories;

use App\Models\Company;
use App\Models\Chatroom;
use Illuminate\Database\Eloquent\Factories\Factory;

class ChatsCompanyFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'message_company' => $this->faker->randomElement(['Bonjour, nous avons vu l\'intérêt que vous portez à notre entreprise, êtes vous disponible pour un entretien ?', 'Bonjour, nous sommes dans le regret de vous dire que votre candidature n\'a pas été retenue',]),
            'company_id'=> Company::factory(),
            'chatroom_id'=> Chatroom::factory(),
        ];
    }
}
