package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.R.id.action_ShowAccount;
import static com.ahmadedlbe.mafatih.R.id.action_addProduct;
import static com.ahmadedlbe.mafatih.R.id.action_all_orders;
import static com.ahmadedlbe.mafatih.storage.LanguageUtils.loadLocale;
import static com.ahmadedlbe.mafatih.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.ahmadedlbe.mafatih.utils.Constant.CATEGORY;
import static com.ahmadedlbe.mafatih.utils.Constant.LOCALHOST;
import static com.ahmadedlbe.mafatih.utils.Constant.PRODUCT;
import static com.ahmadedlbe.mafatih.utils.Constant.READ_EXTERNAL_STORAGE_CODE;
import static com.ahmadedlbe.mafatih.utils.ImageUtils.getImageUri;
import static com.ahmadedlbe.mafatih.utils.ImageUtils.getRealPathFromURI;
import static com.ahmadedlbe.mafatih.utils.InternetUtils.isNetworkConnected;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.ProductAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityProductBinding;
import com.ahmadedlbe.mafatih.model.Product;
import com.ahmadedlbe.mafatih.receiver.NetworkChangeReceiver;
import com.ahmadedlbe.mafatih.storage.LoginUtils;
import com.ahmadedlbe.mafatih.utils.FlagsManager;
import com.ahmadedlbe.mafatih.utils.OnNetworkListener;
import com.ahmadedlbe.mafatih.utils.Slide;
import com.ahmadedlbe.mafatih.viewmodel.HistoryViewModel;
import com.ahmadedlbe.mafatih.viewmodel.ProductViewModel;
import com.ahmadedlbe.mafatih.viewmodel.UploadPhotoViewModel;
import com.ahmadedlbe.mafatih.viewmodel.UserImageViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductActivity";
    private ActivityProductBinding binding;
    private ProductAdapter doorhandleAdapter;
    private ProductAdapter hardwareAdapter;
    private ProductAdapter historyAdapter;

    private ProductViewModel productViewModel;
    private HistoryViewModel historyViewModel;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private UserImageViewModel userImageViewModel;

    private Snackbar snack;

    private CircleImageView circleImageView;

    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadDoorHandles("Door Handle", userID);
        productViewModel.loadHardwares("Hardware",userID);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.loadHistory(userID);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        userImageViewModel = ViewModelProviders.of(this).get(UserImageViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllDoorHandles.setOnClickListener(this);
        binding.included.content.txtSeeAllHardwares.setOnClickListener(this);
        binding.included.content.txtCash.setOnClickListener(this);
        binding.included.content.txtReturn.setOnClickListener(this);
        binding.included.txtSearch.setOnClickListener(this);

        setUpViews();

        getDoorHandles();
        getHardwares();
        getHistory();
        getUserImage();

        flipImages(Slide.getSlides());

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.included.content.listOfDoorHandles.setHasFixedSize(true);
        binding.included.content.listOfDoorHandles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfDoorHandles.setItemAnimator(null);

        binding.included.content.listOfHardwares.setHasFixedSize(true);
        binding.included.content.listOfHardwares.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfHardwares.setItemAnimator(null);

        binding.included.content.historyList.setHasFixedSize(true);
        binding.included.content.historyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.historyList.setItemAnimator(null);

        hardwareAdapter = new ProductAdapter(this, this);
        doorhandleAdapter = new ProductAdapter(this, this);
        historyAdapter = new ProductAdapter(this, this);

        if (FlagsManager.getInstance().isHistoryDeleted()) {
            binding.included.content.textViewHistory.setVisibility(View.GONE);
        }
    }

    private void getDoorHandles() {
        if (isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, products -> doorhandleAdapter.submitList(products));

            binding.included.content.listOfDoorHandles.setAdapter(doorhandleAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getHardwares() {
        if (isNetworkConnected(this)) {
            productViewModel.hardwarePagedList.observe(this, products -> hardwareAdapter.submitList(products));

            binding.included.content.listOfHardwares.setAdapter(hardwareAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getHistory() {
        if (isNetworkConnected(this)) {
            historyViewModel.historyPagedList.observe(this, products -> {
                binding.included.content.historyList.setAdapter(historyAdapter);
                historyAdapter.submitList(products);
                historyAdapter.notifyDataSetChanged();
                
                products.addWeakCallback(null, productCallback);
            });
        } else {
            showOrHideViews(View.INVISIBLE);
            binding.included.content.textViewHistory.setVisibility(View.GONE);
            showSnackBar();
        }

    }

    private void flipImages(List<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.included.content.imageSlider.addView(imageView);
        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllDoorHandles:
                Intent doorhandleIntent = new Intent(this, AllDoorHandlesActivity.class);
                startActivity(doorhandleIntent);
                break;
            case R.id.txtSeeAllHardwares:
                Intent hardwareIntent = new Intent(this, AllHardwaresActivity.class);
                startActivity(hardwareIntent);
                break;
            case R.id.profile_image:
                showCustomAlertDialog();
                break;
            case R.id.txtCash:
                showNormalAlertDialog(getString(R.string.cash));
                break;
            case R.id.txtReturn:
                showNormalAlertDialog(getString(R.string.returnProduct));
                break;
            case R.id.txtSearch:
                Intent searchIntent = new Intent(ProductActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            default: // Should not get here
        }
    }

    private void showNormalAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_image_dialog);

        Button takePicture = dialog.findViewById(R.id.takePicture);
        Button useGallery = dialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(v -> {
            launchCamera();
            dialog.cancel();
        });

        useGallery.setOnClickListener(v -> {
            getImageFromGallery();
            dialog.cancel();
        });

        dialog.show();
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                    getImageFromGallery.launch(chooserIntent);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }

    private void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getImageFromCamera.launch(cameraIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> getImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    circleImageView.setImageURI(imageUri);

                    String filePath = getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromGallery: " + filePath);

                    uploadPhoto(filePath);
                }
            });

    ActivityResultLauncher<Intent> getImageFromCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    circleImageView.setImageBitmap(photo);

                    Uri imageUri = getImageUri(this, photo);
                    String filePath = getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromCamera: " + filePath);

                    uploadPhoto(filePath);
                }
            });


    private void uploadPhoto(String pathname) {
        uploadPhotoViewModel.uploadPhoto(pathname).observe(this, responseBody -> {
            Log.d(TAG, "Image Uploaded");
            getUserImage();
        });
    }

    private void getUserImage() {
        userImageViewModel.getUserImage(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, response -> {
            Log.d(TAG,  "getUserImage");

            if (response != null) {
                String imageUrl = LOCALHOST + response.getImagePath().replaceAll("\\\\", "/");

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .apply(options)
                        .into(circleImageView);
            }
        });
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        showOrHideViews(View.VISIBLE);
        getDoorHandles();
        getHardwares();
        getHistory();
        Log.d(TAG, "onNetworkConnected");
        getUserImage();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem addMenu = menu.findItem(action_addProduct);
        boolean isAdmin = LoginUtils.getInstance(this).getUserInfo().isAdmin();
        addMenu.setVisible(isAdmin);
        MenuItem addMenu2 = menu.findItem(action_ShowAccount);
        addMenu2.setVisible(isAdmin);
        MenuItem addMenu3 = menu.findItem(action_all_orders);
        addMenu3.setVisible(isAdmin);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            case action_ShowAccount:
                Intent showIntent = new Intent(this, ShowAccountsActivity.class);
                startActivity(showIntent);
                return true;
            case action_all_orders:
                Intent ordersIntent = new Intent(this, AllOrdersActivity.class);
                startActivity(ordersIntent);
                return true;
            case action_addProduct:
                Intent addProductIntent = new Intent(this, AddProductActivity.class);
                startActivity(addProductIntent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewDoorHandles.setVisibility(view);
        binding.included.content.txtSeeAllDoorHandles.setVisibility(view);
        binding.included.content.textViewHardwares.setVisibility(view);
        binding.included.content.txtSeeAllHardwares.setVisibility(view);
        binding.included.content.txtCash.setVisibility(view);
        binding.included.content.txtReturn.setVisibility(view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

               if (id == R.id.nav_doorhandle) {
            goToCategoryActivity("Door Handle");
        } else if (id == R.id.nav_hardware) {
            goToCategoryActivity("Hardware");
        } else if (id == R.id.nav_trackOrder) {
            Intent orderIntent = new Intent(this, OrdersActivity.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_newsFeed) {
            Intent newsFeedIntent = new Intent(this, NewsFeedActivity.class);
            startActivity(newsFeedIntent);
        } else if (id == R.id.nav_wishList) {
            Intent wishListIntent = new Intent(this, WishListActivity.class);
            startActivity(wishListIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToCategoryActivity(String category) {
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(CATEGORY, category);
        startActivity(categoryIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this,R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }


    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.invalidate();
        getDoorHandles();
        getHardwares();
        historyViewModel.invalidate();
        getHistory();
    }

    private final PagedList.Callback productCallback = new PagedList.Callback() {
        @Override
        public void onChanged(int position, int count) {
            Log.d(TAG, "onChanged: "+ count);
        }

        @Override
        public void onInserted(int position, int count) {
            Log.d(TAG, "onInserted: "+ count);
            if (count != 0) {
                binding.included.content.textViewHistory.setVisibility(View.VISIBLE);
                historyAdapter.notifyOnInsertedItem(position);
                getHistory();
            }
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.d(TAG, "onRemoved: "+ count);
        }
    };

}
