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
-repackageclasses ''
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#jackson lib proguard
-libraryjars libs/jackson-annotations-2.2.3.jar
-libraryjars libs/jackson-core-2.2.3.jar
-libraryjars libs/jackson-databind-2.2.3.jar
#jackson lib end

-libraryjars libs/volley.jar
-libraryjars libs/android-support-v7-recyclerview.jar
-libraryjars hideapi.jar


#jackson
-dontskipnonpubliclibraryclassmembers

-keepattributes *Annotation*,EnclosingMethod
-keep class com.fasterxml.jackson.databind.** { *; }
-keep class com.z873.market.bean.JacksonWrapper
-keepnames class org.codehaus.jackson.** { *; }
-keep class com.fasterxml.jackson.annotation.**{*;}
#jackson


-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry

#v4
#-keep class android.support.v4.** { *; }

-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *;
}

#jackson bean
-keep public class com.z873.market.bean.** {
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

-keepclassmembers class * {
    @com.fasterxml.jackson.annotation.* *; @com.fasterxml.jackson.annotation.* *;
    @com.z873.market.util.* *;
}



-keepattributes Signature


#my reflection 

-keepclassmembers class * extends android.app.Activity{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.z873.market.util.BindableActivity{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.z873.market.util.**{
   public void *(android.view.View);
}

-keepclassmembers class * extends com.z873.market.BaseActionBarActivity**{
   public void *(android.view.View);
}

