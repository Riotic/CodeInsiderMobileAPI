<?php

namespace App\Http\Controllers\Auth;

use App\Models\Company;
use App\Models\Student;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use Illuminate\Validation\Rules;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class RegisteredUserController extends Controller
{
    /**
     * Handle an incoming registration request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function storeStudent(Request $request)
    {
        $student_rules = array(
          'email' => ['required', 'string', 'email', 'max:255', 'unique:students'],
          'password' => ['required', Rules\Password::defaults()],
          'firstname' => ['required', 'string', 'max:255'],
          'name' => ['required', 'string', 'max:255'],
          'birthday' => ['required', 'string', 'max:255'],
          'localization' => ['required', 'string', 'max:255'],
          'phone_number' => ['required', 'string', 'max:255'],
          'curriculum_year' => ['required', 'string', 'max:255'],
          'requested_remuneration' => ['required', 'string', 'max:255'],
          'skills' => ['required', 'string'],
          'contract_type' => ['required', 'string', 'max:255'],
          'contract_time' => ['required', 'string', 'max:255'],
          'api_token' => ['required', 'string', 'max:101']
        );

        $student_info = array (
          'email' => $request->email,
          'password' => Hash::make($request->password),
          'firstname' => $request->firstname,
          'name' => $request->name,
          'birthday' => $request->birthday,
          'localization' => $request->localization,
          'phone_number' => $request->phone_number,
          'curriculum_year' => $request->curriculum_year,
          'requested_remuneration' => $request->requested_remuneration,
          'skills' => $request->skills,
          'contract_type' => $request->contract_type,
          'contract_time' => $request->contract_time,
          'api_token' => Str::random(100),
        );

        $validator_student = Validator::make($student_info, $student_rules);

        if ($validator_student->fails()) {
            return response()->json([
              'errors' => $validator_student->errors(),
            ], 400);
        }

        Student::create($validator_student->validate());

        return response()->json([
          'success' => 'Utilsateur étudiant créé'
        ], 201);
    } 


    /**
     * Handle an incoming registration request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function storeCompany(Request $request)
    {
        //student info
        $company_rules = array(
          'name' => ['required', 'string', 'max:255'],
          'localization' => ['required', 'string', 'max:255'],
          'description' => ['required', 'string', 'max:255'],
          'phone_number' => ['required', 'string', 'max:255'],
          'email' => ['required', 'string', 'max:255','unique:companies'],
          'password' => ['required', Rules\Password::defaults()],
          'api_token' => ['required', 'string', 'max:101']
        );

        $company_info = array (
          'name' => $request->name,
          'localization' => $request->localization,
          'description' => $request->description,
          'phone_number' => $request->phone_number,
          'email' => $request->email,
          'password' => Hash::make($request->password),
          'api_token' => Str::random(100),
        );

        $validator_company = Validator::make($company_info, $company_rules);

        if ($validator_company->fails()) {
            return response()->json([
              'errors' => $validator_company->errors(),
            ], 400);
        }

        Company::create($validator_company->validated());

        return response()->json([
          'success' => 'Utilsateurs compagnie créé'
        ], 201);
    } 
}
