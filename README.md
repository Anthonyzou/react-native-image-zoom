# React image pan and zoom for Android

This is still very basic since I am quite new to React. There is plenty of room for improvment by adding
additional react bindings for props and callbacks.

##### Props:
| Property | Type | Default | Description |
|---------------|----------|--------------|----------------------------------------------------------------|
| souce | Object | null | same as the react image format |
| tintColor | string | null | optional tintColor |
| onTap | function | null | optional on tap listener |
| scale | float | null | optional scale amount |
| scaleType | string | null | one of center, centerCrop, centerInside, fitCenter, fitStart, fitEnd, fitXY, matrix|


## Include in your App


Installation
------------

Install the npm package [`react-native-image-zoom`](https://www.npmjs.com/package/react-native-image-zoom). Inside your React Native project, run ([example](https://github.com/Anthonyzou/react-native-image-zoom/tree/master/example)):
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
import com.image.zoom.ReactImageZoom; // add this import

public class MainActivity extends ReactActivity {
    //...

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new ReactImageZoom() // add this manager
      );
    }
```

---

TeamLockr image zoom
Team Lockr image zoom for react native

These are functions created by the TeamLockr Team created for the TeamLockr platform.

---
