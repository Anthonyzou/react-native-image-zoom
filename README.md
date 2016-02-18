# React image pan and zoom for Android

## Include in your App


Installation
------------

Install the npm package [`react-native-image-zoom`](https://www.npmjs.com/package/react-native-image-zoom). Inside your React Native project, run ([example](https://github.com/aakashns/react-native-image-zoom-example/commit/e6b83bf3d2238cf7e4ec3688519f38b2544ccad5)):
```bash
npm install --save react-native-image-zoom
```

In `android/settings.gradle`, remove the line `include ':app'` and add the following lines
```
include :react-native-image-zoom'
project(':react-native-image-zoom').projectDir = file('../node_modules/react-native-image-zoom/android')
```
**NOTE** : If you have included other libraries in your project, the `include` line will contain the other dependencies too.

In `android/app/build.gradle`, add a dependency to `':react-native-image-zoom'`
```
dependencies {
    compile project(':react-native-image-zoom')
}
```

Next, you need to change the `MainActivity` of your app to register `ReactNativeDialogsPackage` :
```java
import com.imager.ReactImageZoom; // add this import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
    //...

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new ReactImageZoom() // add this manager
      );
    }
```
