# ads-sdk

To get a Git project into your build:
Step 1. Add the JitPack repository to your build file

========= Features ==========

          ==> For Banner, Interstitial, and Native ads, you can use multiple ad IDs; 
          ==> You can set interstitial ads to appear periodically.
          

========= Gradle ==========

1). Add it in your root build.gradle at the end of repositories:

          allprojects {
              repositories {
                     maven { url 'https://jitpack.io' }
            }
          }
          
2). Add the dependency

==> FOR PRELOADED ADS

       implementation 'com.github.InfyOmLabs:ads-sdk:1.0.8'
        
==> FOR SIMPLE ADS

       implementation 'com.github.InfyOmLabs:ads-sdk:1.3.6'  
     
==> Pre Interstitital Ads

     // 1 = admob id
     
    @Override
    protected void onResume() {
        InfyOmAds.loadPreInterstitial(1,this);
        super.onResume();
    }
         
       
===> FOR BANNER IN APPLICATION CLASS


    public class MyApplication extends Application implements ActivityLifecycleCallbacks {

            private Activity currentActivity;

           @Override
           public void onCreate() {
              super.onCreate();
              this.registerActivityLifecycleCallbacks(this);

           }

           @Override
           public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
               currentActivity = activity;
            }

            @Override
           public void onActivityStarted(@NonNull Activity activity) {}

            @Override
           public void onActivityResumed(@NonNull Activity activity) {
              AdBanner.resumeAdView();

          }

           @Override
            public void onActivityPaused(@NonNull Activity activity) {
                if (AdBanner.getCurrentActivity() != null && (AdBanner.getCurrentActivity() == currentActivity)) {
                  AdBanner.pauseAdView();
              }
           }

           @Override
           public void onActivityStopped(@NonNull Activity activity) {
          }

           @Override
          public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
           }

          @Override
           public void onActivityDestroyed(@NonNull Activity activity) {
             if (AdBanner.getCurrentActivity() != null && (AdBanner.getCurrentActivity() == currentActivity)) {
                      AdBanner.destroyAdView();
                     }
          }

      }
       
        
        
 ===== color guide ====== 
 
            <------------   set color in theme --------------->

                     Progressbar color ------> colorPrimary
                     Button and Space -------> tabSelectedTextColor

 3). use below code in activity 
 
 
              Button showAds;
              RelativeLayout rlBanner,rl_native;
              View tv_space;

             showAds = findViewById(R.id.btn_next);
                  rlBanner = findViewById(com.infyom.adssdk.R.id.rl_banner);
                  rl_native = findViewById(com.infyom.adssdk.R.id.rl_native);
                  tv_space = findViewById(com.infyom.adssdk.R.id.tv_space);


                  InfyOmAds.initializeAds(this);  // Once Application
                  InfyOmAds.enableTestMode(this); // Once

                  InfyOmAds.initDefaultValue(); // Once Splash
                  
                  InfyOmAds.showBanner(this,rlBanner,1);
                  InfyOmAds.showNative(this,rl_native,tv_space,1, InfyOmAds.AdTemplate.NATIVE_300);

                  showAds.setOnClickListener(v -> {
                      InfyOmAds.showInterstitial(1, this, new Interstitial() {
                          @Override
                          public void onAdClose(boolean isFail) {
                              startActivity(new Intent(MainActivity.this,Main2Activity.class));
                          }
                      });
                  });
        
   ======= Native Templates ===============
   
          InfyOmAds.AdTemplate.NATIVE_350,
          InfyOmAds.AdTemplate.NATIVE_300,
          InfyOmAds.AdTemplate.NATIVE_150,
          InfyOmAds.AdTemplate.NATIVE_100,
          InfyOmAds.AdTemplate.NATIVE_50,
          InfyOmAds.AdTemplate.NATIVE_40
