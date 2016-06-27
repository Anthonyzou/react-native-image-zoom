
import React,{Component,PropTypes} from 'react';
import {
  requireNativeComponent,
  View,
} from 'react-native';
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';

export default class ImageViewZoom extends Component {
  static propTypes = {
    ...View.propTypes,
    source: PropTypes.oneOfType([
      PropTypes.shape({
        uri: PropTypes.string,
        thumbnail: PropTypes.string,
        headers: PropTypes.object,
      }),
      // Opaque type returned by require('./image.jpg')
      PropTypes.number,
    ]),
    scale: PropTypes.number,
    scaleType: PropTypes.oneOf(["center","centerCrop","centerInside","fitCenter","fitStart","fitEnd","fitXY","matrix"]),
    onTap : PropTypes.func,
    onLoad : PropTypes.func,
    onScaleChange : PropTypes.func,
    onMatrixChange : PropTypes.func,
  };

  constructor(props) {
    super(props);
  }

  render() {
    const source = resolveAssetSource(this.props.source);
    if (source && source.uri){

      const props = {...this.props, src: source };
      return <ZoomImage {...props} />;
    }
    return null
  }
}

const cfg = {
  nativeOnly: {
    src: true,
  },
};
const ZoomImage = requireNativeComponent('ImageViewZoom', ImageViewZoom, cfg);
