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
  TextInput,
} from 'react-native';

import Image from 'react-native-image-zoom'
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
          <Image onTap={()=>{console.log('ON TAP')}} style={styles.image} source={{uri:this.state.text}}></Image>
          <Image scale={2} resizeMode={"center"} onTap={()=>{console.log('ON TAP')}} style={styles.image} source={{uri:this.state.text}}></Image>
          <Image scale={1} style={styles.image} source={require('./stock-vector-car-on-the-golden-state-bridge-265288760.jpg')}></Image>
      </ScrollView>
    );
  }
}

class main extends Component{
  render(){
    return (
      <Router hideNavBar={true}>
        <Route name="Main" type="reset" component={Example}/>
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
