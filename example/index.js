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
  ScrollView,
  Dimensions,
  ToastAndroid,
  TextInput,
} from 'react-native';

import Image from './zoom.js'
import ViewPager from './ViewPager'
import {Actions, Router, Route, Schema, Animations, TabBar} from 'react-native-router-flux'

class Example extends Component {
  constructor(a,b){
    super(a,b)
    this.state = {
      text:'http://placehold.it/250'
    }
  }
  render() {
    return (
      <ScrollView>
        <TextInput onChangeText={(text) => this.setState({text:text})} value={this.state.text}></TextInput>
          <Image
            onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
            style={styles.image}
            source={{uri:this.state.text}}></Image>
          <Image scale={2} resizeMode={"center"}
            style={styles.image}
            onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
            onLoad={()=>{
              ToastAndroid.show('onLoad',ToastAndroid.LONG)
            }}
            source={{uri:this.state.text}}></Image>
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
        <Route name="ViewPager" component={ViewPager}/>
        <Route name="Main" component={Example}/>

      </Router>
    )
  }
}

const {height, width} = Dimensions.get('window');
const styles = StyleSheet.create({
  container: {
  },
  image:{
    height: 250
  }
});

AppRegistry.registerComponent('example', () => main);
