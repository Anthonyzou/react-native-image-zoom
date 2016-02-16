/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  Dimensions,
  // Image,
} from 'react-native';
import Image from './image.js'
class example extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Image style={styles.image} source={{ uri: "http://placehold.it/300"}}></Image>
      </View>
    );
  }
}
var {height, width} = Dimensions.get('window');
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  image:{
    width: width,
    flex:1,
  }
});

AppRegistry.registerComponent('example', () => example);
