[![Build Status](https://travis-ci.org/mvbasov/lWS.QR.svg?branch=master)](https://travis-ci.org/mvbasov/lWS.QR)
<a href="https://play.google.com/store/apps/details?id=net.basov.lws.qr.gpm"><img src="https://github.com/mvbasov/lWS/raw/master/google-play-badge.png" width="215" height="83" alt="Available on Google Play"/></a>
<a href="https://f-droid.org/en/packages/net.basov.lws.qr.fdroid/"><img src="https://github.com/mvbasov/lWS/raw/master/f-droid-badge.png" width="215" height="83" alt="Available on F-Droid"/></a>
### lightweight Web Server QR code plugin.

This programm was designed as a QR Code plugin for the [lightweight Web Server (lWS)](https://github.com/mvbasov/lWS).
It can also be used by itself as a small and simple QR code generator.
Start by entering text in the text area below or paste it from clipboard and press 'Encode' button to see QR code.
To see this text again press 'Clear' button.
### How to integrate with you application
lWS QR can be easily integrated with your application. The following code explain how to do this:
```
String textToEncode = "Some text to encode";
PackageManager pm = getApplicationContext().getPackageManager();
try {
      pm.getPackageInfo("net.basov.lws.qr.gpm", 0);
      Intent i = new Intent("net.basov.lws.qr.ENCODE");
      i.putExtra("ENCODE_DATA", textToEncode);//(mandatory) text to encode
      i.putExtra("ENCODE_LABEL", textToEncode);//(optional, default: same as text to encode) text above QR code
      i.putExtra("ENCODE_CORRECTION", "L"); //(optional, default: L) Error correction level [L,M,Q,H]
      i.putExtra("ENCODE_MODULE_SIZE", 6);//(optional, default 6) Small black square zize in pixels
      i.putExtra("ENCODE_MASK", -1);//(optional, default -1) QR code data mask 0-7 or -1 for autodetect
      i.putExtra("ENCODE_MIN_VERSION", 1);//(optional, default 1) Force minimal QR code version (size)                         
      startActivity(i);
} catch (PackageManager.NameNotFoundException e_lws_qr) {
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse("market://details?id=net.basov.lws.qr.gpm"));
      startActivity(i);
}
```
### License
This software licensed under [MIT license](LICENSE). Copyright (c) 2018 Mikhail Basov

To create QR code this program uses [QR Code generator library](https://github.com/nayuki/QR-Code-generator) licensed under MIT license also. Copyright (c) 2017 Project Nayuki.

### Acknowledgments
* [Rodrigo I. Ávila D. aka Undigon](https://github.com/Undigon) for my ugly English correction.

