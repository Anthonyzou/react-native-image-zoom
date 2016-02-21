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
  View,
} from 'react-native';

import Image from 'react-native-image-zoom'
import {Actions, Router, Route, Schema, Animations, TabBar} from 'react-native-router-flux'
class Example extends Component {
  constructor(a,b){
    super(a,b)
    this.state = {
      text:'http://placehold.it/500'
    }
  }
  render() {
    return (
      <View style={{flex:1}}>
        <TextInput onChangeText={(text) => this.setState({text:text})} value={this.state.text}></TextInput>
        <Image style={styles.image} src={this.state.text}></Image>
        <Image tintColor="rgba(0,0,0,.5)" style={styles.image} src={this.state.text} scale="2.0" scaleType="center"></Image>
      </View>
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
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  image:{
    height: 200,
  }
});

AppRegistry.registerComponent('example', () => main);
