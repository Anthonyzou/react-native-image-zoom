/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Image,
  ScrollView,
  View,
  Dimensions,
  ToastAndroid,
  TextInput,
  TouchableHighlight,
  Text,
} from 'react-native';

import {Actions, Router, Route, Schema, Animations, TabBar} from 'react-native-router-flux'
import Zoom from './zoom'
class Example extends Component {
  constructor(a,b){
    super(a,b)
    this.state = {
      text:'http://placehold.it/250'
    }
  }
  render() {
    return (

        <Zoom style={{width:500, height: 500}}
          maximumZoomScale={3} minimumZoomScale={.5}
          source={{uri:'https://placeholdit.imgix.net/~text?txtsize=47&txt=500%C3%97500&w=500&h=500'}}/>
    );
  }
}

class main extends Component{
  render(){
    return (
      <Router hideNavBar={true}>
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
    height: 250,
    marginBottom: 10,
  }
});

AppRegistry.registerComponent('example', () => main);
