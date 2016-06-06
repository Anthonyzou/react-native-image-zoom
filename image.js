import { Platform } from 'react-native'
//Please declare your exports in this way,if
//it depends on Platform
if (Platform.OS == 'ios') {
  var ZoomImage = require('./index.ios.js')
  module.exports = ZoomImage

}
else{
  var ZoomImage = require('./index.android.js')
  module.exports = ZoomImage
}
