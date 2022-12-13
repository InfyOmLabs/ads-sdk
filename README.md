# ads-sdk

To get a Git project into your build:
Step 1. Add the JitPack repository to your build file

========= Features ==========

          ==> Ads Show Rate  >  80% 
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

        dependencies {
                implementation 'com.github.InfyOmLabs:ads-sdk:1.0.3'
        }
        
        
 ===== color guide ====== 
 
            <------------   set color in theme --------------->

                     Progressbar color ------> colorPrimary
                     Button and Space -------> colorAccent

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
   
          InfyOmAds.AdTemplate.NATIVE_300,
          InfyOmAds.AdTemplate.NATIVE_100,
          InfyOmAds.AdTemplate.NATIVE_50,
          InfyOmAds.AdTemplate.NATIVE_40
