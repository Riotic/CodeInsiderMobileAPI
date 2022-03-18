package com.codeinsider.Interface;

import com.codeinsider.Model.EditPost;
import com.codeinsider.Model.EditPostEntreprise;
import com.codeinsider.Model.EditPostEtudiant;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.EntrepriseT;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.Model.LoginRequest;
import com.codeinsider.Model.LoginResponseEntreprise;
import com.codeinsider.Model.LoginResponseEtudiant;
import com.codeinsider.Model.Post;
import com.codeinsider.Model.PostEntreprise;
import com.codeinsider.Model.PostEtudiant;
import com.codeinsider.Model.ResponsePost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("posts")
    Call<List<Post>> getAllPosts();

    @GET("company/{id}/posts")
    Call<List<Post>> getOneEntrepriseAllPosts(@Path("id") String id);

    @GET("company/{company_id}/post/{post_id}")
    Call<Post> getOnePost(@Path("company_id") String company_id, @Path("post_id") String post_id);

    @GET("students")
    Call<List<Etudiant>> getAllStudents();

    @POST("loginCompany")
    Call<LoginResponseEntreprise> authCompany(@Body LoginRequest loginRequest);

    @POST("loginStudent")
    Call<LoginResponseEtudiant> authStudent(@Body LoginRequest loginRequest);

    @POST("registerCompany")
    Call<Entreprise> createCompany(@Body PostEntreprise user);

    @POST("registerStudent")
    Call<Etudiant> createStudent(@Body PostEtudiant user);

    @GET("student/{id}")
    Call<Etudiant> getStudent(@Path("id") String id);

    @GET("company/{id}")
    Call<Entreprise> getCompany(@Path("id") String id);

    @GET("company/{id}")
    Call<EntrepriseT> getCompanyT(@Path("id") String id);

    //TOKEN NEEDED

    @Headers("Accept: application/json")
    @POST("company/{id}/post")
    Call<ResponsePost> createPost(@Header("Authorization") String authToken, @Body EditPost post, @Path("id") String company_id);

    @Headers("Accept: application/json")
    @PUT("company/{id}/post/{id2}")
    Call<ResponsePost> editPost(@Header("Authorization") String authToken, @Body EditPost post, @Path("id") String company_id, @Path("id2") String post_id);

    @Headers("Accept: application/json")
    @DELETE("company/{id}/post/{id2}")
    Call<ResponsePost> deletePost(@Header("Authorization") String authToken, @Path("id") String company_id, @Path("id2") String post_id);

    @Headers("Accept: application/json")
    @PUT("student/{id}")
    Call<LoginResponseEtudiant> editStudent(@Header("Authorization") String authToken, @Body EditPostEtudiant student, @Path("id") String id);

    @Headers("Accept: application/json")
    @PUT("company/{id}")
    Call<LoginResponseEntreprise> editCompany(@Header("Authorization") String authToken, @Body EditPostEntreprise company, @Path("id") String id);

    @Headers("Accept: application/json")
    @DELETE("student/{id}")
    Call<LoginResponseEtudiant> deleteStudent(@Header("Authorization") String authToken, @Path("id") String id);

    @Headers("Accept: application/json")
    @DELETE("company/{id}")
    Call<LoginResponseEntreprise> deleteCompany(@Header("Authorization") String authToken, @Path("id") String id);


}
