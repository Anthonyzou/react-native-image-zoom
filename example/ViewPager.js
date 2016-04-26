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
  Dimensions,
  NativeModules,
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
    const {width, height} = Dimensions.get('window')

    return (
      <View style={{flex:1}}>
        <View style={{alignItems:'center', justifyContent:'center', margin: 5}}>
          <TouchableHighlight style={{ backgroundColor:'grey', padding: 5, width:100, borderRadius:5}} onPress={Actions.Main}>
            <Text>Main example</Text>
          </TouchableHighlight>
        </View>
        <ViewPagerAndroid style={styles.container}>
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
        </ViewPagerAndroid>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container : {
    flex : 1,
  }
});
