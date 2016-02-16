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

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  image:{
    height: 300,
    width: 300,
  }
});

AppRegistry.registerComponent('example', () => example);
