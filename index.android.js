
import React,{
  requireNativeComponent,
  Component,
  PropTypes,
  View,
} from 'react-native';

// wtf?
const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

export default class ImageViewZoom extends Component {
  static propTypes = {
    ...View.propTypes,
    source: PropTypes.oneOfType([
      PropTypes.shape({
        uri: PropTypes.string,
      }),
      // Opaque type returned by require('./image.jpg')
      PropTypes.number,
    ]),
    scale: PropTypes.number,
    scaleType: PropTypes.oneOf(["center","centerCrop","centerInside","fitCenter","fitStart","fitEnd","fitXY","matrix"]),
    onTap : PropTypes.func,
    onLoad : PropTypes.func,
    onScaleChange : PropTypes.func,
  };

  constructor(props) {
    super(props);
  }

  render() {
    const source = resolveAssetSource(this.props.source);
    if (source && source.uri){

      const props = {...this.props, src: source.uri };
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
