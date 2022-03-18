# Code-InsiderAPI

Code Insider API

## Installation

Step 1 clone reposetory

```bash
git clone git@rendu-git.etna-alternance.net:module-8837/activity-48527/group-965705.git
```

## Setup and Start API

Step 1 edit env file with the correct login to your database

Step 2 Start migration and factory

```bash
php artisan migrate:fresh --seed
```

Step 3 Start API

```bash
php artisan serve
```

## How to get your API Token (Student or Company)

Get your Student Token:

Setp 1: Register

Use Register route "localhost:8000/api/registerStudent" and enter your information:     
    'email',
    'password',
    'firstname',
    'name',
    'birthday',
    'localization',
    'phone_number',
    'curriculum_year',
    'requested_remuneration',
    'skills',
    'contract_type',
    'contract_time',

Setp 2: Get your Api Token after login

Use auth route "localhost:8000/api/loginStudent" and enter your email and password

Get your Company Token

Setp 1: Register

Use Register route "localhost:8000/api/registerCompany" and enter your information:     
    'name',
    'localization',
    'description',
    'phone_number',
    'email',
    'password',

Setp 2: Get your Api Token after login

Use auth route "localhost:8000/api/loginCompany" and enter your email and password


## All API routes

All the route with a middlewear need a token

```bash
|Method    | Route                             |Desc                            |Security
| GET|HEAD | api/companies                     |get all companies               |none
| GET|HEAD | api/company/{id}                  |get company                     |none
| PUT      | api/company/{id}                  |update company                  |App\Http\Middleware\Authenticate:api_Company
| DELETE   | api/company/{id}                  |delete company                  |App\Http\Middleware\Authenticate:api_Company
| POST     | api/company/{id}/mail/{studentid} |post email                      |App\Http\Middleware\Authenticate:api_Company
| GET|HEAD | api/company/{id}/mails            |get all mails from one company  |App\Http\Middleware\Authenticate:api_Company
| POST     | api/company/{id}/post             |create post                     |App\Http\Middleware\Authenticate:api_Company
| DELETE   | api/company/{id}/post/{id2}       |delete post                     |App\Http\Middleware\Authenticate:api_Company
| GET|HEAD | api/company/{id}/post/{id2}       |get post                        |none
| PUT      | api/company/{id}/post/{id2}       |update post                     |App\Http\Middleware\Authenticate:api_Company
| GET|HEAD | api/company/{id}/posts            |get all posts                   |none
| POST     | api/loginCompany                  |login Company                   |none
| POST     | api/loginStudent                  |login Student                   |none
| GET|HEAD | api/posts                         |get all posts                   |none
| POST     | api/registerCompany               |register Company                |none
| POST     | api/registerStudent               |register Student                |none
| DELETE   | api/student/{id}                  |delete student                  |App\Http\Middleware\Authenticate:api_Company
| PUT      | api/student/{id}                  |update student                  |App\Http\Middleware\Authenticate:api_Company
| GET|HEAD | api/student/{id}                  |get student                     |none
| POST     | api/student/{id}/post/{id2}/like  |like post                       |none
| GET|HEAD | api/student/{id}/post/{id2}/like  |check if like or not            |none
| GET|HEAD | api/students                      |get all students                |none
```

## Features Test

To make your Features Test use:

Features tested : Register, User, Controller

```bash
php artisan test
```

## Contributor

clemen_r and sage_a
