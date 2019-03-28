#include <jni.h>

JNIEXPORT jstring JNICALL
Java_kevin_xu_librarify_cameraCapture_getNativeKey(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "QUl6YVN5QjRGemlRbTlMTTJOYWhiM1NzS2JNRTdfY1RxNjB4Ml9R");
}

