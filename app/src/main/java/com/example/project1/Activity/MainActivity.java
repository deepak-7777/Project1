package com.example.project1.Activity;

import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; //  NEW
import androidx.viewpager2.widget.ViewPager2;

import com.example.project1.Adapter.BannerAdapter;
import com.example.project1.Adapter.CategoryAdapter;
import com.example.project1.Model.CategoryModel;
import com.example.project1.Fragment.FavouriteFragment;
import com.example.project1.Fragment.OrderFragment;
import com.example.project1.Fragment.ProfileFragment;
import com.example.project1.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] imageUrls = {
            "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
            "https://res.cloudinary.com/dmsbaxkle/image/upload/v1753965935/pizza3_e4jpgu.png",       //  Banner image
            "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
            "https://images.unsplash.com/photo-1521295121783-8a321d551ad2",
            "https://images.unsplash.com/photo-1507525428034-b723cf961d3e"
    };

    //  New category images (for horizontal scroll)
//    String[] categoryImageUrls = {
//            "https://res.cloudinary.com/dmsbaxkle/image/upload/v1753965935/pizza3_e4jpgu.png",           // category image   ya popular
//            "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
//            "https://images.unsplash.com/photo-1521295121783-8a321d551ad2",
//            "https://images.unsplash.com/photo-1507525428034-b723cf961d3e"
//    };

    List<CategoryModel> categoryList = List.of(
            new CategoryModel(
                    "https://res.cloudinary.com/dmsbaxkle/image/upload/v1753965935/pizza3_e4jpgu.png",
                    "Cheesy Pizza",
                    "Delicious cheesy pizza with extra cheese and crispy crust.",
                    "₹199",
                    "₹299"
            ),
            new CategoryModel(
                    "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
                    "Fresh Salad",
                    "Healthy green salad with fresh veggies and olive oil.",
                    "₹120",
                    "₹180"
            ),
            new CategoryModel(
                    "https://images.unsplash.com/photo-1521295121783-8a321d551ad2",
                    "Burger Delight",
                    "Juicy beef burger with cheese and french fries.",
                    "₹150",
                    "₹220"
            ),
            new CategoryModel(
                    "https://images.unsplash.com/photo-1507525428034-b723cf961d3e",
                    "Beach View Meal",
                    "Seafood platter with sunset view vibes.",
                    "₹499",
                    "₹699"
            )
    );
    ImageView cartBtn;
    LinearLayout noConnectionLayout;
    ScrollView homeScrollView;
    ViewPager2 viewPager;
    RecyclerView categoryView; //  NEW
    Button retryButton;
    ChipNavigationBar chipNavigationBar;

    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentIndex = 1;
    private List<String> infiniteList;
    InterstitialAd mIinterstitialAd;     /// for Ad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noConnectionLayout = findViewById(R.id.noConnectionLayout);
        homeScrollView = findViewById(R.id.homeScrollView);
        viewPager = findViewById(R.id.viewPager);
        retryButton = findViewById(R.id.retryButton);
        categoryView = findViewById(R.id.categoryView); //  NEW
        chipNavigationBar = findViewById(R.id.buttonNavigation);

        retryButton.setOnClickListener(v -> checkAndLoadImages());

        checkAndLoadImages(); //  handles both banner and category
        bottomNavigation();

//        cartBtn = findViewById(R.id.cartBtn);
//        cartBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, CartActivity.class);
//            startActivity(intent);
//        });


        ///    InterstitialAd  (adRequest)
       AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.interstitial_ad_unit_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mIinterstitialAd = interstitialAd;
                mIinterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {              ///  ek intent se dusre intent mai jane ke lie isme code likhe
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }


                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        mIinterstitialAd = null;
                    }

                });
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Error", loadAdError.toString());
            }

        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mIinterstitialAd != null) {
                    mIinterstitialAd.show(MainActivity.this);
                } else {
                    Log.e("Adpending", "Ad is not ready");
                }
            }
        }, 10000);         //  yaha tak Ad ke liye


    }


    private void bottomNavigation() {
        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment selectedFragment = null;

                if (id == R.id.profile) {
                    selectedFragment = new ProfileFragment();
                    homeScrollView.setVisibility(View.GONE);
                    findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
                } else if (id == R.id.cart) {
                    selectedFragment = new OrderFragment();
                    homeScrollView.setVisibility(View.GONE);
                    findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
                } else if (id == R.id.favourite) {
                    selectedFragment = new FavouriteFragment();
                    homeScrollView.setVisibility(View.GONE);
                    findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
                } else if (id == R.id.home) {
                    homeScrollView.setVisibility(View.VISIBLE);
                    findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }
            }
        });
    }

    private void checkAndLoadImages() {
        if (isConnected()) {
            noConnectionLayout.setVisibility(View.GONE);
            homeScrollView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            chipNavigationBar.setVisibility(View.VISIBLE); //  Show navigation bar

            // Load Banner
            infiniteList = prepareInfiniteImageList();
            BannerAdapter adapter = new BannerAdapter(this, infiniteList.toArray(new String[0]));
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(1, false); // Start from real first image

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    currentIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        if (currentIndex == 0) {
                            viewPager.setCurrentItem(infiniteList.size() - 2, false);
                        } else if (currentIndex == infiniteList.size() - 1) {
                            viewPager.setCurrentItem(1, false);
                        }
                    }
                }
            });

            startAutoScroll();

            CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList);
            categoryView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            categoryView.setAdapter(categoryAdapter);

        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
            homeScrollView.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE); // optional
            chipNavigationBar.setVisibility(View.GONE); //  Hide navigation bar
            stopAutoScroll();
        }
    }


    private List<String> prepareInfiniteImageList() {
        List<String> list = new ArrayList<>();
        if (imageUrls.length == 0) return list;

        list.add(imageUrls[imageUrls.length - 1]); // fake last at beginning
        Collections.addAll(list, imageUrls);       // real items
        list.add(imageUrls[0]);                    // fake first at end

        return list;
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    private void startAutoScroll() {
        stopAutoScroll();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                viewPager.setCurrentItem(currentIndex, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void stopAutoScroll() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoScroll();
    }
}

