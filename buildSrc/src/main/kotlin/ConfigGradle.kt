/**
 * <h3> 作用类描述：全局Gradle管理</h3>
 *
 * @Package :        com.alvin.build_src
 * @Date :           2022/3/3
 * @author 高国峰
 */

/**
 * 发布插件
 */
object JetpackMaven {

    /*   发布插件的相关属性     */

    const val mavenGroup = "com.alvin.mvvm"
    const val mavenVersion = "1.0.8"

    const val mvvmArtifactId = "mvvm_framework"
    const val mvvmNetworkArtifactId = "mvvm_network"
    const val mvvmNavigationArtifactId = "mvvm_navigation"
}

object AndroidConfig {
    const val compileSdk = 30
    const val applicationId = "com.alvin.mvvm_framework"
    const val minSdk = 16
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val minifyEnabled = false
    const val proguardFile = "proguard-rules.pro"
}

object Dependencies {

    private const val kotlinVersion = "1.5.30"
    private const val coreVersion = "1.5.0"
    private const val appcompatVersion = "1.3.0"
    private const val materialVersion = "1.3.0"
    private const val constraintLayoutVersion = "2.0.4"

    private const val lifecycleVersion = "2.2.0"

    private const val navigationVersion = "2.3.5"

    private const val immersionVersion = "3.0.0"

    private const val autoSizeVersion = "v1.2.1"

    private const val refreshVersion = "2.0.3"

    private const val materialDialogVersion = "3.3.0"

    private const val adapterVersion = "3.0.4"
    private const val recyclerDividerVersion = "3.5.0"

    private const val loadingAnimVersion = "2.1.3"

    private const val retrofitVersion = "2.9.0"

    private const val retrofitBaseUrlVersion = "1.4.0"

    private const val okHttpVersion = "4.9.1"

    private const val interceptorVersion = "v1.6.12"

    private const val utilsVersion = "1.31.0"

    private const val lottieVersion = "5.0.2"

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val androidCore = "androidx.core:core-ktx:$coreVersion"
    const val androidAppcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val androidMaterial = "com.google.android.material:material:$materialVersion"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

    /*  Lifecycle  */
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val lifecycleCommonKtx = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    const val lifecycleExtensionsKtx = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    /*  Navigation  */
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    const val navigationCommonKtx = "androidx.navigation:navigation-common-ktx:$navigationVersion"

    /*  状态工具类   */
    const val immersionBar = "com.gyf.immersionbar:immersionbar:$immersionVersion"
    const val immersionBarKtx = "com.gyf.immersionbar:immersionbar-ktx:$immersionVersion"

    /*  头条屏幕适配  */
    const val androidAutoSize = "com.github.JessYanCoding:AndroidAutoSize:$autoSizeVersion"

    /*  RecyclerView 刷新控件  */
    const val refreshCore = "com.scwang.smart:refresh-layout-kernel:$refreshVersion"
    const val refreshHeaderClassics = "com.scwang.smart:refresh-header-classics:$refreshVersion"
    const val materialHeader = "com.scwang.smart:refresh-header-material:$refreshVersion"
    const val refreshFooterClassics = "com.scwang.smart:refresh-footer-classics:$refreshVersion"
    const val ballFooter = "com.scwang.smart:refresh-footer-ball:$refreshVersion"

    /*  Material Dialog  */
    const val materialDialog = "com.afollestad.material-dialogs:core:$materialDialogVersion"

    /*  Adapter  */
    const val adapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:$adapterVersion"

    /*  分割  */
    const val recyclerDivider = "com.github.fondesa:recycler-view-divider:$recyclerDividerVersion"


    /*  加载动画  */
    const val loadingAnim = "com.wang.avi:library:$loadingAnimVersion"

    /*  Retrofit  */
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    const val retrofitBaseUrl = "me.jessyan:retrofit-url-manager:$retrofitBaseUrlVersion"

    /*  OKHttp */
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"

    /*  拦截器  */
    const val okHttpInterceptor =
        "com.github.fengzhizi715:saf-logginginterceptor:$interceptorVersion"

    /*  工具类  */
    const val utils = "com.blankj:utilcodex:$utilsVersion"

    const val lottie = "com.airbnb.android:lottie:$lottieVersion"
}