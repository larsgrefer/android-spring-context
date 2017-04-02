# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-keepattributes Signature,*Annotation*
-keepclassmembers,allowobfuscation class * {
    @org.springframework.beans.factory.annotation.Autowired *;
    @org.springframework.beans.factory.annotation.Value *;
    @org.springframework.context.annotation.Bean *;
}
