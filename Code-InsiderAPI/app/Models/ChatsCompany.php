<?php

namespace App\Models;
use App\Models\Chatroom;


use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;



class ChatsCompany extends Model
{
    use HasFactory;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'chatroom_id',
        'company_id',
        'message_company',
    ];

    public function chats_students()
    {
        return $this->hasOne(ChatRoom::class);
    }
 
}
