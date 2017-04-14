# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-dontwarn org.springframework.**
-dontwarn com.googlecode.openbeans.**
-dontwarn org.apache.harmony.beans.editors.**

-keep class * implements org.springframework.beans.BeanInfoFactory

-keep @org.springframework.stereotype.* class * {*;}

-keepattributes Signature,*Annotation*
-keepclassmembers,allowobfuscation class * {
    @org.springframework.beans.factory.annotation.Autowired *;
    @org.springframework.beans.factory.annotation.Value *;
    @org.springframework.context.annotation.Bean *;
}

-keepclassmembers @interface org.springframework.** {
    <methods>;
}
