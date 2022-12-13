# ads-sdk

To get a Git project into your build:
Step 1. Add the JitPack repository to your build file

========= Features ==========

                    Ads Show Rate  >  80% 

                    You Can Use Multiple Ads Id for banner,interstitial and native ads; 

========= Gradle ==========

1). Add it in your root build.gradle at the end of repositories:

          allprojects {
              repositories {
                     maven { url 'https://jitpack.io' }
            }
          }
          
2). Add the dependency

        dependencies {
                implementation 'com.github.InfyOmLabs:ads-sdk:1.0.1'
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
                  InfyOmAds.showNative(this,rl_native,tv_space,1,false);

                  showAds.setOnClickListener(v -> {
                      InfyOmAds.showInterstitial(1, this, new Interstitial() {
                          @Override
                          public void onAdClose(boolean isFail) {
                              startActivity(new Intent(MainActivity.this,Main2Activity.class));
                          }
                      });
                  });
        
        
