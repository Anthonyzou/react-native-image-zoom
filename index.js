
import React,{
  requireNativeComponent,
  Component,
  PropTypes,
  View
} from 'react-native';

export default class ImageViewZoom extends Component {
  static propTypes = {
    ...View.propTypes,
    src: PropTypes.string,
    scale: PropTypes.number,
    scaleType: PropTypes.oneOf(["center","centerCrop","centerInside","fitCenter","fitStart","fitEnd","fitXY","matrix"]),
    onTap : PropTypes.func,
  };

  constructor(props) {
    super(props);
    this._onChange = this._onChange.bind(this);
  }

  setNativeProps(nativeProps) {
  }

  _onChange(event: Event) {
    this.props.onLoadComplete && this.props.onLoadComplete(event.nativeEvent.value);
  }

  render() {
    return <ImageZoomView {...this.props} />;
  }
}

const ImageZoomView = requireNativeComponent('ImageViewZoom', ImageViewZoom, {
});
