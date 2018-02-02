<a href="https://play.google.com/store/apps/details?id=net.basov.lws.qr.gpm"><img src="https://github.com/mvbasov/lWS/raw/master/google-play-badge.png" width="215" height="83" alt="Available on Google Play"/></a>
### lightweight Web Server QR code plugin.

This programm designed as QR Code plugin for [lightweight Web Server (lWS)](https://github.com/mvbasov/lWS).
It can be used standalone as small and simple QR code generator also.
Start to enter text in the text area below or paste it from clipboard and press 'Encode' button to see QR code.
To see this text again press 'Clear' button.
### How to integrate with you application
lWS QR can be easyly integrated with your application. The following code explain how to do this:
```
String textToEncode = "Some text to encode";
PackageManager pm = getApplicationContext().getPackageManager();
try {
      pm.getPackageInfo("net.basov.lws.qr.gpm", 0);
      Intent i = new Intent("net.basov.lws.qr.ENCODE");
      i.putExtra("ENCODE_DATA", textToEncode);//(mandatory) text to encode
      i.putExtra("ENCODE_SIZE", "256"); //(optional) image size in pixels
      i.putExtra("ENCODE_DARK", "#000"); //(optional) foreground color
      i.putExtra("ENCODE_LIGHT", "#e0ffff"); //(optional) background color
      i.putExtra("ENCODE_CORRECTION", "L"); //(optional) Eroor correction level [L,M,Q,H]
      startActivity(i);
} catch (PackageManager.NameNotFoundException e_lws_qr) {
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse("market://details?id=net.basov.lws.qr.gpm"));
      startActivity(i);
}
```
### License
This software licensed under [MIT license](LICENSE).
To create QR code used [qrcode.js](https://davidshimjs.github.io/qrcodejs/) licensed under MIT license also.
