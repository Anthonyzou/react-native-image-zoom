# Image pan and zoom for Android

```bash
npm install --save react-native-image-zoom
```

##### Props:
| Property | Type | Default | Description |
|---------------|----------|--------------|----------------------------------------------------------------|
| source | Object | null | same as the react image format `source={{uri: Proptypes.string,thumbnail: Proptypes.string, headers: Proptypes.object, }}` or `source={require('./...')}`|
| tintColor | string | null | optional tintColor |
| scale | float | null | optional scale amount |
| scaleType | string | null | one of center, centerCrop, centerInside, fitCenter, fitStart, fitEnd, fitXY, matrix|
| onTap | function | null | optional on tap listener |
| onLoad | function | null | optional on load listener |
| onScaleChange | function | null | optional on scale change listener |
| onMatrixChange | function | null | (Android only) optional on matrix change listener |

## Example

```jsx
import Image from 'react-native-image-zoom'

<Image
  onTap={ ()=> {ToastAndroid.show('ON TAP',ToastAndroid.SHORT)}}
  onLoad={ ()=> {
    ToastAndroid.show('onLoad',ToastAndroid.SHORT)
  }}
  source={{
    uri: this.state.text,
    thumbnail: "http://i.imgur.com/Yl2PB6m.jpg",
    headers: {
      "Referer" : 'http://...'
    }
  }}>
</Image>
```

## Using a view pager

Instead of importing from `ViewPagerAndroid` import
`ViewPagerZoom` from `react-native-image-zoom` there is a bug
in android view pagers with pinch and zoom.

```jsx
import {ViewPagerZoom} from 'react-native-image-zoom'
<ViewPagerZoom style={styles.container}>
  <View style={{flex:1, alignItems:'center', justifyContent:'center'}}>
    <Image
      source={{uri:"http://placehold.it/200"}}
      style={{width:width, flex:1,}}
      onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
      onLoad={()=>{
        ToastAndroid.show('onLoad',ToastAndroid.LONG)
      }}
      />
  </View>
  <View style={{flex:1, alignItems:'center', justifyContent:'center'}}>
    <Image
      source={{uri:"http://placehold.it/200"}}
      style={{width:width, flex:1,}}
      onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
      onLoad={()=>{
        ToastAndroid.show('onLoad',ToastAndroid.LONG)
      }}
      />
  </View>
</ViewPagerZoom>
```


## Installation

Installation
------------

Install the npm package [`react-native-image-zoom`](https://www.npmjs.com/package/react-native-image-zoom). Inside your React Native project, run ([example](https://github.com/Anthonyzou/react-native-image-zoom/tree/master/example)):

In `android/settings.gradle`
```
include ':react-native-image-zoom'
project(':react-native-image-zoom').projectDir = file('../node_modules/react-native-image-zoom/android')
```
**NOTE** : If you have included other libraries in your project, the `include` line will contain the other dependencies too.

In `android/build.gradle` add the jitpack repositories.

```
allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven { url "https://jitpack.io" } // <--- add this line
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url "$rootDir/../node_modules/react-native/android"
        }
    }
}
```

In `android/app/build.gradle`, add a dependency to `':react-native-image-zoom'`
```
dependencies {
    compile project(':react-native-image-zoom')
}
```

Next, you need to change the `MainActivity` of your app to register `ReactImageZoom` :
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
}
```

---

TeamLockr image zoom
Team Lockr image zoom for react native

These are functions created by the TeamLockr Team created for the TeamLockr platform.

---
