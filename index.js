
import React,{
  requireNativeComponent,
  Component,
  PropTypes,
  View
} from 'react-native';

import resolveAssetSource from 'resolveAssetSource';
import merge from 'merge';

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
    loadingIndicatorSource: PropTypes.oneOfType([
      PropTypes.shape({
        uri: PropTypes.string,
      }),
      // Opaque type returned by require('./image.jpg')
      PropTypes.number,
    ]),
    scale: PropTypes.number,
    scaleType: PropTypes.oneOf(["center","centerCrop","centerInside","fitCenter","fitStart","fitEnd","fitXY","matrix"]),
    onTap : PropTypes.func,
  };

  constructor(props) {
    super(props);
  }

  setNativeProps(nativeProps) {
  }

  _onChange(event: Event) {
  }

  render() {
    const source = resolveAssetSource(this.props.source);
    const loadingIndicatorSource = resolveAssetSource(this.props.loadingIndicatorSource);
    if (source && source.uri){


      const props = {...this.props,...{
        src: source.uri,
        loadingIndicatorSource : loadingIndicatorSource ? loadingIndicatorSource.uri : null,
      }};

      return <ZoomImage {...props} />;
    }
    return null
  }
}

const cfg = {
  nativeOnly: {
    src: true,
    loadingIndicatorSrc: true,
  },
};
const ZoomImage = requireNativeComponent('ImageViewZoom', ImageViewZoom, cfg);
