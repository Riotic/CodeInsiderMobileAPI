<?php

namespace App\Models;
use App\Models\ChatsCompany;
use App\Models\ChatsStudent;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use App\Models\Tag;

class Chatroom extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'student_id',
        'company_id',
    ];
    use HasFactory;
    public function chats_students()
    {
        return $this->hasMany(ChatsStudent::class);
    }
 
    /**
     * Get all of the videos that are assigned this tag.
     */
    public function chats_companies()
    {
        return $this->hasMany(ChatsCompany::class);
    }
}
