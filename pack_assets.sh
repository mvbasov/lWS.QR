#!/bin/bash
# Pack assets for mobile development

PROG_NAME="lWS.QR"
ASSETS_LIST="assets/qrcodegen.min.js"
GCOMMIT=`git rev-parse --short HEAD`
GLTAG=`git  describe --tags --abbrev=0`
GCOUNT=`git rev-list --count ${GLTAG}..HEAD`

cd app/src/main/
tar -zcvf ../../../${PROG_NAME}_assets.${GLTAG}-${GCOUNT}-${GCOMMIT}.tgz ${ASSETS_LIST}

