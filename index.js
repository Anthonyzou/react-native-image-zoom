
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
    scale: PropTypes.string,
    scaleType: PropTypes.string    
  };

  constructor(props) {
    super(props);
    this._onChange = this._onChange.bind(this);
  }

  setNativeProps(nativeProps) {
    this._root.setNativeProps(nativeProps);
  }

  _onChange(event: Event) {
    this.props.onLoadComplete && this.props.onLoadComplete(event.nativeEvent.value);
  }

  render() {
    return <ImageZoomView {...this.props} />;
  }
}

const ImageZoomView = requireNativeComponent('ImageViewZoom', ImageViewZoom, {
  nativeOnly: {onChange: true}
});
