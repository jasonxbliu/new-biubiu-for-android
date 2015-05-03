# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#local libs
-libraryjars libs/BaiduLBS_Android.jar


#jackson
-dontskipnonpubliclibraryclassmembers
-keepattributes *Annotation*,EnclosingMethod
-keep class com.fasterxml.jackson.databind.** { *; }
-keepnames class org.codehaus.jackson.** { *; }
-keep class com.fasterxml.jackson.annotation.**{*;}
-keepnames class org.codehaus.jackson.** { *; }
-keep class com.jupaidashu.android.wrapper.**{*;}
-keep class org.apache.http.**{*;}

-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry

-dontwarn android.net.http.AndroidHttpClient
-dontwarn org.apache.http.client.protocol.**
-dontwarn org.apache.http.conn.ssl.**
-dontwarn org.apache.http.impl.**
-dontwarn cn.sharesdk.tencent.qq.e

#jackson bean
-keep public class com.jaf.bean.** {
  public void set*(***);
  public *** get*();
} 

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


#v4
-keep class android.support.v4.** { *; }

-keepclassmembers class * {
    @com.fasterxml.jackson.annotation.* *;
    @com.jupaidashu.android.wrapper.* *;
}

-keepattributes Signature

-keepclassmembers class * extends android.app.Activity{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.jaf.jcore.BindableActivity{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.jaf.jcore.BindableView{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.jaf.biubiu.**{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.jaf.jcore.BaseActionBarActivity**{
   public void *(android.view.View);
}

#-dontpreverify
-verbose
-dontwarn
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes InnerClasses,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class m.framework.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class **.R$* {   
    *;   
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#share sdk end
