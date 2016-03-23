'use strict';

import React, {
  Component,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  TextInput,
  ToastAndroid,
  ToolbarAndroid,
  InteractionManager,
  ViewPagerAndroid,
} from 'react-native';

import _ from 'lodash'
import {Actions} from 'react-native-router-flux'
import Image from 'react-native-image-zoom'


export default class ExamplePage extends Component {
  static propTypes = {};
  static defaultProps = {};

  constructor(props, context) {
    super(props, context);
    this.state = {
    };
  }
  render() {
    return (
      <ViewPagerAndroid style={styles.container}>
        <Image source={{uri:"http://placehold.it/200"}}
          onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
          onLoad={()=>{
            ToastAndroid.show('onLoad',ToastAndroid.LONG)
          }}
        />
        <Image source={{uri:"http://placehold.it/200"}}
          onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
          onLoad={()=>{
            ToastAndroid.show('onLoad',ToastAndroid.LONG)
          }}
        />
        <Image source={{uri:"http://placehold.it/200"}}
          onTap={()=>{ToastAndroid.show('ON TAP',ToastAndroid.LONG)}}
          onLoad={()=>{
            ToastAndroid.show('onLoad',ToastAndroid.LONG)
          }}
        />
      </ViewPagerAndroid>
    );
  }
}

const styles = StyleSheet.create({
  container : {
    flex : 1,
  }
});
