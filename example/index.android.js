/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  ScrollView,
  View,
  Dimensions,
  ToastAndroid,
  TextInput,
  TouchableHighlight,
} from 'react-native';

import Image from 'react-native-image-zoom'
import ViewPager from './ViewPager'
import {Actions, Router, Scene, Schema, Animations, TabBar} from 'react-native-router-flux'

class Example extends Component {
  constructor(a,b){
    super(a,b)
    this.state = {
      text:'http://i.imgur.com/UZaC7fb.jpg'
    }
  }
  render() {
    return (
      <ScrollView>
        <View style={{alignItems:'center', justifyContent:'center', margin: 5}}>
          <TouchableHighlight style={{ backgroundColor:'grey', padding: 5, width:100, borderRadius:5}} onPress={()=>Actions.ViewPager()}>
            <Text>Viewpager example</Text>
          </TouchableHighlight>
        </View>
        <TextInput onChangeText={(text) => this.setState({text:text})} value={this.state.text}></TextInput>
          <Image
            onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
            style={styles.image}
            source={{
              uri: this.state.text,
              thumbnail: "http://i.imgur.com/Yl2PB6m.jpg",
              headers: {
                "Referer" : 'http://...'
              }
            }}
            onLoad={()=>{
              ToastAndroid.show('delayed onLoad',ToastAndroid.LONG)
            }}
            onScaleChange={(e)=>{
              console.log("onScaleChange", e.nativeEvent)
            }}
            >
          </Image>
          <Image
            onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
            style={styles.image}
            source={{
              uri: "http://fail.com/33",
              thumbnail: "http://i.imgur.com/Yl2PB6m.jpg",
              headers: {
                "Referer" : 'http://...'
              }
            }}
            onLoad={()=>{
              ToastAndroid.show('delayed onLoad',ToastAndroid.LONG)
            }}
            onScaleChange={(e)=>{
              console.log("onScaleChange", e.nativeEvent)
            }}
            >
          </Image>
          <Image scale={2} resizeMode={"center"}
            style={styles.image}
            onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
            onLoad={()=>{
              ToastAndroid.show('onLoad',ToastAndroid.LONG)
            }}
            source={{uri:"data:image/png;base64,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"}}></Image>
          <Image
            scale={1}
            style={styles.image}
            source={require('./stock-vector-car-on-the-golden-state-bridge-265288760.jpg')}>
          </Image>
      </ScrollView>
    );
  }
}

class main extends Component{
  render(){
    return (
      <Router hideNavBar={true}>
        <Scene key="root">
            <Scene key="Main" component={Example}/>
            <Scene key="ViewPager" component={ViewPager}/>
        </Scene>
      </Router>
    )
  }
}

const {height, width} = Dimensions.get('window');
const styles = StyleSheet.create({
  container: {
  },
  image:{
    height: 250,
    marginBottom: 10,
  }
});

AppRegistry.registerComponent('example', () => main);
