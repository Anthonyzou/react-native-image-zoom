'use strict';

import React, {
  Component,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  TextInput,
  ToolbarAndroid,
  InteractionManager,
  ViewPagerAndroid,
} from 'react-native';

import _ from 'lodash'
import {Actions} from 'react-native-router-flux'
import Image from './zoom.js'


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
        <Image scale={1} source={{uri:"http://placehold.it/200"}}/>
        <Image scale={1} source={{uri:"http://placehold.it/200"}}/>
        <Image scale={1} source={{uri:"http://placehold.it/200"}}/>
        <Image scale={1} source={{uri:"http://placehold.it/200"}}/>
      </ViewPagerAndroid>
    );
  }
}

const styles = StyleSheet.create({
  container : {
    flex : 1,
  }
});
