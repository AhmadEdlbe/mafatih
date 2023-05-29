package com.ahmadedlbe.mafatih.net;

import com.ahmadedlbe.mafatih.model.AccountsApiResponse;
import com.ahmadedlbe.mafatih.model.Cart;
import com.ahmadedlbe.mafatih.model.CartApiResponse;
import com.ahmadedlbe.mafatih.model.Favorite;
import com.ahmadedlbe.mafatih.model.FavoriteApiResponse;
import com.ahmadedlbe.mafatih.model.History;
import com.ahmadedlbe.mafatih.model.HistoryApiResponse;
import com.ahmadedlbe.mafatih.model.Image;
import com.ahmadedlbe.mafatih.model.LoginApiResponse;
import com.ahmadedlbe.mafatih.model.NewsFeedResponse;
import com.ahmadedlbe.mafatih.model.OrderApiResponse;
import com.ahmadedlbe.mafatih.model.AllOrdersApiResponse;
import com.ahmadedlbe.mafatih.model.Ordering;
import com.ahmadedlbe.mafatih.model.Otp;
import com.ahmadedlbe.mafatih.model.ProductApiResponse;
import com.ahmadedlbe.mafatih.model.RegisterApiResponse;
import com.ahmadedlbe.mafatih.model.Review;
import com.ahmadedlbe.mafatih.model.ReviewApiResponse;
import com.ahmadedlbe.mafatih.model.Shipping;
import com.ahmadedlbe.mafatih.model.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register")
    Call<RegisterApiResponse> createUser(@Body User user);

    @GET("users/login")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);

    @DELETE("users/{userId}")
    Call<ResponseBody> deleteAccount(@Header("authorization") String token , @Path("userId") int userId);

    @Multipart
    @PUT("users/upload")
    Call<ResponseBody> uploadPhoto(@Header("authorization") String token , @Part MultipartBody.Part userPhoto, @Part("id") RequestBody userId);

    @PUT("users/info")
    Call<ResponseBody> updatePassword(@Header("authorization") String token, @Query("password") String password, @Query("id") int userId);

    @Multipart
    @POST("products/insert")
    Call<ResponseBody> insertProduct(@Header("authorization") String token, @PartMap Map<String, RequestBody> productInfo, @Part MultipartBody.Part image);

    @GET("users/getImage")
    Call<Image> getUserImage(@Query("id") int userId);

    @GET("users/otp")
    Call<Otp> getOtp(@Header("authorization") String token, @Query("email") String email);

    @GET("products")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category, @Query("userId") int userId,@Query("page") int page);

    @GET("products/search")
    Call<ProductApiResponse> searchForProduct(@Query("q") String keyword, @Query("userId") int userId);

    @POST("favorites/add")
    Call<ResponseBody> addFavorite(@Body Favorite favorite);

    @DELETE("favorites/remove")
    Call<ResponseBody> removeFavorite(@Query("userId") int userId, @Query("productId") int productId);

    @GET("favorites")
    Call<FavoriteApiResponse> getFavorites(@Query("userId") int userId);

    @POST("carts/add")
    Call<ResponseBody> addToCart(@Body Cart cart);

    @DELETE("carts/remove")
    Call<ResponseBody> removeFromCart(@Query("userId") int userId, @Query("productId") int productId);

    @GET("carts")
    Call<CartApiResponse> getProductsInCart(@Query("userId") int userId);

    @POST("history/add")
    Call<ResponseBody> addToHistory(@Body History history);

    @DELETE("history/remove")
    Call<ResponseBody> removeAllFromHistory();

    @GET("history")
    Call<HistoryApiResponse> getProductsInHistory(@Query("userId") int userId, @Query("page") int page);

    @POST("review/add")
    Call<ResponseBody> addReview(@Body Review review);

    @GET("review")
    Call<ReviewApiResponse> getAllReviews(@Query("productId") int productId);

    @GET("posters")
    Call<NewsFeedResponse> getPosters();

    @GET("orders/get")
    Call<OrderApiResponse> getOrders(@Query("userId") int userId);

    @GET("orders/all")
    Call<AllOrdersApiResponse> getAllOrders();

    @GET("users/users")
    Call<AccountsApiResponse> getAccounts();

    @POST("address/add")
    Call<ResponseBody> addShippingAddress(@Body Shipping shipping);

    @POST("orders/add")
    Call<ResponseBody> orderProduct(@Body Ordering ordering);
}
