<?php

namespace App\Models;
use App\Models\Chatroom;


use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use App\Models\Tag;

class ChatsStudent extends Model
{
    use HasFactory;
            /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'message_student',
        'chatroom_id',
        'student_id',
    ];

    public function chats_room()
    {
        return $this->hasOne(ChatRoom::class);
    }
}
